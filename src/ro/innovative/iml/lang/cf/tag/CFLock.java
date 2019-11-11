/**
 * Copyright (c) 2006 - present Innovative Systems SRL
 * Copyright (c) 2006 - present Ovidiu Podisor ovidiu.podisor@innodocs.com
 * 
 * Authors: Ovidiu Podisor and members of the
 *          IML lab at West University Timisoara (www.uvt.ro)
 * 
 * This file is part of the CF2JSP project.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 
package ro.innovative.iml.lang.cf.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.TimeUnit;
import javax.servlet.jsp.PageContext;

public class CFLock extends BodyTagSupport {
	static final long serialVersionUID = 767765;

	private long timeout = 0;
	private String scope = "Application";
	private String name = "";
	private String throwontimeout = "Yes";
	private String type = "exclusive";
	private ReentrantReadWriteLock rwl;
	private HashMap lockmap;

	private boolean lockacquired = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThrowontimeout() {
		return throwontimeout;
	}

	public void setThrowontimeout(String throwOnTimeout) {
		this.throwontimeout = throwOnTimeout;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int doStartTag() throws JspException {
		int aScope;

		if (scope.equalsIgnoreCase("application")) {
			aScope = PageContext.APPLICATION_SCOPE;
		} else if (scope.equalsIgnoreCase("session")) {
			aScope = PageContext.SESSION_SCOPE;
		} else if (scope.equalsIgnoreCase("server")) {
			throw new JspException("No server scope in JSP");
		} else {
			throw new JspException("Invalid specified scope");
		}

		lockmap = (HashMap) pageContext.getAttribute("lockmap", aScope);
		if (lockmap == null) {
			pageContext.setAttribute("lockmap", new HashMap(), aScope);
			lockmap = (HashMap) pageContext.getAttribute("lockmap", aScope);
		}
		rwl = (ReentrantReadWriteLock) lockmap.get(name);
		if (rwl == null) {
			lockmap.put(name, new ReentrantReadWriteLock());
			rwl = (ReentrantReadWriteLock) lockmap.get(name);
		}

		try {
			if (type.equalsIgnoreCase("exclusive")) {
				lockacquired = rwl.writeLock().tryLock(timeout,
						TimeUnit.SECONDS);
			} else if (type.equalsIgnoreCase("readonly")) {
				lockacquired = rwl.readLock()
						.tryLock(timeout, TimeUnit.SECONDS);
			} else {
				throw new JspException("Invalid specified lock type:" + type);
			}
		} catch (InterruptedException ie) {
			throw new JspException("Thread interrupted");
		}
		if (!lockacquired) {
			if (throwontimeout.equalsIgnoreCase("yes")) {
				throw new JspException("Timeout while waiting for lock:"
						+ scope + "." + name);
			}
			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		if (!lockacquired)
			return super.doEndTag();
		if (type.equalsIgnoreCase("exclusive")) {
			rwl.writeLock().unlock();
		} else if (type.equalsIgnoreCase("readonly")) {
			rwl.readLock().unlock();
		}
		if (!rwl.hasQueuedThreads()) {
			lockmap.remove(name);
		}
		return super.doEndTag();
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setPageContext(PageContext arg0) {
		this.pageContext = arg0;
	}

}
