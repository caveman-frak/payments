package uk.co.bluegecko.pay.portfolio.model.base;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class PortfolioBaseTest
{

	@Test
	public final void testToString()
	{
		assertThat( new PortfolioBase( 101L ).toString(), startsWith( "PortfolioBase(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( PortfolioBase.class )
				.suppress( Warning.NONFINAL_FIELDS )
				.verify();
	}

}
