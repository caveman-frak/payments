package uk.co.bluegecko.pay.portfolio.config;


import org.springframework.cloud.stream.converter.CompositeMessageConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.bluegecko.pay.common.annotation.TestProfile;


@TestProfile
@Configuration
public class TestConfig
{

	@Bean
	public CompositeMessageConverterFactory compositeMessageConverterFactory()
	{
		return new CompositeMessageConverterFactory();
	}

}
