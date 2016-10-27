package uk.co.bluegecko.pay.common.config;


import java.time.Clock;

import javax.validation.Validator;

import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class StandardConfiguration
{

	@Bean
	public Clock clock()
	{
		return Clock.systemUTC();
	}

	@Bean
	public ObjectMapper objectMapper( final Jackson2ObjectMapperBuilder builder )
	{
		return builder.createXmlMapper( false )
				.build()
				.registerModule( new JavaTimeModule() )
				.registerModule( new Jdk8Module() )
				.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS )
				.disable( SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS )
				.disable( DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS )
				.enable( SerializationFeature.INDENT_OUTPUT )
				.enable( SerializationFeature.WRITE_ENUMS_USING_TO_STRING )
				.enable( DeserializationFeature.READ_ENUMS_USING_TO_STRING )
				.enable( DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS )
				.enable( JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN )
				.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES )
				.setSerializationInclusion( Include.NON_EMPTY )
				.enable( MapperFeature.DEFAULT_VIEW_INCLUSION )
				.setVisibility( PropertyAccessor.ALL, Visibility.NONE )
				.setVisibility( PropertyAccessor.FIELD, Visibility.ANY );
	}

	@Bean
	public Validator validatorFactory()
	{
		return new LocalValidatorFactoryBean();
	}

}