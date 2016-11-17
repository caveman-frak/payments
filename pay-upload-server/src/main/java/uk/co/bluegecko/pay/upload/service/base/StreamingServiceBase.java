package uk.co.bluegecko.pay.upload.service.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.service.v1.InstructionWireStd18ToV1;
import uk.co.bluegecko.pay.upload.service.StreamingService;


@Service
@EnableBinding( Source.class )
public class StreamingServiceBase implements StreamingService
{

	private static final Logger logger = LoggerFactory.getLogger( StreamingService.class );

	private final Source source;
	private final InstructionWireStd18ToV1 instructionWireService;

	@Autowired
	public StreamingServiceBase( final Source source, final InstructionWireStd18ToV1 instructionWireService )
	{
		super();

		this.source = source;
		this.instructionWireService = instructionWireService;
	}

	@Override
	@Output( value = Source.OUTPUT )
	public void sendInstruction( final Instruction instruction )
	{
		logger.info( "sending {}", instruction.reference() );

		source.output()
				.send( MessageBuilder.withPayload( instructionWireService.toWire( instruction ) )
						.setSequenceNumber( instruction.index() )
						.build() );
	}

}
