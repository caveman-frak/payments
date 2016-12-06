package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch;
import uk.co.bluegecko.pay.v1.portfolio.wire.Batch.BatchBuilder;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total;
import uk.co.bluegecko.pay.v1.portfolio.wire.Total.Type;
import uk.co.bluegecko.pay.view.View;


public class BatchTest extends TestHarness implements TestConstants
{

	private BatchBuilder batchBuilder;

	@Before
	public void setUp() throws Exception
	{
		final Total creditTotal = Total.builder()
				.type( Type.CREDIT )
				.count( 1 )
				.amount( AMOUNT )
				.build();
		batchBuilder = Batch.builder()
				.id( BATCH_ID )
				.index( BATCH_IDX )
				.portfolio( PORTFOLIO_ID )
				.name( BATCH_NAME )
				.userNumber( SUN )
				.set( SET )
				.generation( GENERATION )
				.section( SECTION )
				.sequence( SEQUENCE )
				.version( VERSION )
				.totals( Stream.of( creditTotal )
						.collect( Collectors.toSet() ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Batch batch = batchBuilder.build();

		final String str = write( batch );

		assertThat( stripWhitespace( str ),
				is( "{\"id\":104,\"index\":1,\"portfolio\":102,\"name\":\"Batch#1\",\"userNumber\":\"123456\","
						+ "\"set\":\"001\",\"section\":2,\"sequence\":3,\"generation\":1,\"version\":4,"
						+ "\"totals\":[{\"type\":\"CREDIT\",\"count\":1,\"amount\":10.01}]}" ) );

		final Batch result = read( str, Batch.class );

		assertThat( result.id(), is( BATCH_ID ) );
		assertThat( result.index(), is( BATCH_IDX ) );
		assertThat( result.portfolio(), is( PORTFOLIO_ID ) );
		assertThat( result.set(), is( SET ) );
		assertThat( result.section(), is( SECTION ) );
		assertThat( result.sequence(), is( SEQUENCE ) );
		assertThat( result.generation(), is( GENERATION ) );
		assertThat( result.version(), is( VERSION ) );
		assertThat( result.totals(), hasSize( 1 ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Batch batch = batchBuilder.build();

		final String str = write( View.Standard.class, batch );

		assertThat( stripWhitespace( str ),
				is( "{\"index\":1,\"name\":\"Batch#1\",\"userNumber\":\"123456\",\"set\":\"001\",\"section\":2,"
						+ "\"sequence\":3,\"generation\":1,\"version\":4}" ) );

		final Batch result = read( str, Batch.class );

		assertThat( result.id(), is( nullValue() ) );
		assertThat( result.index(), is( BATCH_IDX ) );
		assertThat( result.portfolio(), is( nullValue() ) );
		assertThat( result.set(), is( SET ) );
		assertThat( result.section(), is( SECTION ) );
		assertThat( result.sequence(), is( SEQUENCE ) );
		assertThat( result.generation(), is( GENERATION ) );
		assertThat( result.version(), is( VERSION ) );
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
