package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.v1.wire.TransactionCode.CreditCode;


public class TransactionCodeTest
{

	@Before
	public void setUp() throws Exception
	{}

	@Test
	public final void testByCodePass()
	{
		assertThat( TransactionCode.byCode( CreditCode.class, "Z4" ), is( CreditCode.INTEREST ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void testByCodeFail()
	{
		TransactionCode.byCode( CreditCode.class, "17" );
	}

}
