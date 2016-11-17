package uk.co.bluegecko.pay.upload.service;


import uk.co.bluegecko.pay.bacs.std18.model.Instruction;


public interface StreamingService
{

	public void sendInstruction( Instruction instruction );

}
