package uk.co.bluegecko.pay.portfolio.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.integration.annotation.ServiceActivator;


@EnableBinding( Sink.class )
public class InstructionSink
{

	private static Logger logger = LoggerFactory.getLogger( InstructionSink.class );

	@ServiceActivator( inputChannel = Sink.INPUT )
	public void loggerSink( final Object payload )
	{
		logger.info( "Received: " + payload );
	}

}
