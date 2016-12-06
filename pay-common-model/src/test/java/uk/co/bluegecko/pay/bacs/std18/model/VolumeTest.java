package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Volume.VolumeBuilder;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class VolumeTest extends TestHarness implements TestConstants
{

	private VolumeBuilder volumeBuilder;

	@Before
	public void setUp() throws Exception
	{
		volumeBuilder = Volume.builder()
				.serialNo( SERIAL_NO )
				.accessibility( ACCESSIBILITY )
				.userNumber( SUN )
				.label( LABEL );
	}

	@Test
	public final void testBuilder()
	{
		final Volume volume = volumeBuilder.build();

		assertThat( volume.serialNo(), is( SERIAL_NO ) );
		assertThat( volume.accessibility(), is( ACCESSIBILITY ) );
		assertThat( volume.userNumber(), is( SUN ) );
		assertThat( volume.label(), is( LABEL ) );
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
