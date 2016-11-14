package uk.co.bluegecko.pay.test;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;


public interface FixedDates
{

	public static final int YEAR = 2015;
	public static final Month MONTH = Month.JUNE;
	public static final int DAY = 1;
	public static final int HOUR = 12;
	public static final int MINUTE = 5;
	public static final int SECOND = 30;
	public static final int MILLI = 500;

	public static final ZoneOffset ZONE = ZoneOffset.UTC;
	public static final ZoneId ZONE_ID = ZONE;

	public static final LocalDate DATE = LocalDate.of( YEAR, MONTH, DAY );
	public static final LocalTime TIME = LocalTime.of( HOUR, MINUTE, SECOND, MILLI );
	public static final LocalDateTime DATE_TIME = LocalDateTime.of( DATE, TIME );
	public static final OffsetTime TIME_ZONE = OffsetTime.of( TIME, ZONE );
	public static final OffsetDateTime DATE_TIME_ZONE = OffsetDateTime.of( DATE, TIME, ZONE );
	public static final ZonedDateTime DATE_TIME_ZONED = ZonedDateTime.of( DATE, TIME, ZONE );

}
