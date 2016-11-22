package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.BureauUser;
import uk.co.bluegecko.pay.v1.portfolio.wire.BureauUser.BureauUserBuilder;
import uk.co.bluegecko.pay.view.View;


public class BureauUserTest extends TestHarness
{

	private BureauUserBuilder bureauUserBuilder;

	@Before
	public void setUp() throws Exception
	{
		bureauUserBuilder = BureauUser.builder()
				.userNumber( "B12345" )
				.serviceUsers( Stream.of( "123456", "456789" )
						.collect( Collectors.toSet() ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final BureauUser bureauUser = bureauUserBuilder.build();

		final String str = write( bureauUser );

		assertThat( stripWhitespace( str ),
				is( "{\"userNumber\":\"B12345\",\"serviceUsers\":[\"456789\",\"123456\"]}" ) );

		final BureauUser result = read( str, BureauUser.class );

		assertThat( result.userNumber(), is( "B12345" ) );
		assertThat( result.serviceUsers(), hasItems( "123456", "456789" ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final BureauUser bureauUser = bureauUserBuilder.build();

		final String str = write( View.Standard.class, bureauUser );

		assertThat( stripWhitespace( str ), is( "{\"userNumber\":\"B12345\"}" ) );

		final BureauUser result = read( str, BureauUser.class );

		assertThat( result.userNumber(), is( "B12345" ) );
		assertThat( result.serviceUsers(), is( empty() ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( bureauUserBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailUserNumber()
	{
		assertThat( isValid( bureauUserBuilder.userNumber( "123456" )
				.build() ), is( false ) );
	}

	@Test
	public final void testValidationFailUserNumberNull()
	{
		assertThat( isValid( bureauUserBuilder.userNumber( null )
				.build() ), is( false ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( bureauUserBuilder.build()
				.toString(), startsWith( "BureauUser(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( BureauUser.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( bureauUserBuilder.toString(), startsWith( "BureauUser.BureauUserBuilder(" ) );
	}
}
