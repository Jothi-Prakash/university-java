package com.university_java.config;

import jakarta.servlet.Filter;   
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "X-College-Header";
    private static final Logger logger = LoggerFactory.getLogger(TenantFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

           
            String requestURI = httpRequest.getRequestURI();
            if (requestURI.contains("/swagger-ui/index.html") || requestURI.contains("/v3/api-docs")) {
                chain.doFilter(request, response);
                return;
            }


            
            String tenant = httpRequest.getHeader(TENANT_HEADER);

            
            if (tenant == null || tenant.isBlank()) {
                logger.error("Missing or invalid '{}'", TENANT_HEADER);
                ((HttpServletResponse) response).sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        String.format("Missing or invalid '%s' header.", TENANT_HEADER)
                );
                return;
            }

          
            logger.info("Processing request for tenant: {}", tenant);
            TenantContext.setCurrentTenant(tenant);
        }

        try {
          
            chain.doFilter(request, response);
        } finally {
            
            TenantContext.clear();
            logger.info("Tenant context cleared");
        }
    }

}
