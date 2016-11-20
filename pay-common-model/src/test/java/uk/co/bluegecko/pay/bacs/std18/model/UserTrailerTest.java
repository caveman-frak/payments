package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.UserTrailer.UserTrailerBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class UserTrailerTest extends TestHarness
{

	private UserTrailerBuilder trailerBuilder;

	@Before
	public void setUp() throws Exception
	{
		trailerBuilder = UserTrailer.builder()
				.debitValue( "0003" )
				.creditValue( "0005" )
				.debitCount( 2 )
				.creditCount( 4 )
				.ddiCount( 0 )
				.serviceUser( "123456" );

	}

	@Test
	public final void testBuilder()
	{
		final UserTrailer trailer = trailerBuilder.build();

		assertThat( trailer.debitValue(), is( new BigDecimal( "00.03" ) ) );
		assertThat( trailer.creditValue(), is( new BigDecimal( "00.05" ) ) );
		assertThat( trailer.debitCount(), is( 2 ) );
		assertThat( trailer.creditCount(), is( 4 ) );
		assertThat( trailer.ddiCount(), is( 0 ) );
		assertThat( trailer.serviceUser(), is( "123456" ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final UserTrailer trailer = trailerBuilder.creditValue( new BigDecimal( "01.03" ) )
				.debitValue( new BigDecimal( "01.05" ) )
				.build();
		assertThat( trailer.creditValue(), is( new BigDecimal( "01.03" ) ) );
		assertThat( trailer.debitValue(), is( new BigDecimal( "01.05" ) ) );
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
