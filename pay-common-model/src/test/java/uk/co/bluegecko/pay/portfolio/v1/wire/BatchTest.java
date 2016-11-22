package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch.BatchBuilder;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total.Type;
import uk.co.bluegecko.pay.view.View;


public class BatchTest extends TestHarness
{

	private BatchBuilder batchBuilder;

	@Before
	public void setUp() throws Exception
	{
		final Total creditTotal = Total.builder()
				.type( Type.CREDIT )
				.count( 1 )
				.amount( new BigDecimal( "01.01" ) )
				.build();
		batchBuilder = Batch.builder()
				.id( 10L )
				.index( 1 )
				.portfolio( 20L )
				.set( "001" )
				.section( 2 )
				.sequence( 3 )
				.generation( 4 )
				.version( 5 )
				.totals( Stream.of( creditTotal )
						.collect( Collectors.toSet() ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Batch batch = batchBuilder.build();

		final String str = write( batch );

		assertThat( stripWhitespace( str ),
				is( "{\"id\":10,\"index\":1,\"portfolio\":20,\"set\":\"001\","
						+ "\"section\":2,\"sequence\":3,\"generation\":4,\"version\":5,"
						+ "\"totals\":[{\"type\":\"CREDIT\",\"count\":1,\"amount\":1.01}]}" ) );

		final Batch result = read( str, Batch.class );

		assertThat( result.id(), is( 10L ) );
		assertThat( result.index(), is( 1 ) );
		assertThat( result.portfolio(), is( 20L ) );
		assertThat( result.set(), is( "001" ) );
		assertThat( result.section(), is( 2 ) );
		assertThat( result.sequence(), is( 3 ) );
		assertThat( result.generation(), is( 4 ) );
		assertThat( result.version(), is( 5 ) );
		assertThat( result.totals(), hasSize( 1 ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Batch batch = batchBuilder.build();

		final String str = write( View.Standard.class, batch );

		assertThat( stripWhitespace( str ),
				is( "{\"index\":1,\"set\":\"001\",\"section\":2,\"sequence\":3,\"generation\":4,\"version\":5}" ) );

		final Batch result = read( str, Batch.class );

		assertThat( result.id(), is( nullValue() ) );
		assertThat( result.index(), is( 1 ) );
		assertThat( result.portfolio(), is( nullValue() ) );
		assertThat( result.set(), is( "001" ) );
		assertThat( result.section(), is( 2 ) );
		assertThat( result.sequence(), is( 3 ) );
		assertThat( result.generation(), is( 4 ) );
		assertThat( result.version(), is( 5 ) );
		assertThat( result.totals(), is( empty() ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( batchBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailId()
	{
		assertThat( isValid( batchBuilder.id( -20L )
				.build() ), is( false ) );
	}

	@Test
	public final void testValidationPassIdNull()
	{
		assertThat( isValid( batchBuilder.id( null )
				.build() ), is( true ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( batchBuilder.build()
				.toString(), startsWith( "Batch(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Batch.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( batchBuilder.toString(), startsWith( "Batch.BatchBuilder(" ) );
	}
}
