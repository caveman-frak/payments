package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Account;
import uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder;
import uk.co.bluegecko.pay.view.View;


public class AccountTest extends TestHarness implements FakeDataConstants
{

	private AccountBuilder accountBuilder;

	@Before
	public void setUp() throws Exception
	{
		accountBuilder = Account.builder()
				.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = write( account );

		assertThat( stripWhitespace( str ),
				is( "{\"sortCode\":\"123456\",\"number\":\"12345678\",\"name\":\"TESTAC1\"}" ) );

		final Account result = read( str, Account.class );

		assertThat( result.sortCode(), is( SORT_CODE ) );
		assertThat( result.number(), is( ACCT_NO ) );
		assertThat( result.name(), is( ACCT_NAME ) );
		assertThat( result.type(), is( nullValue() ) );
	}

	@Test
	public final void testMarshallingWithStandardView() throws IOException
	{
		final Account account = accountBuilder.build();

		final String str = write( View.Standard.class, account );

		assertThat( stripWhitespace( str ), is( "{\"sortCode\":\"123456\",\"number\":\"12345678\"}" ) );

		final Account result = read( str, Account.class );

		assertThat( result.sortCode(), is( SORT_CODE ) );
		assertThat( result.number(), is( ACCT_NO ) );
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

	@Test
	public final void testToString()
	{
		assertThat( accountBuilder.build()
				.toString(), startsWith( "Account(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( Account.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( accountBuilder.toString(), startsWith( "Account.AccountBuilder(" ) );
	}
}
