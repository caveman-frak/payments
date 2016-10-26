package uk.co.bluegecko.pay.portfolio.wire.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.wire.v1.Instruction.InstructionBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class InstructionTest extends TestHarness
{

	private InstructionBuilder instructionBuilder;

	@Before
	public void setUp() throws Exception
	{
		final Account origin = Account.builder()
				.sortCode( "123456" )
				.number( "12345678" )
				.build();
		final Account destination = Account.builder()
				.sortCode( "654321" )
				.number( "87654321" )
				.build();

		instructionBuilder = Instruction.builder()
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
				.serviceUserNumber( "123456" )
				.pence( "1001" )
				.julianDate( DATE.toEpochDay() )
				.reference( "A-REFERENCE" );
	}

	@Test
	public final void testBuilder()
	{
		final Instruction instruction = instructionBuilder.build();
		assertThat( instruction.amount(), is( new BigDecimal( "10.01" ) ) );
		assertThat( instruction.processingDate(), is( DATE ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Instruction instruction = instructionBuilder.build();

		final String str = stripWhitespace( mapper.writeValueAsString( instruction ) );

		assertThat( str,
				is( "{\"origin\":{\"sortCode\":\"123456\",\"number\":\"12345678\",\"type\":null},"
						+ "\"destination\":{\"sortCode\":\"654321\",\"number\":\"87654321\",\"type\":null},"
						+ "\"transactionType\":\"99\",\"amount\":10.01,\"serviceUserNumber\":\"123456\","
						+ "\"reference\":\"A-REFERENCE\",\"processingDate\":\"2015-06-01\"}" ) );

		final Instruction result = mapper.readValue( str, Instruction.class );

		assertThat( result.origin()
				.sortCode(), is( "123456" ) );
		assertThat( result.destination()
				.number(), is( "87654321" ) );
		assertThat( result.serviceUserNumber(), is( "123456" ) );
		assertThat( result.reference(), is( "A-REFERENCE" ) );
		assertThat( result.transactionType(), is( "99" ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( instructionBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationPassReference()
	{
		assertThat( isValid( instructionBuilder.reference( "ABC-123.8/9&0" )
				.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailServiceUserNumber()
	{
		assertThat( isValid( instructionBuilder.serviceUserNumber( "A12345" )
				.build() ), is( false ) );
	}

}
