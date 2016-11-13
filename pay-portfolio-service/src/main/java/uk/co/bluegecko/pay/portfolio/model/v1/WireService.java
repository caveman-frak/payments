package uk.co.bluegecko.pay.portfolio.model.v1;


import uk.co.bluegecko.pay.portfolio.model.Batch;


public interface WireService
{

	public Batch fromWire( uk.co.bluegecko.pay.portfolio.v1.wire.Batch batch );

	public uk.co.bluegecko.pay.portfolio.v1.wire.Batch toWire( Batch batch );

}
