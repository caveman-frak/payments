package uk.co.bluegecko.pay.upload.service.base;


import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import uk.co.bluegecko.pay.bacs.std18.mapper.Standard18Mapper;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.common.service.ParsingService;
import uk.co.bluegecko.pay.upload.service.StreamingService;
import uk.co.bluegecko.pay.upload.service.UploadService;


@Service
public class UploadServiceBase implements UploadService
{

	private static final Logger logger = LoggerFactory.getLogger( UploadService.class );

	private final ParsingService parsingService;
	private final StreamingService streamingService;

	@Autowired
	public UploadServiceBase( final ParsingService parsingService, final StreamingService streamingService )
	{
		super();

		this.parsingService = parsingService;
		this.streamingService = streamingService;
	}

	@Override
	public long processFile( final MultipartFile file ) throws IOException
	{
		final Standard18Mapper standard18Mapper = new Standard18Mapper();

		standard18Mapper.add( Row.INSTR,
				( final Row row, final Object value ) -> streamingService.sendInstruction( ( Instruction ) value ) );

		logger.info( "processing: {}", file.getOriginalFilename() );

		parsingService.parse( new InputStreamReader( file.getInputStream(), StandardCharsets.UTF_8 ),
				standard18Mapper.mappingFile(), standard18Mapper );

		return 0;
	}

	@Override
	public String getJobStatus( final long jobId )
	{
		return "COMPLETED:" + jobId;
	}

}
