package uk.co.bluegecko.pay.upload.controller;


import java.io.IOException;
import java.net.URI;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import uk.co.bluegecko.pay.upload.service.UploadService;


@RestController
@MultipartConfig( maxFileSize = 10485760 )
public class UploadController
{

	protected static final String UPLOAD = "/upload/";
	protected static final String STATUS = "/status/{jobId}";

	private final UploadService uploadService;

	@Autowired
	public UploadController( final UploadService uploadService )
	{
		super();

		this.uploadService = uploadService;
	}

	@PostMapping( UPLOAD )
	public HttpEntity< Void > handleFileUpload( @RequestParam( "file" ) final MultipartFile file ) throws IOException
	{
		final Long jobId = uploadService.processFile( file );

		final URI location = UriComponentsBuilder.fromPath( STATUS ).buildAndExpand( jobId ).toUri();
		return ResponseEntity.accepted().location( location ).build();
	}

	@GetMapping( STATUS )
	public HttpEntity< String > getJobStatus( @PathVariable( "jobId" ) final long jobId )
	{
		final String body = uploadService.getJobStatus( jobId );
		return ResponseEntity.ok().contentType( MediaType.TEXT_PLAIN ).body( body );
	}

}
