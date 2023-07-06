package server.stutino.interceptor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;

public class NullCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            if (handlerMethod.getMethod().isAnnotationPresent(PostMapping.class)) {
                Object requestBody = request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);

                if (requestBody != null) {
                    Field[] fields = requestBody.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object fieldValue = field.get(requestBody);
                        if (fieldValue == null) {
                            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                            return false; // 요청 처리 중단
                        }
                    }
                }
            }
        }
        return true;
    }
}

