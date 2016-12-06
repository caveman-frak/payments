package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Header1.Header1Builder;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class Header1Test extends TestHarness implements TestConstants
{

	private Header1Builder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = Header1.builder()
				.indicator( Row.HDR1 )
				.file( FILE )
				.set( SET )
				.generation( GENERATION )
				.section( SECTION )
				.sequence( SEQUENCE )
				.version( VERSION )
				.created( DATE.toEpochDay() )
				.expires( DATE.plusWeeks( 1 )
						.toEpochDay() )
				.accessibility( ACCESSIBILITY )
				.blockCount( BLOCK_COUNT )
				.systemCode( SYSTEM_CODE );
	}

	@Test
	public final void testBuilder()
	{
		final Header1 header = headerBuilder.build();

		assertThat( header.indicator(), is( Row.HDR1 ) );
		assertThat( header.file(), is( FILE ) );
		assertThat( header.set(), is( SET ) );
		assertThat( header.section(), is( SECTION ) );
		assertThat( header.sequence(), is( SEQUENCE ) );
		assertThat( header.generation(), is( GENERATION ) );
		assertThat( header.version(), is( VERSION ) );
		assertThat( header.created(), is( DATE ) );
		assertThat( header.expires(), is( DATE.plusWeeks( 1 ) ) );
		assertThat( header.accessibility(), is( ACCESSIBILITY ) );
		assertThat( header.blockCount(), is( BLOCK_COUNT ) );
		assertThat( header.systemCode(), is( SYSTEM_CODE ) );
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
