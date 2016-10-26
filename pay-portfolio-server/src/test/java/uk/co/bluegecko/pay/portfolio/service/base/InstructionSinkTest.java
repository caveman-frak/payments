package uk.co.bluegecko.pay.portfolio.service.base;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetDateTime;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.fasterxml.jackson.core.JsonProcessingException;

import uk.co.bluegecko.pay.portfolio.PortfolioApplication;
import uk.co.bluegecko.pay.test.harness.TestHarness;


@SpringBootTest( classes = PortfolioApplication.class, webEnvironment = WebEnvironment.MOCK )
public class InstructionSinkTest extends TestHarness
{

	@Test
	public final void testMarshal() throws JsonProcessingException
	{
		final String str = mapper.writeValueAsString( OffsetDateTime.now( clock ) );

		assertThat( str ).isEqualTo( "\"2015-06-01T12:05:30.0000005Z\"" );
	}

}
