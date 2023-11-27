package com.example.lab3;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        // Инициализация фильтра (может быть пустой, если не требуется)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // Ваша логика проверки cookie для аутентификации
        boolean isAuthenticated = CookieUtils.checkAuthentication((HttpServletRequest) request);

        if (isAuthenticated) {
            Cache<Integer, User> idUserCache = (Cache<Integer, User>) filterConfig.getServletContext().getAttribute("idUserCache");
            Cache<String, Integer> idNameCache = (Cache<String, Integer>) filterConfig.getServletContext().getAttribute("idNameCache");
            User user=CookieUtils.getObjectFromCookie((HttpServletRequest) request,"currentUser",User.class);
            idUserCache.put(user.getId(),user);
            idNameCache.put(user.getUsername(),user.getId());
            // Прошли аутентификацию, выполняем цепочку фильтров и сервлета
            chain.doFilter(request, response);
        } else {
            String servletPath = ((HttpServletRequest) request).getServletPath();
            String [] allowedPaths={"/index.jsp", "/registration","/login"};
            if (!FilterUtils.isPathAllowed(servletPath,allowedPaths)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                HttpServletRequest httpRequest = (HttpServletRequest) request;
                httpResponse.sendRedirect(httpResponse.encodeRedirectURL(httpRequest.getServletContext().getContextPath()));
            }else{
                chain.doFilter(request, response);
            }
        }
    }


    @Override
    public void destroy() {
        // Освобождение ресурсов, если необходимо
    }
}

