package uk.co.bluegecko.pay.portfolio.model;

public interface WireService< M, W >
{

	public M fromWire( W wire );

	public W toWire( M model );

}
