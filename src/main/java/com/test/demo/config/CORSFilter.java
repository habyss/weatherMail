// package com.test.demo.config;
//
// import org.springframework.context.annotation.Configuration;
//
// import javax.servlet.*;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.io.IOException;
//
// /**
//  * @author chensheng.fang@mytian.com
//  * @date 2018/7/6-15:06
//  */
//
// @Configuration
// public class CORSFilter implements Filter {
//     @Override
//     public void init(FilterConfig filterConfig) throws ServletException {
//
//     }
//
//
//     @Override
//     public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
//             throws IOException, ServletException {
//
//         HttpServletRequest request = (HttpServletRequest) servletRequest;
//         HttpServletResponse response = (HttpServletResponse) servletResponse;
//         response.setHeader("Access-Control-Allow-Origin", "*");
//         response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
//         response.setHeader("Access-Control-Max-Age", "3600");
//         response.setHeader("Access-Control-Allow-Headers", "token,content-type,x-auth-token,Accept,Origin, X-Requested-With");
//         response.setHeader("Access-Control-Allow-Credentials", "true");
//         filterChain.doFilter(servletRequest, servletResponse);
//
//     }
//
//     @Override
//     public void destroy() {
//
//     }
// }
