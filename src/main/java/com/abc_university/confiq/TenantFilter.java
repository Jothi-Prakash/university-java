package com.abc_university.confiq;

import jakarta.servlet.Filter;  
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TenantFilter implements Filter {

    private static final String TENANT_HEADER = "X-College-Header";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest) {
            String tenant = httpRequest.getHeader(TENANT_HEADER);

            if (tenant == null || tenant.isBlank()) {
                throw new ServletException("Missing or invalid 'X-College-Header'.");
            }

            TenantContext.setCurrentTenant(tenant);
        }

        try {
            chain.doFilter(request, response); 
        } finally {
            TenantContext.clear(); 
        }
    }
}
