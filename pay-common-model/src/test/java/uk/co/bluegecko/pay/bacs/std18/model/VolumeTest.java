package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Volume.VolumeBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class VolumeTest extends TestHarness
{

	private VolumeBuilder volumeBuilder;

	@Before
	public void setUp() throws Exception
	{
		volumeBuilder = Volume.builder()
				.serialNo( "12345" )
				.accessibility( "Y" )
				.userNumber( "123456" )
				.label( "L" );
	}

	@Test
	public final void testBuilder()
	{
		final Volume volume = volumeBuilder.build();

		assertThat( volume.serialNo(), is( "12345" ) );
		assertThat( volume.accessibility(), is( "Y" ) );
		assertThat( volume.userNumber(), is( "123456" ) );
		assertThat( volume.label(), is( "L" ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( volumeBuilder.build()
				.toString(), startsWith( "Volume(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Volume.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( volumeBuilder.toString(), startsWith( "Volume.VolumeBuilder(" ) );
	}

}
