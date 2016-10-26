package uk.co.bluegecko.pay.portfolio.wire.v1;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.wire.v1.Account.AccountBuilder;
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
				.number( "12345678" );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = stripWhitespace( mapper.writeValueAsString( account ) );

		assertThat( str, is( "{\"sortCode\":\"123456\",\"number\":\"12345678\",\"type\":null}" ) );

		final Account result = mapper.readValue( str, Account.class );

		assertThat( result.sortCode(), is( "123456" ) );
		assertThat( result.number(), is( "12345678" ) );
		assertThat( result.type(), is( nullValue() ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = stripWhitespace( mapper.writerWithView( View.Standard.class )
				.writeValueAsString( account ) );

		assertThat( str, is( "{\"sortCode\":\"123456\",\"number\":\"12345678\"}" ) );

		final Account result = mapper.readValue( str, Account.class );

		assertThat( result.sortCode(), is( "123456" ) );
		assertThat( result.number(), is( "12345678" ) );
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
