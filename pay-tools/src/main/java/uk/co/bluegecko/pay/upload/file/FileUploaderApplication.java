package uk.co.bluegecko.pay.upload.file;


import java.io.File;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class FileUploaderApplication
{

	public static void main( final String... args )
	{
		SpringApplication.run( FileUploaderApplication.class, args );
	}

	@Bean
	RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

	@Autowired
	RestTemplate restTemplate;

	@Bean
	CommandLineRunner processArguments()
	{
		return ( args ) ->
			{
				if ( args.length > 0 )
				{
					for ( final String arg : args )
					{
						processArgument( arg );
					}
				}
				else
				{
					System.err.printf( "Usage: file [,file]...\n" );
				}
			};
	}

	protected void processArgument( final String arg )
	{
		final File file = new File( arg );

		if ( !file.isFile() || !file.exists() )
		{
			System.err.printf( "Unable to locate file '%s'\n", file.getName() );
		}
		else if ( !file.canRead() )
		{
			System.err.printf( "Unable to read file '%s'\n", file.getName() );
		}
		else
		{
			uploadFile( file );
		}
	}

	protected void uploadFile( final File file )
	{
		final MultiValueMap< String, Object > map = new LinkedMultiValueMap<>();
		map.add( "file", new FileSystemResource( file ) );

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.MULTIPART_FORM_DATA );

		final HttpEntity< MultiValueMap< String, Object > > requestEntity = new HttpEntity<>( map, headers );
		final URI url = URI.create( "http://red-dragon.local:8081/upload/" );
		final ResponseEntity< Void > result = restTemplate.exchange( url, HttpMethod.POST, requestEntity, Void.class );

		System.out.printf( "Uploaded '%s' with response %s\n", file.getAbsolutePath(), result.getStatusCode() );
		System.out.printf( "Redirect to '%s\n'", result.getHeaders().getLocation() );
	}

}
