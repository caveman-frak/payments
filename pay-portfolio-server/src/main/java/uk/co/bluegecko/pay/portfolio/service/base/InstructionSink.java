package uk.co.bluegecko.pay.portfolio.service.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;


@EnableBinding( Sink.class )
@EnableAutoConfiguration
public class InstructionSink
{

	private static final Logger logger = LoggerFactory.getLogger( InstructionSink.class );

	@StreamListener( Sink.INPUT )
	public void instructionSink( final Instruction instruction )
	{
		logger.info( instruction.toString() );
	}

}
