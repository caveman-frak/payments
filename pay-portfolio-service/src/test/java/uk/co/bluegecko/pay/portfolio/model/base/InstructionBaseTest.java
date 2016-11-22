package uk.co.bluegecko.pay.portfolio.model.base;


import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


public class InstructionBaseTest
{

	@Test
	public final void testToString()
	{
		assertThat( new InstructionBase( 101L, new BatchBase( 100L ), 1 ).toString(),
				startsWith( "InstructionBase(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( InstructionBase.class )
				.suppress( Warning.NONFINAL_FIELDS )
				.verify();
	}

}
