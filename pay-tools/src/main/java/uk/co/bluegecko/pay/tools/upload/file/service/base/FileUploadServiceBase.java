package uk.co.bluegecko.pay.tools.upload.file.service.base;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import uk.co.bluegecko.pay.tools.upload.file.cli.UploadCmdLine;
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
	public void processFiles( final UploadCmdLine commandLine, final FileSystem fileSystem ) throws IOException
	{
		final URI host = commandLine.host();

		checkConnection( host );

		processFiles( commandLine.arguments()
				.stream(), commandLine.directory(), fileSystem, host );
	}

	protected void processFiles( final Stream< String > stream, final String baseDir, final FileSystem fileSystem,
			final URI host )
	{
		stream.map( arg -> fileSystem.getPath( baseDir, arg ) )
				.filter( file -> isFileValid( file ) )
				.forEach( file -> uploadFile( host, file ) );
	}

	protected void checkConnection( final URI host ) throws IOException
	{
		try (final Socket testSocket = new Socket())
		{
			testSocket.connect( new InetSocketAddress( host.getHost(), host.getPort() ), 500 );
		}
	}

	protected boolean isFileValid( final Path file )
	{
		if ( !Files.exists( file, LinkOption.NOFOLLOW_LINKS ) || !Files.isRegularFile( file, LinkOption.NOFOLLOW_LINKS )
				|| !Files.isReadable( file ) )
		{
			logger.error( "Unable to locate or read file '{}'", file.toString() );
			return false;
		}
		else
		{
			return true;
		}
	}

	protected void uploadFile( final URI host, final Path file )
	{
		final MultiValueMap< String, Object > map = new LinkedMultiValueMap<>();
		map.add( "file", new PathResource( file ) );

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType( MediaType.MULTIPART_FORM_DATA );

		final HttpEntity< MultiValueMap< String, Object > > requestEntity = new HttpEntity<>( map, headers );
		final ResponseEntity< Void > result = restTemplate.exchange( host.resolve( UPLOAD ), HttpMethod.POST,
				requestEntity, Void.class );

		logger.warn( "Uploaded '{}' with response {}", file.toString(), result.getStatusCode() );
		logger.warn( "Redirect to '{}'", result.getHeaders()
				.getLocation() );
	}

}
