package io.jutil.web.common.core.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @author Jin Zheng
 * @since 2022-12-20
 */
public class DateUtil {
	private DateUtil() {
	}

    /**
     * 当前时间，精确到秒
     *
     * @return
     */
    public static LocalDateTime nowWithSecond() {
        var now = LocalDateTime.now();
        return now.truncatedTo(ChronoUnit.SECONDS);
    }

}
