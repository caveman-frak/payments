package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Header2.Header2Builder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class Header2Test extends TestHarness
{

	private Header2Builder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = Header2.builder()
				.indicator( Row.HDR2 )
				.format( "F" )
				.block( "0001" )
				.record( "00106" )
				.offset( "0" );
	}

	@Test
	public final void testBuilder()
	{
		final Header2 header = headerBuilder.build();

		assertThat( header.indicator(), is( Row.HDR2 ) );
		assertThat( header.format(), is( "F" ) );
		assertThat( header.block(), is( "0001" ) );
		assertThat( header.record(), is( "00106" ) );
		assertThat( header.offset(), is( "0" ) );
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
