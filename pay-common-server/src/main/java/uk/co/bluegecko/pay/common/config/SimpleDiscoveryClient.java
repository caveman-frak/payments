package uk.co.bluegecko.pay.common.config;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.EmbeddedWebServer;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ClassUtils;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public final class SimpleDiscoveryClient implements DiscoveryClient
{

	private final ServiceInstance serviceInstance;

	public SimpleDiscoveryClient( final ApplicationContext context, final Environment environment,
			final ServerProperties server )
	{
		serviceInstance = new DefaultServiceInstance( name( environment ), host(), port( context, server ), false );
	}

	protected String name( final Environment environment )
	{
		return environment.getProperty( "spring.application.name", "application" );
	}

	protected String host()
	{
		String host = "localhost";
		try
		{
			host = InetAddress.getLocalHost()
					.getHostName();
		}
		catch ( final UnknownHostException e )
		{
			log.warn( "Cannot get host info: (" + e.getMessage() + ")" );
		}
		return host;
	}

	private int port( final ApplicationContext context, final ServerProperties server )
	{
		final int port = port( server );
		return port == 0 ? port( context ) : port;
	}

	protected int port( final ServerProperties server )
	{
		int port = 0;
		if ( server != null && server.getPort() != null )
		{
			port = server.getPort();
		}
		return port;
	}

	protected int port( final ApplicationContext context )
	{
		if ( ClassUtils.isPresent( "org.springframework.web.context.support.GenericWebApplicationContext", null ) )
		{
			if ( context instanceof EmbeddedWebApplicationContext )
			{
				final EmbeddedWebServer container = ( ( EmbeddedWebApplicationContext ) context )
						.getEmbeddedWebServer();
				if ( container != null )
				{
					return container.getPort();
				}
			}
		}
		else
		{
			// Apparently spring-web is not on the classpath
			if ( log.isDebugEnabled() )
			{
				log.debug( "Could not locate port in embedded container (spring-web not available)" );
			}
		}
		return 0;
	}

	@Override
	public String description()
	{
		return "Spring Cloud Simple DiscoveryClient";
	}

	@SuppressWarnings( "deprecation" )
	@Deprecated
	@Override
	public ServiceInstance getLocalServiceInstance()
	{
		return serviceInstance;
	}

	@Override
	public List< ServiceInstance > getInstances( final String serviceId )
	{
		return Collections.emptyList();
	}

	@Override
	public List< String > getServices()
	{
		return Collections.emptyList();
	}

}