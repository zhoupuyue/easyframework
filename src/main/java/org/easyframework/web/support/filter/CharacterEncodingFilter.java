/**
 * 
 */
package org.easyframework.web.support.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import static org.easyframework.util.ParamUtil.empty;


/**
 * 设置字符编码过滤器
 * @author zhoupuyue
 * @date 2013-8-21
 */
public class CharacterEncodingFilter implements Filter {

	private String encoding = "UTF-8";
	
	public void init(FilterConfig config) throws ServletException {
		String encoding = config.getInitParameter("encoding");
		if(!empty(encoding)){
			this.encoding = encoding;
		}
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	public void destroy() {
		
	}

}
