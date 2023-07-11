package by.akoltovich.task.util;

import java.sql.Date;
import java.time.LocalDateTime;

public class ValidationUtil {

    public static boolean validateDate(Date date, Integer age) {
        Date currentDate = Date.valueOf(LocalDateTime.now().toLocalDate());
        return date.before(currentDate) && date.getYear() == currentDate.getYear() - age;
    }

    public static boolean validateDateFormat(String dateFormat) {
        return dateFormat.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static boolean validateAgeField(String age) {
        return age.matches("\\d+");
    }

}
