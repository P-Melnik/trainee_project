package trainee.GymApp.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RestCallLoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        StringBuffer requestURL = request.getRequestURL();
        log.info("RequestURL: {} ", requestURL.toString());
        String requestMethod = request.getMethod();
        log.info("Requested Method : {}", requestMethod);
        return true;
    }

    @Override
    public void afterCompletion(@NotNull HttpServletRequest request, HttpServletResponse response, @NotNull Object handler, Exception ex) {
        int status = response.getStatus();
        log.info("Response status: {}", status);
        if (ex != null) {
            log.error("Exception during request processing ", ex);
        }
    }
}
