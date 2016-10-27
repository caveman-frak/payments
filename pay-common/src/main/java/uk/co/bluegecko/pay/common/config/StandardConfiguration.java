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
		final ObjectMapper objectMapper = builder.createXmlMapper( false )
				.build();

		objectMapper.registerModule( new JavaTimeModule() );
		objectMapper.registerModule( new Jdk8Module() );
		objectMapper.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
		objectMapper.disable( SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS );
		objectMapper.disable( DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS );
		objectMapper.enable( SerializationFeature.INDENT_OUTPUT );
		objectMapper.enable( SerializationFeature.WRITE_ENUMS_USING_TO_STRING );
		objectMapper.enable( DeserializationFeature.READ_ENUMS_USING_TO_STRING );
		objectMapper.enable( DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS );
		objectMapper.enable( JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN );
		objectMapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );
		objectMapper.setSerializationInclusion( Include.NON_EMPTY );
		objectMapper.enable( MapperFeature.DEFAULT_VIEW_INCLUSION );
		objectMapper.setVisibility( PropertyAccessor.ALL, Visibility.NONE );
		objectMapper.setVisibility( PropertyAccessor.FIELD, Visibility.ANY );

		return objectMapper;
	}

	@Bean
	public Validator validatorFactory()
	{
		return new LocalValidatorFactoryBean();
	}

}