package server.stutino.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class CustomDateUtil {
    public LocalDate convertStringToLocalDate(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ISO_DATE);
    }
    public LocalDateTime convertStringToLocalDateTime(String datetime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(datetime, formatter);
    }

    public String localDateToString (LocalDate date) {
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return date.format(formater);
    }
}
