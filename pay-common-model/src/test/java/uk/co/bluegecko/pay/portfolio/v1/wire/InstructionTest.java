package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Account;
import uk.co.bluegecko.pay.v1.portfolio.wire.Instruction;
import uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.InstructionBuilder;
import uk.co.bluegecko.pay.view.View;


public class InstructionTest extends TestHarness implements FakeDataConstants
{

	private InstructionBuilder instructionBuilder;

	@Before
	public void setUp() throws Exception
	{
		final Account origin = Account.builder()
				.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.build();
		final Account destination = Account.builder()
				.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.type( DEST_ACCT_TYPE )
				.build();

		instructionBuilder = Instruction.builder()
				.id( INSTRUCTION_ID )
				.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI );
	}

	@Test
	public final void testBuilder()
	{
		final Instruction instruction = instructionBuilder.build();
		assertThat( instruction.amount(), is( AMOUNT ) );
		assertThat( instruction.processingDate(), is( DATE ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Instruction instruction = instructionBuilder.build();

		final String str = write( instruction );

		assertThat( stripWhitespace( str ),
				is( "{\"id\":105,\"index\":1,\"lineNo\":2,\"origin\":{\"sortCode\":\"123456\",\"number\":\"12345678\","
						+ "\"name\":\"TESTAC1\"},\"destination\":{\"sortCode\":\"654321\",\"number\":\"87654321\","
						+ "\"type\":\"1\"},\"transactionType\":\"99\",\"rti\":\"/001\",\"amount\":10.01,"
						+ "\"reference\":\"A-REFERENCE\",\"processingDate\":\"2015-06-01\"}" ) );

		final Instruction result = read( str, Instruction.class );

		assertThat( result.origin()
				.sortCode(), is( SORT_CODE ) );
		assertThat( result.destination()
				.number(), is( DEST_ACCT_NO ) );
		assertThat( result.index(), is( INSTRUCTION_IDX ) );
		assertThat( result.lineNo(), is( LINE_NO ) );
		assertThat( result.reference(), is( REFERENCE ) );
		assertThat( result.transactionType(), is( TRANSACTION_TYPE ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Instruction instruction = instructionBuilder.build();

		final String str = write( View.Standard.class, instruction );

		assertThat( stripWhitespace( str ),
				is( "{\"index\":1,\"origin\":{\"sortCode\":\"123456\",\"number\":\"12345678\"},"
						+ "\"destination\":{\"sortCode\":\"654321\",\"number\":\"87654321\"},"
						+ "\"transactionType\":\"99\",\"rti\":\"/001\",\"amount\":10.01,"
						+ "\"reference\":\"A-REFERENCE\",\"processingDate\":\"2015-06-01\"}" ) );

		final Instruction result = read( str, Instruction.class );

		assertThat( result.origin()
				.sortCode(), is( SORT_CODE ) );
		assertThat( result.destination()
				.number(), is( DEST_ACCT_NO ) );
		assertThat( result.reference(), is( REFERENCE ) );
		assertThat( result.transactionType(), is( TRANSACTION_TYPE ) );
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

	@Test
	public final void testToString()
	{
		assertThat( instructionBuilder.build()
				.toString(), startsWith( "Instruction(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Instruction.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( instructionBuilder.toString(), startsWith( "Instruction.InstructionBuilder(" ) );
	}
}
