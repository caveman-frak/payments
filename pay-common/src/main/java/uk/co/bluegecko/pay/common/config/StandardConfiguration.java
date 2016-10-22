package uk.co.bluegecko.pay.common.config;


import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Configuration
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
		final ObjectMapper mapper = builder.createXmlMapper( false ).build();
		mapper.registerModule( new JavaTimeModule() );
		mapper.registerModule( new Jdk8Module() );
		mapper.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
		mapper.disable( SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS );
		mapper.disable( DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS );
		return mapper;
	}

}
