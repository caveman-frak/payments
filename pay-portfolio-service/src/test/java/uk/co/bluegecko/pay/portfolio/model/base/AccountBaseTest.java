package uk.co.bluegecko.pay.portfolio.model.base;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class AccountBaseTest
{

	@Test
	public final void testToString()
	{
		assertThat( new AccountBase( 101L ).toString(), startsWith( "AccountBase(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( AccountBase.class )
				.suppress( Warning.NONFINAL_FIELDS )
				.verify();
	}

}
