package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio;
import uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio.PortfolioBuilder;


public class PortfolioTest extends TestHarness implements TestConstants
{

	private PortfolioBuilder portfolioBuilder;

	@Before
	public void setUp() throws Exception
	{
		portfolioBuilder = Portfolio.builder()
				.id( PORTFOLIO_ID )
				.name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Portfolio portfolio = portfolioBuilder.build();

		final String str = write( portfolio );

		assertThat( stripWhitespace( str ),
				is( "{\"id\":102,\"name\":\"PORTFOLIO#1\",\"serialNo\":\"321\",\"userNumber\":\"012345\"}" ) );

		final Portfolio result = read( str, Portfolio.class );

		assertThat( result.id(), is( PORTFOLIO_ID ) );
		assertThat( result.name(), is( PORTFOLIO_NAME ) );
		assertThat( result.serialNo(), is( SERIAL_NO ) );
		assertThat( result.userNumber(), is( SUN ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( portfolioBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailId()
	{
		assertThat( isValid( portfolioBuilder.id( -20L )
				.build() ), is( false ) );
	}

	@Test
	public final void testValidationPassIdNull()
	{
		assertThat( isValid( portfolioBuilder.id( null )
				.build() ), is( true ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( portfolioBuilder.build()
				.toString(), startsWith( "Portfolio(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Portfolio.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( portfolioBuilder.toString(), startsWith( "Portfolio.PortfolioBuilder(" ) );
	}
}
