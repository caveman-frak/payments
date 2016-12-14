package uk.co.bluegecko.pay.common.service.base;


import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
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

import uk.co.bluegecko.pay.bacs.std18.model.Volume;
import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingService;


public class ParsingServiceBaseTest
{

	private static final String TEST = "test";
	private static final String TYPE = "VOL1";
	private static final String LINE = "VOL1173922                               100101                                1                          ";

	private ParsingService parsingService;

	@Before
	public void setUp() throws Exception
	{
		parsingService = new ParsingServiceBase();
	}

	@Test
	public final void testParseWithMap() throws IOException
	{
		final Mapper mapper = mock( Mapper.class );
		when( mapper.name() ).thenReturn( TEST );

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

		try (Reader dataFile = new StringReader( LINE ))
		{
			parsingService.parse( dataFile, factory, mapper );

			@SuppressWarnings( "rawtypes" )
			final ArgumentCaptor< Map > record = ArgumentCaptor.forClass( Map.class );
			final ArgumentCaptor< BeanReader > reader = ArgumentCaptor.forClass( BeanReader.class );

			verify( mapper, times( 1 ) ).map( record.capture(), reader.capture() );

			final Map< String, Object > value = record.getValue();
			assertThat( value, hasEntry( "text", "173922" ) );
		}
	}

	@Test
	public final void testParseWithVolume() throws IOException
	{
		final Mapper mapper = mock( Mapper.class );
		when( mapper.name() ).thenReturn( TEST );

		final StreamFactory factory = parsingService.factory();
		factory.define( new StreamBuilder( TEST ).format( "fixedlength" )
				.parser( new FixedLengthRecordParserFactory() )
				.addRecord( new RecordBuilder( TYPE ).type( Volume.VolumeBuilder.class )
						.minLength( 1 )
						.addField( new FieldBuilder( "type" ).length( 4 )
								.rid()
								.literal( TYPE )
								.ignore() )
						.addField( new FieldBuilder( "serialNo" ).setter( "serialNo" )
								.length( 6 ) )
						.addField( new FieldBuilder( "reserved#1" ).length( 30 )
								.ignore() )
						.addField( new FieldBuilder( "accessibility" ).setter( "accessibility" )
								.length( 1 ) )
						.addField( new FieldBuilder( "userNumber" ).setter( "userNumber" )
								.length( 6 ) )
						.addField( new FieldBuilder( "reserved#2" ).length( 32 )
								.ignore() )
						.addField( new FieldBuilder( "label" ).setter( "label" )
								.length( 1 ) ) ) );

		try (Reader dataFile = new StringReader( LINE ))
		{
			parsingService.parse( dataFile, factory, mapper );

			final ArgumentCaptor< Volume.VolumeBuilder > record = ArgumentCaptor.forClass( Volume.VolumeBuilder.class );
			final ArgumentCaptor< BeanReader > reader = ArgumentCaptor.forClass( BeanReader.class );

			verify( mapper, times( 1 ) ).map( record.capture(), reader.capture() );

			final Volume value = record.getValue()
					.build();
			assertThat( value.serialNo(), is( "173922" ) );
			assertThat( value.accessibility(), is( "" ) );
			assertThat( value.userNumber(), is( "100101" ) );
			assertThat( value.label(), is( "1" ) );

		}
	}

}
