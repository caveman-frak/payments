package uk.co.bluegecko.pay.test.harness;


import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.bluegecko.pay.common.config.StandardConfiguration;
import uk.co.bluegecko.pay.test.harness.TestHarness.TestConfig;


@ContextConfiguration( classes =
	{ TestConfig.class } )
@ActiveProfiles( "test" )
public abstract class TestHarness
{

	public static LocalDate DATE = LocalDate.of( 2015, 6, 1 );
	public static LocalTime TIME = LocalTime.of( 12, 5, 30, 500 );
	public static LocalDateTime DATE_TIME = LocalDateTime.of( DATE, TIME );
	public static ZoneOffset ZONE = ZoneOffset.UTC;
	public static OffsetDateTime DATE_TIME_ZONE = OffsetDateTime.of( DATE, TIME, ZONE );

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	protected Clock clock;

	@Autowired
	@Qualifier( "objectMapper" )
	protected ObjectMapper mapper;

	@Configuration
	public static class TestConfig extends StandardConfiguration
	{

		@Override
		@Bean
		public Clock clock()
		{
			return Clock.fixed( DATE_TIME_ZONE.toInstant(), ZONE );
		}

		@Bean
		public Jackson2ObjectMapperBuilder objectMapperBuilder()
		{
			return new Jackson2ObjectMapperBuilder();
		}

	}

}
