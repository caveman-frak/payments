package uk.co.bluegecko.pay.portfolio.model.base;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class BatchBaseTest
{

	@Test
	public final void testToString()
	{
		assertThat( new BatchBase( 101L ).toString(), startsWith( "BatchBase(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( BatchBase.class )
				.suppress( Warning.NONFINAL_FIELDS )
				.verify();
	}

}
