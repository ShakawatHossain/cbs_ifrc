package accuratesoft.shakawat.ifrc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class AddDays {

    public static String addDays(String inputDate, int days) {
        String outputDateString = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate date = LocalDate.parse(inputDate);
            LocalDate futureDate = date.plusDays(days);
            outputDateString = futureDate.toString();
        }else{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                // 1. Parse the string to a Date object
                Date date = dateFormat.parse(inputDate);

                // 2. Use a Calendar instance to perform date arithmetic
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                // 3. Add 30 days
                calendar.add(Calendar.DAY_OF_MONTH, days);

                // 4. Format the resulting Date back to a yyyy-MM-dd string
                outputDateString = dateFormat.format(calendar.getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return outputDateString;
    }
}
