package uk.co.bluegecko.pay.portfolio.service.v1;


import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.WireService;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;


@Service
public class BatchWirePortfolioToV1 implements WireService< Batch, uk.co.bluegecko.pay.v1.portfolio.wire.Batch >
{

	@Override
	public Batch fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Batch batch, final Object... params )
	{
		return new BatchBase( batch.id() ).index( batch.index() );
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Batch toWire( final Batch batch, final Object... params )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Batch.builder()
				.id( batch.id() )
				.index( batch.index() )
				.build();
	}

}
