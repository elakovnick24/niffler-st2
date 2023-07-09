package guru.qa.niffler.helpers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatConverter {

    public static Date convertWebElementFormatToSpendJsonFormatDate(String webElementDate) {
        DateFormat spendsFormat = new SimpleDateFormat("dd MMM yy", new Locale("en", "EN"));
        try {
            return spendsFormat.parse(webElementDate);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

    }
    public static String convertSpendJsonFormatToWebElementFormatDate(Date spendJsonDate) {
        DateFormat webElementFormat = new SimpleDateFormat("dd MMM yy", new Locale("en", "EN"));
        return webElementFormat.format(spendJsonDate);
    }

}
