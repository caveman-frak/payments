package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction.InstructionBuilder;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


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
				.type( ACCT_TYPE )
				.build();
		final Account destination = Account.builder()
				.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.type( DEST_ACCT_TYPE )
				.build();

		instructionBuilder = Instruction.builder()
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
		assertThat( instruction.amount(), is( new BigDecimal( "10.01" ) ) );
		assertThat( instruction.processingDate(), is( DATE ) );
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
