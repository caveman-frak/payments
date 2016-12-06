package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.service.v1.test.FakeDataFactory;


public class InstructionWirePortfolioToV1Test extends FakeDataFactory
{

	private InstructionWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new InstructionWirePortfolioToV1( new AccountWirePortfolioToV1() );
	}

	@Test
	public final void testToWire()
	{
		final Portfolio portfolio = createPortfolio();

		final Instruction instruction = createInstruction( createAccountOrigin(), createAccountDestination(),
				createBatch( portfolio ) );

		final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction = wireService.toWire( instruction );

		assertThat( wireInstruction.id(), is( instruction.id() ) );
		assertThat( wireInstruction.batch(), is( instruction.batch()
				.id() ) );
		assertThat( wireInstruction.index(), is( instruction.index() ) );
		assertThat( wireInstruction.lineNo(), is( instruction.lineNo() ) );
		assertThat( wireInstruction.origin()
				.sortCode(),
				is( instruction.origin()
						.sortCode() ) );
		assertThat( wireInstruction.origin()
				.number(),
				is( instruction.origin()
						.number() ) );
		assertThat( wireInstruction.origin()
				.name(),
				is( instruction.origin()
						.name() ) );
		assertThat( wireInstruction.destination()
				.sortCode(),
				is( instruction.destination()
						.sortCode() ) );
		assertThat( wireInstruction.destination()
				.number(),
				is( instruction.destination()
						.number() ) );
		assertThat( wireInstruction.destination()
				.type(),
				is( instruction.destination()
						.type() ) );
		assertThat( wireInstruction.reference(), is( instruction.reference() ) );
		assertThat( wireInstruction.transactionType(), is( instruction.transactionType() ) );
		assertThat( wireInstruction.rti(), is( instruction.rti() ) );
		assertThat( wireInstruction.amount(), is( instruction.amount() ) );
		assertThat( wireInstruction.processingDate(), is( instruction.processingDate() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction = createWireInstruction(
				createWireAccountOrigin(), createWireAccountDestination() );

		final Instruction instruction = wireService.fromWire( wireInstruction );

		assertThat( instruction.index(), is( wireInstruction.index() ) );
		assertThat( instruction.lineNo(), is( wireInstruction.lineNo() ) );
		assertThat( instruction.origin()
				.sortCode(),
				is( wireInstruction.origin()
						.sortCode() ) );
		assertThat( instruction.origin()
				.number(),
				is( wireInstruction.origin()
						.number() ) );
		assertThat( instruction.origin()
				.name(),
				is( wireInstruction.origin()
						.name() ) );
		assertThat( instruction.destination()
				.sortCode(),
				is( wireInstruction.destination()
						.sortCode() ) );
		assertThat( instruction.destination()
				.number(),
				is( wireInstruction.destination()
						.number() ) );
		assertThat( instruction.destination()
				.type(),
				is( wireInstruction.destination()
						.type() ) );
		assertThat( instruction.reference(), is( wireInstruction.reference() ) );
		assertThat( instruction.transactionType(), is( wireInstruction.transactionType() ) );
		assertThat( instruction.rti(), is( wireInstruction.rti() ) );
		assertThat( instruction.amount(), is( wireInstruction.amount() ) );
		assertThat( instruction.processingDate(), is( wireInstruction.processingDate() ) );

	}

}
