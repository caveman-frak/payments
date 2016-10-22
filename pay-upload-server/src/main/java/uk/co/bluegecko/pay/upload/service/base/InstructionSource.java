package uk.co.bluegecko.pay.upload.service.base;


import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;


@Service
@EnableBinding( Source.class )
public class InstructionSource
{

	private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.MEDIUM,
			FormatStyle.MEDIUM );

	@Autowired
	Clock clock;

	private static Logger logger = LoggerFactory.getLogger( InstructionSource.class );

	@InboundChannelAdapter( value = Source.OUTPUT, poller = @Poller( fixedDelay = "5000", maxMessagesPerPoll = "1" ) )
	public Message< LocalDateTime > instructionSource()
	{
		final LocalDateTime now = LocalDateTime.now( clock );

		logger.info( "Sending: {}", now.format( FORMAT ) );

		return MessageBuilder.withPayload( now ).build();
	}

}
