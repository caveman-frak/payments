package uk.co.bluegecko.pay.bacs.std18.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.Account;


public class AccountWireStd18ToV1Test
{

	private Account.AccountBuilder std18Builder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder wireBuilder;
	private Account std18Account;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Account wireAccount;

	private AccountWireStd18ToV1 wireService;

	@Before
	public final void setUp()
	{
		std18Builder = Account.builder();
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder();

		wireService = new AccountWireStd18ToV1();
	}

	@Test
	public final void testToWire()
	{
		std18Account = std18Builder.sortCode( "123456" )
				.number( "12345678" )
				.name( "TEST AC" )
				.type( "A" )
				.build();

		wireAccount = wireService.toWire( std18Account );

		assertThat( wireAccount.sortCode(), is( std18Account.sortCode() ) );
		assertThat( wireAccount.number(), is( std18Account.number() ) );
		assertThat( wireAccount.name(), is( std18Account.name() ) );
		assertThat( wireAccount.type(), is( std18Account.type() ) );
	}

	@Test
	public final void testFromWire()
	{
		wireAccount = wireBuilder.sortCode( "123456" )
				.number( "12345678" )
				.name( "TEST AC" )
				.type( "A" )
				.build();

		std18Account = wireService.fromWire( wireAccount );

		assertThat( std18Account.sortCode(), is( wireAccount.sortCode() ) );
		assertThat( std18Account.number(), is( wireAccount.number() ) );
		assertThat( std18Account.name(), is( wireAccount.name() ) );
		assertThat( std18Account.type(), is( wireAccount.type() ) );
	}

}
