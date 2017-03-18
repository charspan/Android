package com.charspan.webmicroarchitecture.handler.itf;

import com.charspan.webmicroarchitecture.server.HttpContext;

/**
 * 路由规则
 */
public interface IResourceUriHandler {

	/**
	 * 验证 uri 是否合法
	 * 
	 * @param uri
	 * @return
	 */
	boolean accept(String uri);

	/**
	 * 处理相关请求
	 * 
	 * @param uri
	 * @param httpContext
	 */
	void handler(String uri, HttpContext httpContext);
}
