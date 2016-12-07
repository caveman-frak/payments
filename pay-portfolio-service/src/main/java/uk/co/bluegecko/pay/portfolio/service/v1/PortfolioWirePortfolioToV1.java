package uk.co.bluegecko.pay.portfolio.service.v1;


import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.WireService;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;


@Service
public class PortfolioWirePortfolioToV1
		implements WireService< Portfolio, uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio >
{

	@Override
	public Portfolio fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio portfolio, final Object... params )
	{
		return new PortfolioBase( portfolio.id() ).name( portfolio.name() )
				.userNumber( portfolio.userNumber() )
				.serialNo( portfolio.serialNo() );
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio toWire( final Portfolio portfolio, final Object... params )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio.builder()
				.id( portfolio.id() )
				.name( portfolio.name() )
				.userNumber( portfolio.userNumber() )
				.serialNo( portfolio.serialNo() )
				.build();
	}

}
