package uk.co.bluegecko.pay.upload.service;


import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;


public interface StreamingService
{

	public void send( Instruction instruction );

}
