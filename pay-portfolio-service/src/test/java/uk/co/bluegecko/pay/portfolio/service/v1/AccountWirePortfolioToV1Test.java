package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.base.AccountBase;


public class AccountWirePortfolioToV1Test
{

	private uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder wireBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount;
	private Account account;

	private AccountWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder();

		wireService = new AccountWirePortfolioToV1();
	}

	@Test
	public final void testToWire()
	{
		account = new AccountBase( 101L ).sortCode( "123456" )
				.number( "12345678" )
				.name( "TEST AC" )
				.type( "A" );

		wireAccount = wireService.toWire( account );

		assertThat( wireAccount.sortCode(), is( account.sortCode() ) );
		assertThat( wireAccount.number(), is( account.number() ) );
		assertThat( wireAccount.name(), is( account.name() ) );
		assertThat( wireAccount.type(), is( account.type() ) );
	}

	@Test
	public final void testFromWire()
	{
		wireAccount = wireBuilder.sortCode( "123456" )
				.number( "12345678" )
				.name( "TEST AC" )
				.type( "A" )
				.build();

		account = wireService.fromWire( wireAccount );

		assertThat( account.sortCode(), is( wireAccount.sortCode() ) );
		assertThat( account.number(), is( wireAccount.number() ) );
		assertThat( account.name(), is( wireAccount.name() ) );
		assertThat( account.type(), is( wireAccount.type() ) );
	}

}
