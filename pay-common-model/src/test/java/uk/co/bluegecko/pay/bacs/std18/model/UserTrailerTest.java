package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.UserTrailer.UserTrailerBuilder;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class UserTrailerTest extends TestHarness implements TestConstants
{

	private static final String DEBIT_PENCE = "0003";
	private static final String DEBIT_AMT = "0.03";
	private static final String CREDIT_PENCE = "0005";
	private static final String CREDIT_AMT = "0.05";
	private static final int DEBIT_COUNT = 2;
	private static final int CREDIT_COUNT = 4;
	private static final int DDI_COUNT = 0;

	private UserTrailerBuilder trailerBuilder;

	@Before
	public void setUp() throws Exception
	{
		trailerBuilder = UserTrailer.builder()
				.debitValue( DEBIT_PENCE )
				.creditValue( CREDIT_PENCE )
				.debitCount( 2 )
				.creditCount( 4 )
				.ddiCount( 0 )
				.serviceUser( SUN );

	}

	@Test
	public final void testBuilder()
	{
		final UserTrailer trailer = trailerBuilder.build();

		assertThat( trailer.debitValue(), is( new BigDecimal( DEBIT_AMT ) ) );
		assertThat( trailer.creditValue(), is( new BigDecimal( CREDIT_AMT ) ) );
		assertThat( trailer.debitCount(), is( DEBIT_COUNT ) );
		assertThat( trailer.creditCount(), is( CREDIT_COUNT ) );
		assertThat( trailer.ddiCount(), is( DDI_COUNT ) );
		assertThat( trailer.serviceUser(), is( SUN ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final UserTrailer trailer = trailerBuilder.creditValue( new BigDecimal( DEBIT_AMT ) )
				.debitValue( new BigDecimal( CREDIT_AMT ) )
				.build();
		assertThat( trailer.creditValue(), is( new BigDecimal( DEBIT_AMT ) ) );
		assertThat( trailer.debitValue(), is( new BigDecimal( CREDIT_AMT ) ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( trailerBuilder.build()
				.toString(), startsWith( "UserTrailer(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( UserTrailer.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( trailerBuilder.toString(), startsWith( "UserTrailer.UserTrailerBuilder(" ) );
	}

}
