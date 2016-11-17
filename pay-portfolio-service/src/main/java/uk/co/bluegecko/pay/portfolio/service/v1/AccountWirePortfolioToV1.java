package uk.co.bluegecko.pay.portfolio.service.v1;


import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.WireService;
import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.base.AccountBase;


@Service
public class AccountWirePortfolioToV1 implements WireService< Account, uk.co.bluegecko.pay.v1.portfolio.wire.Account >
{

	@Override
	public Account fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Account account, final Object... params )
	{
		return new AccountBase( null ).sortCode( account.sortCode() )
				.number( account.number() )
				.name( account.name() )
				.type( account.type() );
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Account toWire( final Account account, final Object... params )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder()
				.sortCode( account.sortCode() )
				.number( account.number() )
				.name( account.name() )
				.type( account.type() )
				.build();
	}

}
