package uk.co.bluegecko.pay.upload.service.base;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uk.co.bluegecko.pay.upload.service.ParsingService;
import uk.co.bluegecko.pay.upload.service.UploadService;


@Service
public class UploadServiceBase implements UploadService
{

	private static final Logger logger = LoggerFactory.getLogger( UploadService.class );

	private final ParsingService parsingService;

	@Autowired
	public UploadServiceBase( final ParsingService parsingService )
	{
		super();

		this.parsingService = parsingService;
	}

	@Override
	public long processFile( final MultipartFile file ) throws IOException
	{
		logger.info( "processing: {}", file.getOriginalFilename() );

		parsingService.parse( file.getInputStream() );

		return 0;
	}

	@Override
	public String getJobStatus( final long jobId )
	{
		return "COMPLETED:" + jobId;
	}

}
