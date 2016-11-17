package uk.co.bluegecko.pay.portfolio.service.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

import uk.co.bluegecko.pay.portfolio.service.v1.InstructionWirePortfolioToV1;
import uk.co.bluegecko.pay.v1.portfolio.wire.Instruction;


@EnableBinding( Sink.class )
@EnableAutoConfiguration
public class InstructionSink
{

	private static final Logger logger = LoggerFactory.getLogger( InstructionSink.class );
	private final InstructionWirePortfolioToV1 instructionWireService;

	@Autowired
	public InstructionSink( final InstructionWirePortfolioToV1 instructionWireService )
	{
		super();

		this.instructionWireService = instructionWireService;
	}

	@StreamListener( Sink.INPUT )
	public void instructionSink( final Instruction instruction )
	{
		logger.info( instructionWireService.fromWire( instruction )
				.toString() );
	}

}
