package uk.co.bluegecko.pay.common.service.base;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.sf.flatpack.Record;
import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingService;


public class ParsingServiceBaseTest
{

	private static final String LINE = "VOL1173922                               100101                                1                          ";

	private ParsingService parsingService;

	@Before
	public void setUp() throws Exception
	{
		parsingService = new ParsingServiceBase();
	}

	@Test
	public final void testParse() throws IOException
	{
		try (Reader dataFile = new StringReader( LINE );
				Reader mappingFile = new InputStreamReader(
						getClass().getResourceAsStream( "/mapping/alternate-vol.pzmap.xml" ), StandardCharsets.UTF_8 ))
		{
			final Mapper mapper = mock( Mapper.class );

			parsingService.parse( dataFile, mappingFile, mapper );

			final ArgumentCaptor< Record > argument = ArgumentCaptor.forClass( Record.class );
			verify( mapper, only() ).map( argument.capture() );

			assertThat( argument.getValue()
					.isRecordID( "VOL1" ), is( true ) );
		}
	}

}
