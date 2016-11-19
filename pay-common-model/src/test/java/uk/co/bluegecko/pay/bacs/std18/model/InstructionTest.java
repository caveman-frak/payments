package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction.InstructionBuilder;
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
				.amount( "1001" )
				.processingDate( DATE.toEpochDay() )
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
