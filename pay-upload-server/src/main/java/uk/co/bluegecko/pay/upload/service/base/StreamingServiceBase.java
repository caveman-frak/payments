package uk.co.bluegecko.pay.upload.service.base;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;
import uk.co.bluegecko.pay.upload.service.StreamingService;


@Service
@EnableBinding( Source.class )
public class StreamingServiceBase implements StreamingService
{

	private static final Logger logger = LoggerFactory.getLogger( StreamingService.class );

	@Autowired
	Source source;

	@Override
	@Output( value = Source.OUTPUT )
	public void send( final Instruction instruction )
	{
		logger.info( "sending {}", instruction.reference() );

		source.output()
				.send( MessageBuilder.withPayload( instruction )
						.setSequenceNumber( instruction.index() )
						.build() );
	}

}
