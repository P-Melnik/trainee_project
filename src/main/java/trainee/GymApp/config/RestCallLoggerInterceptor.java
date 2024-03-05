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
        System.out.println("In interceptor 1!");
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("In interceptor 2!");
        log.info("RequestURL: {} ", requestURL.toString());
        System.out.println("In interceptor 3!");
        String requestMethod = request.getMethod();
        System.out.println("In interceptor 4!");
        log.info("Requested Method : {}", requestMethod);
        System.out.println("In interceptor 5!");
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
