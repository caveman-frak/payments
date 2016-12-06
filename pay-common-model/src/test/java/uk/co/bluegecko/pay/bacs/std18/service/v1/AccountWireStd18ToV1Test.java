package uk.co.bluegecko.pay.bacs.std18.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.Account;
import uk.co.bluegecko.pay.bacs.std18.service.v1.test.FakeDataFactory;


public class AccountWireStd18ToV1Test extends FakeDataFactory
{

	private AccountWireStd18ToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new AccountWireStd18ToV1();
	}

	@Test
	public final void testToWire()
	{
		final Account std18Account = createAccountOrigin();

		final uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount = wireService.toWire( std18Account );

		assertThat( wireAccount.sortCode(), is( std18Account.sortCode() ) );
		assertThat( wireAccount.number(), is( std18Account.number() ) );
		assertThat( wireAccount.name(), is( std18Account.name() ) );
		assertThat( wireAccount.type(), is( std18Account.type() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount = createWireAccountOrigin();

		final Account std18Account = wireService.fromWire( wireAccount );

		assertThat( std18Account.sortCode(), is( wireAccount.sortCode() ) );
		assertThat( std18Account.number(), is( wireAccount.number() ) );
		assertThat( std18Account.name(), is( wireAccount.name() ) );
		assertThat( std18Account.type(), is( wireAccount.type() ) );
	}

}
