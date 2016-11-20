package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Contra.ContraBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class ContraTest extends TestHarness
{

	private ContraBuilder contraBuilder;

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

		contraBuilder = Contra.builder()
				.index( 1 )
				.lineNo( 3 )
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
				.amount( "1001" )
				.processingDate( DATE.toEpochDay() )
				.narrative( "A-NARRATIVE" );
	}

	@Test
	public final void testBuilder()
	{
		final Contra contra = contraBuilder.build();
		assertThat( contra.amount(), is( new BigDecimal( "10.01" ) ) );
		assertThat( contra.processingDate(), is( DATE ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final Contra contra = contraBuilder.amount( new BigDecimal( "10.02" ) )
				.processingDate( DATE.plusDays( 1 ) )
				.build();
		assertThat( contra.amount(), is( new BigDecimal( "10.02" ) ) );
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
