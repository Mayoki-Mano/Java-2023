package com.example.lab3.structures;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class Lab3ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Cache<Integer, User> idUserCache = Caffeine.newBuilder().build();
        servletContextEvent.getServletContext().setAttribute("idUserCache", idUserCache);
        Cache<String, Integer> idNameCache = Caffeine.newBuilder().build();
        servletContextEvent.getServletContext().setAttribute("idNameCache", idNameCache);
    }

}
