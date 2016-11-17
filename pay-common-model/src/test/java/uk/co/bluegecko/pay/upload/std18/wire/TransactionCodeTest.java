package uk.co.bluegecko.pay.upload.std18.wire;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.CreditCode;


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

	@Test
	public final void testAllByCodePass()
	{
		assertThat( TransactionCode.byCode( "Z4" ), is( CreditCode.INTEREST ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void testAllByCodeFail()
	{
		TransactionCode.byCode( CreditCode.class, "XX" );
	}

}
