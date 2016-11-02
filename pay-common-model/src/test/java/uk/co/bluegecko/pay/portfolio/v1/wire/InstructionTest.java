package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction.InstructionBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.view.View;


public class InstructionTest extends TestHarness
{

	private InstructionBuilder instructionBuilder;

	@Before
	public void setUp() throws Exception
	{
		final Account origin = Account.builder()
				.sortCode( "123456" )
				.number( "12345678" )
				.name( "B.BAGGINS" )
				.build();
		final Account destination = Account.builder()
				.sortCode( "654321" )
				.number( "87654321" )
				.type( "8" )
				.build();

		instructionBuilder = Instruction.builder()
				.index( 1 )
				.lineNo( 3 )
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
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

		final String str = mapper.writeValueAsString( instruction );

		assertThat( stripWhitespace( str ),
				is( "{\"index\":1,\"lineNo\":3,"
						+ "\"origin\":{\"sortCode\":\"123456\",\"number\":\"12345678\",\"name\":\"B.BAGGINS\"},"
						+ "\"destination\":{\"sortCode\":\"654321\",\"number\":\"87654321\",\"type\":\"8\"},"
						+ "\"transactionType\":\"99\",\"amount\":10.01,\"reference\":\"A-REFERENCE\","
						+ "\"processingDate\":\"2015-06-01\"}" ) );

		final Instruction result = mapper.readValue( str, Instruction.class );

		assertThat( result.origin()
				.sortCode(), is( "123456" ) );
		assertThat( result.destination()
				.number(), is( "87654321" ) );
		assertThat( result.index(), is( 1 ) );
		assertThat( result.lineNo(), is( 3 ) );
		assertThat( result.reference(), is( "A-REFERENCE" ) );
		assertThat( result.transactionType(), is( "99" ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Instruction instruction = instructionBuilder.build();

		final String str = mapper.writerWithView( View.Standard.class )
				.writeValueAsString( instruction );

		assertThat( stripWhitespace( str ),
				is( "{\"index\":1,\"origin\":{\"sortCode\":\"123456\",\"number\":\"12345678\"},"
						+ "\"destination\":{\"sortCode\":\"654321\",\"number\":\"87654321\"},"
						+ "\"transactionType\":\"99\",\"amount\":10.01,\"reference\":\"A-REFERENCE\","
						+ "\"processingDate\":\"2015-06-01\"}" ) );

		final Instruction result = mapper.readValue( str, Instruction.class );

		assertThat( result.origin()
				.sortCode(), is( "123456" ) );
		assertThat( result.destination()
				.number(), is( "87654321" ) );
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
	public final void testValidationFailReference()
	{
		assertThat( isValid( instructionBuilder.reference( "A12345__12" )
				.build() ), is( false ) );
	}

}
