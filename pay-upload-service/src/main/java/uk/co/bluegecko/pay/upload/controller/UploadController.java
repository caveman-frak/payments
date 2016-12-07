package uk.co.bluegecko.pay.upload.controller;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.FILE;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.JOB_ID;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.STATUS;
import static uk.co.bluegecko.pay.v1.upload.rest.UploadMapping.UPLOAD;

import java.io.IOException;
import java.net.URI;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import uk.co.bluegecko.pay.common.controller.AbstractController;
import uk.co.bluegecko.pay.upload.service.UploadService;


@RestController
@MultipartConfig( maxFileSize = 10_485_760 )
public class UploadController extends AbstractController
{

	private final UploadService uploadService;

	@Autowired
	public UploadController( final UploadService uploadService )
	{
		super();

		this.uploadService = uploadService;
	}

	// TODO waiting for hateos fix for handling @<Method>Mapping
	// @PostMapping( UPLOAD )
	@RequestMapping( path = UPLOAD, method = RequestMethod.POST )
	public HttpEntity< Void > handleFileUpload( @RequestParam( FILE ) final MultipartFile file ) throws IOException
	{
		final Long jobId = uploadService.processFile( file );

		final URI location = linkTo( methodOn( UploadController.class ).getJobStatus( jobId ) ).toUri();
		return ResponseEntity.accepted()
				.location( location )
				.build();
	}

	// @GetMapping( STATUS )
	@RequestMapping( path = STATUS, method = RequestMethod.GET )
	public HttpEntity< String > getJobStatus( @PathVariable( JOB_ID ) final long jobId )
	{
		final String body = uploadService.getJobStatus( jobId );
		return ResponseEntity.ok()
				.contentType( MediaType.TEXT_PLAIN )
				.body( body );
	}

}
