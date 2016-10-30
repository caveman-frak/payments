package uk.co.bluegecko.pay.upload.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.OffsetDateTime;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.co.bluegecko.pay.test.harness.TestHarness;


public class InstructionServiceBaseTest extends TestHarness
{

	@Test
	public final void testMarshal() throws JsonProcessingException
	{
		final String str = mapper.writeValueAsString( OffsetDateTime.now( clock ) );

		assertThat( str ).isEqualTo( "\"2015-06-01T12:05:30.0000005Z\"" );
	}

	@Test
	public final void testUnmarshal() throws IOException
	{
		final OffsetDateTime time = mapper.readValue( "\"2015-06-01T12:05:30.0000005Z\"", OffsetDateTime.class );

		assertThat( time ).isEqualTo( DATE_TIME_ZONE );
	}

}
