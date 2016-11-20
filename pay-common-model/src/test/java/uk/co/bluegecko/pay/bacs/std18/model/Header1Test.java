package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Header1.Header1Builder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class Header1Test extends TestHarness
{

	private Header1Builder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = Header1.builder()
				.indicator( Row.HDR1 )
				.file( "12345" )
				.set( "001" )
				.section( "002" )
				.sequence( "003" )
				.generation( "4" )
				.version( "5" )
				.created( DATE.toEpochDay() )
				.expires( DATE.plusWeeks( 1 )
						.toEpochDay() )
				.accessibility( "Y" )
				.blockCount( "00106" )
				.systemCode( "CODE" );
	}

	@Test
	public final void testBuilder()
	{
		final Header1 header = headerBuilder.build();

		assertThat( header.indicator(), is( Row.HDR1 ) );
		assertThat( header.file(), is( "12345" ) );
		assertThat( header.set(), is( "001" ) );
		assertThat( header.section(), is( "002" ) );
		assertThat( header.sequence(), is( "003" ) );
		assertThat( header.generation(), is( "4" ) );
		assertThat( header.version(), is( "5" ) );
		assertThat( header.created(), is( DATE ) );
		assertThat( header.expires(), is( DATE.plusWeeks( 1 ) ) );
		assertThat( header.accessibility(), is( "Y" ) );
		assertThat( header.blockCount(), is( "00106" ) );
		assertThat( header.systemCode(), is( "CODE" ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final Header1 header = headerBuilder.created( DATE.plusDays( 1 ) )
				.expires( DATE.plusDays( 8 ) )
				.build();
		assertThat( header.created(), is( DATE.plusDays( 1 ) ) );
		assertThat( header.expires(), is( DATE.plusDays( 8 ) ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( headerBuilder.build()
				.toString(), startsWith( "Header1(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Header1.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( headerBuilder.toString(), startsWith( "Header1.Header1Builder(" ) );
	}

}
