package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class TransactionTypeTest
{

	@Test
	public final void testValueOf()
	{
		assertThat( TransactionType.valueOf( "CREDIT" ), is( TransactionType.CREDIT ) );
	}

}
