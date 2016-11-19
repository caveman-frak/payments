package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total.TotalBuilder;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total.Type;


public class TotalTest extends TestHarness
{

	private TotalBuilder totalBuilder;

	@Before
	public void setUp() throws Exception
	{
		totalBuilder = Total.builder()
				.type( Type.CREDIT )
				.count( 23 )
				.amount( new BigDecimal( "56.78" ) );
	}

	@Test
	public final void testValueOf()
	{
		assertThat( Type.valueOf( "CREDIT" ), is( Type.CREDIT ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Total total = totalBuilder.build();

		final String str = write( total );

		assertThat( stripWhitespace( str ), is( "{\"type\":\"CREDIT\",\"count\":23,\"amount\":56.78}" ) );

		final Total result = read( str, Total.class );

		assertThat( result.type(), is( Type.CREDIT ) );
		assertThat( result.count(), is( 23 ) );
		assertThat( result.amount(), is( new BigDecimal( "56.78" ) ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( totalBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailSortCode()
	{
		assertThat( isValid( totalBuilder.count( -4 )
				.build() ), is( false ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( totalBuilder.build()
				.toString(), startsWith( "Total(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Total.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( totalBuilder.toString(), startsWith( "Total.TotalBuilder(" ) );
	}
}
