package uk.co.bluegecko.pay.upload.service.base;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Service;

import net.sf.flatpack.DataSet;
import net.sf.flatpack.Parser;
import net.sf.flatpack.Record;
import net.sf.flatpack.brparse.BuffReaderParseFactory;
import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;
import uk.co.bluegecko.pay.upload.readers.InstructionReader;
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
		try (final InputStreamReader definition = new InputStreamReader(
				getClass().getResourceAsStream( "/mapping/instruction.pzmap.xml" ) );
				InputStreamReader dataFile = new InputStreamReader( inputStream ))
		{
			final Parser parser = BuffReaderParseFactory.getInstance()
					.newFixedLengthParser( definition, dataFile );
			parser.setHandlingShortLines( true );
			parser.setIgnoreExtraColumns( true );
			parser.setNullEmptyStrings( true );
			final DataSet dataSet = parser.parse();

			int index = 1;
			while ( dataSet.next() )
			{
				final Record record = dataSet.getRecord()
						.get();

				if ( record.isRecordID( "VOL1" ) )
				{
					System.out.println( "------------------- VOL1" );
				}
				else if ( record.isRecordID( "HRD1" ) )
				{
					System.out.println( "------------------- HRD1" );
				}
				else if ( record.isRecordID( "HDR2" ) )
				{
					System.out.println( "------------------- HRD2" );
				}
				else if ( record.isRecordID( "UHL1" ) )
				{
					System.out.println( "------------------- UHL1" );
				}
				else if ( record.isRecordID( "EOF1" ) )
				{
					System.out.println( "------------------- EOF1" );
				}
				else if ( record.isRecordID( "EOF2" ) )
				{
					System.out.println( "------------------- EOF2" );
				}
				else if ( record.isRecordID( "UTL1" ) )
				{
					System.out.println( "------------------- UTL1" );
				}
				else if ( record.isRecordID( "CONTRA" ) )
				{
					System.out.println( "------------------- CONTRA" );
				}
				else
				{
					System.out.println( "------------------- INSTR" );
					final Instruction instruction = new InstructionReader().read( record, index++ );
					streamingService.send( instruction );
				}
			}

		}
	}

}
