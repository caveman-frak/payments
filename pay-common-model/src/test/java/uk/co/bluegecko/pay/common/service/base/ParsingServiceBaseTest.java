package uk.co.bluegecko.pay.common.service.base;


import static org.hamcrest.Matchers.hasEntry;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.stream.fixedlength.FixedLengthRecordParserFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import uk.co.bluegecko.pay.common.service.Mapper;


public class ParsingServiceBaseTest
{

	private static final String TEST = "test";
	private static final String TYPE = "VOL1";
	private static final String LINE = "VOL1173922                               100101                                1";

	private ParsingServiceBase parsingService;

	@Before
	public void setUp() throws Exception
	{
		parsingService = new ParsingServiceBase();
	}

	@Test
	public final void testParseWithMap() throws IOException
	{
		final StreamFactory factory = parsingService.factory();
		factory.define( new StreamBuilder( TEST ).format( "fixedlength" )
				.parser( new FixedLengthRecordParserFactory() )
				.addRecord( new RecordBuilder( TYPE ).type( HashMap.class )
						.minLength( 1 )
						.addField( new FieldBuilder( "type" ).length( 4 )
								.rid()
								.literal( TYPE )
								.ignore() )
						.addField( new FieldBuilder( "text" ).length( 6 ) ) ) );

		final Mapper< TextParsingContext > mapper = mock( Mapper.class );
		when( mapper.name() ).thenReturn( TEST );
		when( mapper.addMapping( any( StreamFactory.class ) ) ).thenReturn( factory );
		when( mapper.newContext( any( BeanReader.class ) ) )
				.thenAnswer( invocation -> new TextParsingContext( invocation.getArgumentAt( 0, BeanReader.class ) ) );

		try (Reader dataFile = new StringReader( LINE ))
		{
			parsingService.parse( dataFile, mapper );

			@SuppressWarnings( "rawtypes" )
			final ArgumentCaptor< Map > record = ArgumentCaptor.forClass( Map.class );
			final ArgumentCaptor< TextParsingContext > contect = ArgumentCaptor.forClass( TextParsingContext.class );

			verify( mapper, times( 1 ) ).map( record.capture(), contect.capture() );

			final Map< String, Object > value = record.getValue();
			assertThat( value, hasEntry( "text", "173922" ) );
		}
	}

	private static final class TextParsingContext extends AbstractParsingContext
	{

		public TextParsingContext( final BeanReader reader )
		{
			super( reader );
		}

	}

}
