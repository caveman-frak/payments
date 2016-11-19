package uk.co.bluegecko.pay.common.service.base;


import java.io.IOException;
import java.io.Reader;

import org.springframework.stereotype.Service;

import net.sf.flatpack.DataSet;
import net.sf.flatpack.Parser;
import net.sf.flatpack.brparse.BuffReaderParseFactory;
import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingService;


@Service
public class ParsingServiceBase implements ParsingService
{

	@Override
	public void parse( final Reader dataFile, final Reader mappingFile, final Mapper mapper ) throws IOException
	{
		final Parser parser = BuffReaderParseFactory.getInstance()
				.newFixedLengthParser( mappingFile, dataFile );

		parser.setHandlingShortLines( true );
		parser.setIgnoreExtraColumns( true );
		parser.setNullEmptyStrings( true );
		final DataSet dataSet = parser.parse();

		while ( dataSet.next() )
		{
			mapper.map( dataSet.getRecord()
					.get() );
		}
	}

}
