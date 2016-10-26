package uk.co.bluegecko.pay.test.harness;


import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.bluegecko.pay.common.config.StandardConfiguration;
import uk.co.bluegecko.pay.test.harness.TestHarness.TestConfig;


@ContextConfiguration( classes =
	{ TestConfig.class } )
@ActiveProfiles( "test" )
public abstract class TestHarness
{

	protected static LocalDate DATE = LocalDate.of( 2015, 6, 1 );
	protected static LocalTime TIME = LocalTime.of( 12, 5, 30, 500 );
	protected static LocalDateTime DATE_TIME = LocalDateTime.of( DATE, TIME );
	protected static ZoneOffset ZONE = ZoneOffset.UTC;
	protected static OffsetDateTime DATE_TIME_ZONE = OffsetDateTime.of( DATE, TIME, ZONE );

	private static final Pattern REGEX_WHITESPACE = Pattern.compile( "\\s" );

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	protected Clock clock;

	@Autowired
	@Qualifier( "objectMapper" )
	protected ObjectMapper mapper;

	@Autowired
	private Validator validator;

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

	protected String stripWhitespace( final String str )
	{
		return REGEX_WHITESPACE.matcher( str ).replaceAll( "" );
	}

	protected < T > Set< ConstraintViolation< T > > validate( final T model )
	{
		final Set< ConstraintViolation< T > > violations = validator.validate( model );
		return violations;
	}

	protected < T > boolean isValid( final T model )
	{
		return CollectionUtils.isEmpty( validator.validate( model ) );
	}

}
