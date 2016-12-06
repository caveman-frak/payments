package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.Account.AccountBuilder;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class AccountTest extends TestHarness implements TestConstants
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
