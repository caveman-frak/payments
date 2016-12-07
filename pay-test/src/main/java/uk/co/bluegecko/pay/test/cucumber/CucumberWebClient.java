package uk.co.bluegecko.pay.test.cucumber;


import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public abstract class CucumberWebClient
{

	private static final String SCHEME = "http";

	@Autowired
	private int port;

	@Autowired
	private String host;

	protected URI host( final String path ) throws URISyntaxException
	{
		return new URI( SCHEME, null, host, port, path, null, null );
	}

	protected String host() throws URISyntaxException
	{
		return new URI( SCHEME, null, host, port, null, null, null ).toString();
	}

	protected HttpEntity< MultiValueMap< String, Object > > multiPartEntity( final String name, final Path file )
	{
		final MultiValueMap< String, Object > map = new LinkedMultiValueMap<>();
		map.add( name, new PathResource( file ) );

		return multiPartEntity( map );
	}

	protected HttpEntity< MultiValueMap< String, Object > > multiPartEntity( final MultiValueMap< String, Object > map )
	{
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.MULTIPART_FORM_DATA );

		final HttpEntity< MultiValueMap< String, Object > > requestEntity = new HttpEntity<>( map, headers );
		return requestEntity;
	}

	@Configuration
	public static class CucumberConfig
	{

		@Bean
		@CucumberScope
		public int port( final Environment environment )
		{
			return Integer.valueOf( environment.getProperty( "test.http.port", "80" ) );
		}

		@Bean
		@CucumberScope
		public String host( final Environment environment )
		{
			return environment.getProperty( "test.http.host", "localhost" );
		}

		@Bean
		@CucumberScope
		public RestTemplateBuilder restTemplateBuilder()
		{
			return new RestTemplateBuilder();
		}

		@Bean
		@CucumberScope
		public TestRestTemplate testRestTemplate( final RestTemplateBuilder restTemplateBuilder )
		{
			return new TestRestTemplate( restTemplateBuilder );
		}

		@Bean
		@CucumberScope
		public RestTemplate restTemplate( final TestRestTemplate testRestTemplate )
		{
			return testRestTemplate.getRestTemplate();
		}

	}

}
