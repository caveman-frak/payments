package uk.co.bluegecko.pay.upload.service.base;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.springframework.stereotype.Service;

import net.sf.flatpack.DataSet;
import net.sf.flatpack.Parser;
import net.sf.flatpack.brparse.BuffReaderParseFactory;
import uk.co.bluegecko.pay.bacs.std18.mapper.Standard18Mapper;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.upload.service.ParsingService;
import uk.co.bluegecko.pay.upload.service.StreamingService;


@Service
public class ParsingServiceBase implements ParsingService
{

	private final StreamingService streamingService;

	public ParsingServiceBase( final StreamingService streamingService )
	{
		super();

		this.streamingService = streamingService;
	}

	@Override
	public void parse( final InputStream inputStream ) throws IOException
	{
		final Standard18Mapper standard18Mapper = new Standard18Mapper();

		standard18Mapper.add( Row.INSTR,
				( final Row row, final Object value ) -> streamingService.sendInstruction( ( Instruction ) value ) );

		try (final Reader definition = standard18Mapper.mappingFile();
				Reader dataFile = new InputStreamReader( inputStream ))
		{
			final Parser parser = BuffReaderParseFactory.getInstance()
					.newFixedLengthParser( definition, dataFile );

			parser.setHandlingShortLines( true );
			parser.setIgnoreExtraColumns( true );
			parser.setNullEmptyStrings( true );
			final DataSet dataSet = parser.parse();

			while ( dataSet.next() )
			{
				standard18Mapper.map( dataSet.getRecord()
						.get() );
			}

		}
	}

}
