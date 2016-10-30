package uk.co.bluegecko.pay.upload.service.base;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;
import uk.co.bluegecko.pay.upload.service.ParsingService;
import uk.co.bluegecko.pay.upload.service.StreamingService;


public class ParsingServiceBaseTest
{

	private ParsingService parsingService;
	private StreamingService streamingService;

	@Before
	public void setUp() throws Exception
	{
		streamingService = mock( StreamingService.class );
		parsingService = new ParsingServiceBase( streamingService );
	}

	@Test
	public final void testSingleInstruction() throws IOException
	{
		final ArgumentCaptor< Instruction > argument = ArgumentCaptor.forClass( Instruction.class );

		final String line = "0100390105996309940202421315692000000000000006BSDSAF 00000000006REF&LT 00000000006NAME   00000000006 14308     ";
		try (InputStream in = new ByteArrayInputStream( line.getBytes() ))
		{
			parsingService.parse( in );

			verify( streamingService, times( 1 ) ).send( argument.capture(), eq( 1 ) );

			final Instruction instruction = argument.getValue();

			assertThat( instruction.destination()
					.sortCode(), is( "010039" ) );
			assertThat( instruction.destination()
					.number(), is( "01059963" ) );
			assertThat( instruction.destination()
					.name(), is( "NAME   00000000006" ) );
			assertThat( instruction.destination()
					.type(), is( "0" ) );
			assertThat( instruction.origin()
					.sortCode(), is( "402024" ) );
			assertThat( instruction.origin()
					.number(), is( "21315692" ) );
			assertThat( instruction.transactionType(), is( "99" ) );
			assertThat( instruction.amount(), is( new BigDecimal( "0.06" ) ) );
			assertThat( instruction.processingDate(), is( LocalDate.of( 2009, Month.MARCH, 5 ) ) );
			assertThat( instruction.reference(), is( "REF&LT 00000000006" ) );
		}

	}

	@Test
	public final void testInstructionFile() throws IOException
	{
		final ArgumentCaptor< Instruction > argument = ArgumentCaptor.forClass( Instruction.class );

		try (InputStream in = getClass().getResourceAsStream( "/sample-files/BTE_DEF.100101.1.1" ))
		{
			parsingService.parse( in );

			verify( streamingService, times( 1 ) ).send( argument.capture(), eq( 5 ) );

			assertThat( argument.getValue()
					.reference(), is( "REF&LT 00000000006" ) );
		}

	}

}
