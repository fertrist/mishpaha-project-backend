package org.mishpaha.project.config;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang.StringUtils.isBlank;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter implements Filter {

    private String[] acceptedHosts = new String[] {"localhost"};
    private String accessControlAllowHeaders = "Accept-Encoding, X-Requested-With, Access-Control-Request-Method, " +
        "Access-Control-Request-Headers, Accept-Encoding, Content-Type, Authorization";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = ((HttpServletRequest) req);
        //System.out.println(getRequest(request));
        String origin = request.getHeader("origin");
        if (isAcceptedOrigin(origin)) {
            response.setHeader("Access-Control-Allow-Origin", origin);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
            response.setHeader("Access-Control-Allow-Credentials", "true");
        }
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(200);
            response.getWriter().print("OK");
            response.getWriter().flush();
        } else {
            chain.doFilter(req, res);
        }
        //System.out.println(getResponse(response));
    }

    private String getRequest(HttpServletRequest request) {
        return "Request:"
            + "\nORIGIN: " + request.getHeader("origin")
            + "\nURL: " + request.getRequestURL().toString()
            + "\nMETHOD: " + request.getMethod()
            + "\nHEADERS: " + request.getHeaderNames().toString();
    }

    private String getResponse(HttpServletResponse response) {
        return "Response:"
            + "\nHEADERS: " + response.getHeaderNames().toString();
    }

    public void init(FilterConfig filterConfig) {}

    public void destroy() {}

    private boolean isAcceptedOrigin(String origin)
    {
        if (isBlank(origin)) {
            return false;
        }
        String host = StringUtils.substringAfter(origin, "://");
        if (isBlank(host)) {
            return false;
        }
        return StringUtils.startsWithAny(host, acceptedHosts);
    }

}
