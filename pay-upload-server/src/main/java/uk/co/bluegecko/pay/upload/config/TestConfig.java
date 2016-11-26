package uk.co.bluegecko.pay.upload.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.converter.CompositeMessageConverterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.common.annotation.TestProfile;
import uk.co.bluegecko.pay.upload.service.StreamingService;


@TestProfile
@Configuration
public class TestConfig
{

	@Bean
	public StreamingService streamingService()
	{
		return new StreamingServiceFake();
	}

	@Bean
	public CompositeMessageConverterFactory compositeMessageConverterFactory()
	{
		return new CompositeMessageConverterFactory();
	}

	protected static final class StreamingServiceFake implements StreamingService
	{

		private static final Logger logger = LoggerFactory.getLogger( StreamingService.class );

		@Override
		public void sendInstruction( final Instruction instruction )
		{
			logger.info( "sending {}", instruction.reference() );
		}

	}

}
