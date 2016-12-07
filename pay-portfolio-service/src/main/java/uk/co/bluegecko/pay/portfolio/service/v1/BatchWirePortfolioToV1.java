package uk.co.bluegecko.pay.portfolio.service.v1;


import java.util.Optional;

import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.WireService;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;


@Service
public class BatchWirePortfolioToV1 implements WireService< Batch, uk.co.bluegecko.pay.v1.portfolio.wire.Batch >
{

	@Override
	public Batch fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Batch batch, final Object... params )
	{
		final Portfolio portfolio = Optional.ofNullable( batch.portfolio() )
				.map( ( final Long id ) -> new PortfolioBase( id ) )
				.orElse( null );

		return new BatchBase( batch.id() ).index( batch.index() )
				.portfolio( portfolio )
				.name( batch.name() )
				.userNumber( batch.userNumber() )
				.set( batch.set() )
				.generation( batch.generation() )
				.section( batch.section() )
				.sequence( batch.sequence() )
				.version( batch.version() );
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Batch toWire( final Batch batch, final Object... params )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Batch.builder()
				.id( batch.id() )
				.index( batch.index() )
				.portfolio( batch.portfolio()
						.map( ( final Portfolio p ) -> p.id() )
						.orElse( null ) )
				.name( batch.name() )
				.userNumber( batch.userNumber() )
				.set( batch.set() )
				.generation( batch.generation() )
				.section( batch.section() )
				.sequence( batch.sequence() )
				.version( batch.version() )
				.build();
	}

}
