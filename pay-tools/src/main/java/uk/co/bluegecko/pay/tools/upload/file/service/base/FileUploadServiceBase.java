package uk.co.bluegecko.pay.tools.upload.file.service.base;


import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import uk.co.bluegecko.pay.tools.upload.file.service.FileUploadService;


@Service
public class FileUploadServiceBase implements FileUploadService
{

	private static final String UPLOAD = "/upload/";
	private static final Logger logger = LoggerFactory.getLogger( FileUploadService.class );

	private final RestTemplate restTemplate;

	@Autowired
	public FileUploadServiceBase( final RestTemplate restTemplate )
	{
		super();

		this.restTemplate = restTemplate;
	}

	@Override
	public void uploadFile( final URI host, final File file )
	{
		final MultiValueMap< String, Object > map = new LinkedMultiValueMap<>();
		map.add( "file", new FileSystemResource( file ) );

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.MULTIPART_FORM_DATA );

		final HttpEntity< MultiValueMap< String, Object > > requestEntity = new HttpEntity<>( map, headers );
		final ResponseEntity< Void > result = restTemplate.exchange( host.resolve( UPLOAD ), HttpMethod.POST,
				requestEntity, Void.class );

		logger.warn( "Uploaded '{}' with response {}", file.getAbsolutePath(), result.getStatusCode() );
		logger.warn( "Redirect to '{}'", result.getHeaders().getLocation() );
	}

	@Override
	public void checkConnection( final URI host ) throws IOException
	{
		try (final Socket testSocket = new Socket())
		{
			testSocket.connect( new InetSocketAddress( host.getHost(), host.getPort() ), 500 );
		}
	}

	@Override
	public boolean isFileValid( final File file )
	{
		if ( !file.isFile() || !file.exists() )
		{
			logger.error( "Unable to locate file '{}'", file.getAbsolutePath() );
			return false;
		}
		else if ( !file.canRead() )
		{
			logger.error( "Unable to read file '{}'", file.getAbsolutePath() );
			return false;
		}
		else
		{
			return true;
		}
	}

}
