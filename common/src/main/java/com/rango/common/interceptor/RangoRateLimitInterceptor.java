package com.rango.common.interceptor;

import com.rango.common.limit.SemaphoreRateLimit;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
@Data
public class RangoRateLimitInterceptor implements HandlerInterceptor {

    private SemaphoreRateLimit semaphoreRateLimit;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return semaphoreRateLimit.acquire(request.getRequestURI());
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        semaphoreRateLimit.release(request.getRequestURI());
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
