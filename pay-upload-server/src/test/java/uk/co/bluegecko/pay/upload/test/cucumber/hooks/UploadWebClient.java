package uk.co.bluegecko.pay.upload.test.cucumber.hooks;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.FILE;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.UPLOAD;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import uk.co.bluegecko.pay.test.cucumber.CucumberComponent;
import uk.co.bluegecko.pay.test.cucumber.CucumberWebClient;


@CucumberComponent
public class UploadWebClient extends CucumberWebClient
{

	private final RestTemplate restTemplate;

	@Autowired
	public UploadWebClient( final RestTemplate restTemplate )
	{
		this.restTemplate = restTemplate;
	}

	public URI submitFileForUpload( final Path file ) throws RestClientException, URISyntaxException
	{
		final ResponseEntity< Void > result = restTemplate.exchange( host( UPLOAD ), HttpMethod.POST,
				multiPartEntity( FILE, file ), Void.class );

		assertThat( result.getStatusCode(), is( HttpStatus.ACCEPTED ) );
		return result.getHeaders()
				.getLocation();
	}

	public String requestFileStatus( final URI jobStatus )
	{
		final ResponseEntity< String > result = restTemplate.getForEntity( jobStatus, String.class );

		assertThat( result.getStatusCode(), is( HttpStatus.OK ) );
		return result.getBody();
	}

}
