package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.service.v1.test.FakeDataFactory;


public class BatchWirePortfolioToV1Test extends FakeDataFactory
{

	private BatchWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new BatchWirePortfolioToV1();
	}

	@Test
	public final void testToWire()
	{
		final Batch batch = createBatch( createPortfolio() );

		final uk.co.bluegecko.pay.v1.portfolio.wire.Batch wireBatch = wireService.toWire( batch );

		assertThat( wireBatch.id(), is( batch.id() ) );
		assertThat( wireBatch.portfolio(), is( batch.portfolio()
				.get()
				.id() ) );
		assertThat( wireBatch.index(), is( batch.index() ) );
		assertThat( wireBatch.name(), is( batch.name() ) );
		assertThat( wireBatch.userNumber(), is( batch.userNumber() ) );
		assertThat( wireBatch.generation(), is( batch.generation() ) );
		assertThat( wireBatch.section(), is( batch.section() ) );
		assertThat( wireBatch.sequence(), is( batch.sequence() ) );
		assertThat( wireBatch.set(), is( batch.set() ) );
		assertThat( wireBatch.version(), is( batch.version() ) );
	}

	@Test
	public final void testToWireNoPortfolio()
	{
		final Batch batch = createBatch();

		final uk.co.bluegecko.pay.v1.portfolio.wire.Batch wireBatch = wireService.toWire( batch );

		assertThat( wireBatch.id(), is( batch.id() ) );
		assertThat( wireBatch.portfolio(), is( nullValue() ) );
		assertThat( wireBatch.index(), is( batch.index() ) );
		assertThat( wireBatch.name(), is( batch.name() ) );
		assertThat( wireBatch.userNumber(), is( batch.userNumber() ) );
		assertThat( wireBatch.generation(), is( batch.generation() ) );
		assertThat( wireBatch.section(), is( batch.section() ) );
		assertThat( wireBatch.sequence(), is( batch.sequence() ) );
		assertThat( wireBatch.set(), is( batch.set() ) );
		assertThat( wireBatch.version(), is( batch.version() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Batch wireBatch = createWireBatch( PORTFOLIO_ID );

		final Batch batch = wireService.fromWire( wireBatch );

		assertThat( batch.id(), is( wireBatch.id() ) );
		assertThat( batch.portfolio()
				.get()
				.id(), is( wireBatch.portfolio() ) );
		assertThat( wireBatch.index(), is( batch.index() ) );
		assertThat( wireBatch.name(), is( batch.name() ) );
		assertThat( wireBatch.userNumber(), is( batch.userNumber() ) );
		assertThat( batch.generation(), is( wireBatch.generation() ) );
		assertThat( batch.section(), is( wireBatch.section() ) );
		assertThat( batch.sequence(), is( wireBatch.sequence() ) );
		assertThat( batch.set(), is( wireBatch.set() ) );
		assertThat( batch.version(), is( wireBatch.version() ) );
	}

	@Test
	public final void testFromWireNoPortfolio()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Batch wireBatch = createWireBatch();

		final Batch batch = wireService.fromWire( wireBatch );

		assertThat( batch.id(), is( wireBatch.id() ) );
		assertThat( batch.portfolio()
				.isPresent(), is( false ) );
		assertThat( wireBatch.index(), is( batch.index() ) );
		assertThat( wireBatch.name(), is( batch.name() ) );
		assertThat( wireBatch.userNumber(), is( batch.userNumber() ) );
		assertThat( batch.generation(), is( wireBatch.generation() ) );
		assertThat( batch.section(), is( wireBatch.section() ) );
		assertThat( batch.sequence(), is( wireBatch.sequence() ) );
		assertThat( batch.set(), is( wireBatch.set() ) );
		assertThat( batch.version(), is( wireBatch.version() ) );
	}

}
