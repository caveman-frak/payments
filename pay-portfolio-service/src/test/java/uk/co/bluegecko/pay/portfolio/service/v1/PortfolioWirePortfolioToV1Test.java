package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.service.test.FakeDataFactory;


public class PortfolioWirePortfolioToV1Test extends FakeDataFactory
{

	private PortfolioWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new PortfolioWirePortfolioToV1();
	}

	@Test
	public final void testToWire()
	{
		final Portfolio portfolio = createPortfolio();

		final uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio wirePortfolio = wireService.toWire( portfolio );

		assertThat( wirePortfolio.id(), is( portfolio.id() ) );
		assertThat( wirePortfolio.name(), is( portfolio.name() ) );
		assertThat( portfolio.serialNo(), is( wirePortfolio.serialNo() ) );
		assertThat( portfolio.userNumber(), is( wirePortfolio.userNumber() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio wirePortfolio = createWirePortfolio();

		final Portfolio portfolio = wireService.fromWire( wirePortfolio );

		assertThat( portfolio.id(), is( wirePortfolio.id() ) );
		assertThat( portfolio.name(), is( wirePortfolio.name() ) );
		assertThat( portfolio.serialNo(), is( wirePortfolio.serialNo() ) );
		assertThat( portfolio.userNumber(), is( wirePortfolio.userNumber() ) );
	}

}
