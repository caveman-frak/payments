package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Header2.Header2Builder;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class Header2Test extends TestHarness implements TestConstants
{

	private Header2Builder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = Header2.builder()
				.indicator( Row.HDR2 )
				.format( FORMAT )
				.block( BLOCK )
				.record( RECORD )
				.offset( OFFSET );
	}

	@Test
	public final void testBuilder()
	{
		final Header2 header = headerBuilder.build();

		assertThat( header.indicator(), is( Row.HDR2 ) );
		assertThat( header.format(), is( FORMAT ) );
		assertThat( header.block(), is( BLOCK ) );
		assertThat( header.record(), is( RECORD ) );
		assertThat( header.offset(), is( OFFSET ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( headerBuilder.build()
				.toString(), startsWith( "Header2(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Header2.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( headerBuilder.toString(), startsWith( "Header2.Header2Builder(" ) );
	}

}
