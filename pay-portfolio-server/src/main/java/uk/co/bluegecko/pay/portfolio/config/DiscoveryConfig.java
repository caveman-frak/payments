package uk.co.bluegecko.pay.portfolio.config;


import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import uk.co.bluegecko.pay.common.config.SimpleDiscoveryClient;


@Configuration
public class DiscoveryConfig
{

	@Bean
	public DiscoveryClient discoveryClient( final ApplicationContext context, final Environment environment )
	{
		return new SimpleDiscoveryClient( context, environment, null );
	}

}
