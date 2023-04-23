package tuk.mentor.global.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class StringUtil {
    public String toString(Object obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        StringBuilder sb = new StringBuilder();
        sb.append(clazz.getSimpleName() + " [");
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                sb.append(field.getName() + "=" + field.get(obj) + ", ");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        sb.delete(sb.length() - 2, sb.length()); // 마지막 ", " 제거
        sb.append("]");

        return sb.toString();
    }
}
