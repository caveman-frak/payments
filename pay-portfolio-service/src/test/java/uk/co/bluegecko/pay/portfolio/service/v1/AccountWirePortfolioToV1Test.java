package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.service.v1.test.FakeDataFactory;


public class AccountWirePortfolioToV1Test extends FakeDataFactory
{

	private AccountWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new AccountWirePortfolioToV1();
	}

	@Test
	public final void testToWire()
	{
		final Account account = createAccountOrigin();

		final uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount = wireService.toWire( account );

		assertThat( wireAccount.sortCode(), is( account.sortCode() ) );
		assertThat( wireAccount.number(), is( account.number() ) );
		assertThat( wireAccount.name(), is( account.name() ) );
		assertThat( wireAccount.type(), is( account.type() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount = createWireAccountOrigin();

		final Account account = wireService.fromWire( wireAccount );

		assertThat( account.sortCode(), is( wireAccount.sortCode() ) );
		assertThat( account.number(), is( wireAccount.number() ) );
		assertThat( account.name(), is( wireAccount.name() ) );
		assertThat( account.type(), is( wireAccount.type() ) );
	}

}
