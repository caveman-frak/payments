package uk.co.bluegecko.pay.test.harness;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;


public class TestHarnessTest extends TestHarness
{

	private FakeModel model;

	@Before
	public final void setUp()
	{
		model = new FakeModel( 1, "A" );
	}

	@Test
	public final void testClock()
	{
		assertThat( LocalDate.now( clock() ), is( DATE ) );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final String s = write( model );

		assertThat( stripWhitespace( s ), is( "{\"integer\":1,\"string\":\"A\"}" ) );

		final FakeModel m = read( s, FakeModel.class );

		assertThat( m.integer, is( 1 ) );
		assertThat( m.string, is( "A" ) );
	}

	@Test
	public final void testMarshallingWithView() throws IOException
	{
		assertThat( stripWhitespace( write( FakeModel.class, model ) ), is( "{\"integer\":1,\"string\":\"A\"}" ) );
	}

	@Test
	public final void testMapper() throws IOException
	{
		assertThat( stripWhitespace( mapper().writeValueAsString( model ) ), is( "{\"integer\":1,\"string\":\"A\"}" ) );
	}

	@Test
	public final void testValidation()
	{
		assertThat( isValid( model ), is( true ) );
		assertThat( validate( model ), is( empty() ) );
		assertThat( validator().validate( model ), is( empty() ) );
	}

	protected static final class FakeModel
	{

		public int integer;
		public String string;

		public FakeModel()
		{}

		public FakeModel( final int integer, final String string )
		{
			this.integer = integer;
			this.string = string;
		}

	}

}
