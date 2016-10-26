package uk.co.bluegecko.pay.upload.service.base;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uk.co.bluegecko.pay.upload.service.UploadService;


@Service
public class UploadServiceBase implements UploadService
{

	private static final Logger logger = LoggerFactory.getLogger( UploadService.class );

	private final Source source;

	@Autowired
	public UploadServiceBase( final Source source )
	{
		super();

		this.source = source;
	}

	@Override
	public long processFile( final MultipartFile file ) throws IOException
	{
		logger.info( "processing: {}", file.getOriginalFilename() );

		try (BufferedReader reader = new BufferedReader( new InputStreamReader( file.getInputStream() ) ))
		{
			String line;
			int i = 0;
			while ( ( line = reader.readLine() ) != null )
			{
				source.output().send( MessageBuilder.withPayload( line ).setSequenceNumber( i++ ).build() );
				logger.info( "sent: {}", line );
			}
		}

		return 0;
	}

	@Override
	public String getJobStatus( final long jobId )
	{
		return "COMPLETED:" + jobId;
	}

}
