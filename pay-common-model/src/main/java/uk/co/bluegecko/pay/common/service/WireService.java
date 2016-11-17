package uk.co.bluegecko.pay.common.service;


public interface WireService< M, W >
{

	public M fromWire( W wire, Object... params );

	public W toWire( M model, Object... params );

}
