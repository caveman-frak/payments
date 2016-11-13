package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.v1.wire.Account.AccountBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.view.View;


public class AccountTest extends TestHarness
{

	private AccountBuilder accountBuilder;

	@Before
	public void setUp() throws Exception
	{
		accountBuilder = Account.builder()
				.sortCode( "123456" )
				.number( "12345678" )
				.name( "JOE BLOGGS" );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = write( account );

		assertThat( stripWhitespace( str ),
				is( "{\"sortCode\":\"123456\",\"number\":\"12345678\",\"name\":\"JOEBLOGGS\"}" ) );

		final Account result = read( str, Account.class );

		assertThat( result.sortCode(), is( "123456" ) );
		assertThat( result.number(), is( "12345678" ) );
		assertThat( result.name(), is( "JOE BLOGGS" ) );
		assertThat( result.type(), is( nullValue() ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = write( View.Standard.class, account );

		assertThat( stripWhitespace( str ), is( "{\"sortCode\":\"123456\",\"number\":\"12345678\"}" ) );

		final Account result = read( str, Account.class );

		assertThat( result.sortCode(), is( "123456" ) );
		assertThat( result.number(), is( "12345678" ) );
		assertThat( result.name(), is( nullValue() ) );
		assertThat( result.type(), is( nullValue() ) );
	}

	@Test
	public final void testValidationPass()
	{
		assertThat( isValid( accountBuilder.build() ), is( true ) );
	}

	@Test
	public final void testValidationFailSortCode()
	{
		assertThat( isValid( accountBuilder.sortCode( "A12345" )
				.build() ), is( false ) );
	}

	@Test
	public final void testValidationPassNumberNull()
	{
		assertThat( isValid( accountBuilder.number( null )
				.build() ), is( true ) );
	}

}
