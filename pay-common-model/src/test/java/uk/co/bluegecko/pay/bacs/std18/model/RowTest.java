package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class RowTest
{

	@Test
	public final void testValueOf()
	{
		assertThat( Row.valueOf( "VOL1" ), is( Row.VOL1 ) );
	}

}
