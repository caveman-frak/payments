package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class PortfolioWirePortfolioToV1Test extends TestHarness implements TestConstants
{

	private uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio.PortfolioBuilder wireBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio wirePortfolio;

	private PortfolioWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio.builder();

		wireService = new PortfolioWirePortfolioToV1();
	}

	@Test
	public final void testToWire()
	{
		final Portfolio portfolio = new PortfolioBase( 103L ).name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN );

		wirePortfolio = wireService.toWire( portfolio );

		assertThat( wirePortfolio.id(), is( portfolio.id() ) );
		assertThat( wirePortfolio.name(), is( portfolio.name() ) );
		assertThat( portfolio.serialNo(), is( wirePortfolio.serialNo() ) );
		assertThat( portfolio.userNumber(), is( wirePortfolio.userNumber() ) );
	}

	@Test
	public final void testFromWire()
	{
		wirePortfolio = wireBuilder.id( 103L )
				.name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN )
				.build();

		final Portfolio portfolio = wireService.fromWire( wirePortfolio );

		assertThat( portfolio.id(), is( wirePortfolio.id() ) );
		assertThat( portfolio.name(), is( wirePortfolio.name() ) );
		assertThat( portfolio.serialNo(), is( wirePortfolio.serialNo() ) );
		assertThat( portfolio.userNumber(), is( wirePortfolio.userNumber() ) );
	}

}
