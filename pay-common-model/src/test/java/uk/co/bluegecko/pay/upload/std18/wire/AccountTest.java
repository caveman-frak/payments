package uk.co.bluegecko.pay.upload.std18.wire;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.Account;
import uk.co.bluegecko.pay.bacs.std18.model.Account.AccountBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


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
