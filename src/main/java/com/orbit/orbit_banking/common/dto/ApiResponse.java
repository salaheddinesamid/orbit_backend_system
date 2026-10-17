package com.orbit.orbit_banking.common.dto;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Standard envelope for every REST response.
 * Success: success=true, data set, error null.
 * Failure: success=false, error set, data null.
 */
public record ApiResponse<T>(
        boolean success,
        T data,
        ApiError error,
        Instant timestamp,
        String requestId) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null, Instant.now(), MDC.get(RequestIdFilter.MDC_KEY));
    }

    public static ApiResponse<Void> ok() {
        return new ApiResponse<>(true, null, null, Instant.now(), MDC.get(RequestIdFilter.MDC_KEY));
    }

    public static <T> ApiResponse<T> failure(String code, String message) {
        return failure(code, message, List.of());
    }

    public static <T> ApiResponse<T> failure(String code, String message, List<FieldViolation> details) {
        return new ApiResponse<>(false, null, new ApiError(code, message, details),
                Instant.now(), MDC.get(RequestIdFilter.MDC_KEY));
    }

    /** Stable machine-readable code plus a human-readable message. */
    public record ApiError(String code, String message, List<FieldViolation> details) {
    }

    public record FieldViolation(String field, String message) {
    }
}

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class RequestIdFilter extends OncePerRequestFilter {

    public static final String HEADER = "X-Request-Id";
    public static final String MDC_KEY = "requestId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String id = request.getHeader(HEADER);
        if (id == null || !id.matches("[A-Za-z0-9\\-]{8,64}")) {
            id = UUID.randomUUID().toString();
        }
        MDC.put(MDC_KEY, id);
        response.setHeader(HEADER, id);
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}