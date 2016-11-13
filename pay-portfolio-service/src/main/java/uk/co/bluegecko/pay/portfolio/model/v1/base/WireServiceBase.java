package uk.co.bluegecko.pay.portfolio.model.v1.base;


import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.v1.WireService;


@Service
public class WireServiceBase implements WireService
{

	@Override
	public Batch fromWire( final uk.co.bluegecko.pay.portfolio.v1.wire.Batch batch )
	{
		return new BatchBase( batch.id() ).index( batch.index() );
	}

	@Override
	public uk.co.bluegecko.pay.portfolio.v1.wire.Batch toWire( final Batch batch )
	{
		return uk.co.bluegecko.pay.portfolio.v1.wire.Batch.builder()
				.id( batch.id() )
				.index( batch.index() )
				.build();
	}

}
