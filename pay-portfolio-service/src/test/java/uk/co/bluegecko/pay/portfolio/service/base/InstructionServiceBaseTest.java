package uk.co.bluegecko.pay.portfolio.service.base;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.service.InstructionService;
import uk.co.bluegecko.pay.portfolio.service.test.FakeDataFactory;


public class InstructionServiceBaseTest extends FakeDataFactory
{

	private InstructionService instructionService;

	@Before
	public void setUp() throws Exception
	{
		instructionService = new InstructionServiceBase();
	}

	@Test
	public final void test()
	{
		final Instruction instruction = createInstruction( createAccountOrigin(), createAccountDestination(),
				createBatch() );
		instructionService.save( instruction );

		assertThat( instruction.id(), is( INSTRUCTION_ID ) );
	}

}
