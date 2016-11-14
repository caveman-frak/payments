package uk.co.bluegecko.pay.test.harness;


import java.io.IOException;
import java.time.Clock;
import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.ClassRule;
import org.junit.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import uk.co.bluegecko.pay.common.config.StandardConfiguration;
import uk.co.bluegecko.pay.test.FixedDates;
import uk.co.bluegecko.pay.test.harness.TestHarness.TestConfig;


@ContextConfiguration( classes =
	{ TestConfig.class } )
@ActiveProfiles( "test" )
public abstract class TestHarness implements FixedDates
{

	private static final Pattern REGEX_WHITESPACE = Pattern.compile( "\\s" );

	@ClassRule
	public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

	@Rule
	public final SpringMethodRule springMethodRule = new SpringMethodRule();

	@Autowired
	private Clock clock;

	@Autowired
	@Qualifier( "objectMapper" )
	private ObjectMapper mapper;

	@Autowired
	private Validator validator;

	@TestConfiguration
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

	protected Clock clock()
	{
		return clock;
	}

	protected ObjectMapper mapper()
	{
		return mapper;
	}

	protected Validator validator()
	{
		return validator;
	}

	protected String stripWhitespace( final String str )
	{
		return REGEX_WHITESPACE.matcher( str )
				.replaceAll( "" );
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

	protected String write( final Object o ) throws JsonProcessingException
	{
		return mapper.writeValueAsString( o );
	}

	protected String write( final Class< ? > view, final Object o ) throws JsonProcessingException
	{
		return mapper.writerWithView( view )
				.writeValueAsString( o );
	}

	protected < T > T read( final String str, final Class< T > type )
			throws IOException, JsonParseException, JsonMappingException
	{
		return mapper.readValue( str, type );
	}

}
