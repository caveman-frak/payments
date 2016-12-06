package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Contra.ContraBuilder;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class ContraTest extends TestHarness implements FakeDataConstants
{

	private ContraBuilder contraBuilder;

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

		contraBuilder = Contra.builder()
				.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.narrative( REFERENCE );
	}

	@Test
	public final void testBuilder()
	{
		final Contra contra = contraBuilder.build();
		assertThat( contra.amount(), is( AMOUNT ) );
		assertThat( contra.processingDate(), is( DATE ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final Contra contra = contraBuilder.amount( AMOUNT )
				.processingDate( DATE.plusDays( 1 ) )
				.build();
		assertThat( contra.amount(), is( AMOUNT ) );
		assertThat( contra.processingDate(), is( DATE.plusDays( 1 ) ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( contraBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationPassReference()
	{
		assertThat( isValid( contraBuilder.narrative( "ABC-123.8/9&0" )
				.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailReference()
	{
		assertThat( isValid( contraBuilder.narrative( "A12345__12" )
				.build() ), is( false ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( contraBuilder.build()
				.toString(), startsWith( "Contra(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Contra.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( contraBuilder.toString(), startsWith( "Contra.ContraBuilder(" ) );
	}

}
