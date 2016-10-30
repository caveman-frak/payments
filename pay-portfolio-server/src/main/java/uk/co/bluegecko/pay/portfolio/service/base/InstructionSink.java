package uk.co.bluegecko.pay.portfolio.service.base;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import reactor.core.publisher.Flux;
import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;


@EnableBinding( Sink.class )
@EnableAutoConfiguration
public class InstructionSink
{

	@StreamListener( Sink.INPUT )
	public void instructionSink( final Flux< Instruction > input )
	{
		input.log();
	}

}
