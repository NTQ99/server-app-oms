package ntq.uet.server.common.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil extends DateUtils {
    public DateUtil() {
    }

    public static String getDateTime(Date date, int... daysOffset) {
        if (date == null) {
            date = new Date();
        }

        if (daysOffset != null && daysOffset.length > 0) {
            date = addDays(date, daysOffset[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return sdf.format(date);
    }

    public static String getDate(Date date, int... daysOffset) {
        if (date == null) {
            date = new Date();
        }

        if (daysOffset != null && daysOffset.length > 0) {
            date = addDays(date, daysOffset[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
