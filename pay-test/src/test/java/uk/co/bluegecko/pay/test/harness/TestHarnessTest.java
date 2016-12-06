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

	private static final int NUMBER = 1;
	private static final String LETTER = "A";

	private static final String JSON = "{\"integer\":1,\"string\":\"A\"}";
	private FakeModel model;

	@Before
	public final void setUp()
	{
		model = new FakeModel( NUMBER, LETTER );
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

		assertThat( stripWhitespace( s ), is( JSON ) );

		final FakeModel m = read( s, FakeModel.class );

		assertThat( m.integer, is( NUMBER ) );
		assertThat( m.string, is( LETTER ) );
	}

	@Test
	public final void testMarshallingWithView() throws IOException
	{
		assertThat( stripWhitespace( write( FakeModel.class, model ) ), is( JSON ) );
	}

	@Test
	public final void testMapper() throws IOException
	{
		assertThat( stripWhitespace( mapper().writeValueAsString( model ) ), is( JSON ) );
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

		protected int integer;
		protected String string;

		public FakeModel()
		{}

		public FakeModel( final int integer, final String string )
		{
			this.integer = integer;
			this.string = string;
		}

	}

}
