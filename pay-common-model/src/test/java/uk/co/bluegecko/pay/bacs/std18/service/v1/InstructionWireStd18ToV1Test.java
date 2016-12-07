package uk.co.bluegecko.pay.bacs.std18.service.v1;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.service.v1.test.FakeDataFactory;


public class InstructionWireStd18ToV1Test extends FakeDataFactory
{

	private InstructionWireStd18ToV1 wireService;

	@Before
	public final void setUp()
	{
		wireService = new InstructionWireStd18ToV1( new AccountWireStd18ToV1() );
	}

	@Test
	public final void testToWire()
	{
		final Instruction std18Instruction = createInstruction( createAccountOrigin(), createAccountDestination() );

		final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction = wireService
				.toWire( std18Instruction );

		assertThat( wireInstruction.id(), is( nullValue() ) );
		assertThat( wireInstruction.batch(), is( nullValue() ) );
		assertThat( wireInstruction.index(), is( std18Instruction.index() ) );
		assertThat( wireInstruction.lineNo(), is( std18Instruction.lineNo() ) );
		assertThat( wireInstruction.origin()
				.sortCode(),
				is( std18Instruction.origin()
						.sortCode() ) );
		assertThat( wireInstruction.origin()
				.number(),
				is( std18Instruction.origin()
						.number() ) );
		assertThat( wireInstruction.origin()
				.name(),
				is( std18Instruction.origin()
						.name() ) );
		assertThat( wireInstruction.destination()
				.sortCode(),
				is( std18Instruction.destination()
						.sortCode() ) );
		assertThat( wireInstruction.destination()
				.number(),
				is( std18Instruction.destination()
						.number() ) );
		assertThat( wireInstruction.destination()
				.type(),
				is( std18Instruction.destination()
						.type() ) );
		assertThat( wireInstruction.reference(), is( std18Instruction.reference() ) );
		assertThat( wireInstruction.transactionType(), is( std18Instruction.transactionType() ) );
		assertThat( wireInstruction.rti(), is( std18Instruction.rti() ) );
		assertThat( wireInstruction.amount(), is( std18Instruction.amount() ) );
		assertThat( wireInstruction.processingDate(), is( std18Instruction.processingDate() ) );
	}

	@Test
	public final void testToWireWithId()
	{
		final Instruction std18Instruction = createInstruction( createAccountOrigin(), createAccountDestination() );

		final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction = wireService.toWire( std18Instruction,
				BATCH_ID );

		assertThat( wireInstruction.id(), is( nullValue() ) );
		assertThat( wireInstruction.batch(), is( BATCH_ID ) );
		assertThat( wireInstruction.index(), is( std18Instruction.index() ) );
		assertThat( wireInstruction.lineNo(), is( std18Instruction.lineNo() ) );
		assertThat( wireInstruction.origin()
				.sortCode(),
				is( std18Instruction.origin()
						.sortCode() ) );
		assertThat( wireInstruction.origin()
				.number(),
				is( std18Instruction.origin()
						.number() ) );
		assertThat( wireInstruction.origin()
				.name(),
				is( std18Instruction.origin()
						.name() ) );
		assertThat( wireInstruction.destination()
				.sortCode(),
				is( std18Instruction.destination()
						.sortCode() ) );
		assertThat( wireInstruction.destination()
				.number(),
				is( std18Instruction.destination()
						.number() ) );
		assertThat( wireInstruction.destination()
				.type(),
				is( std18Instruction.destination()
						.type() ) );
		assertThat( wireInstruction.reference(), is( std18Instruction.reference() ) );
		assertThat( wireInstruction.transactionType(), is( std18Instruction.transactionType() ) );
		assertThat( wireInstruction.rti(), is( std18Instruction.rti() ) );
		assertThat( wireInstruction.amount(), is( std18Instruction.amount() ) );
		assertThat( wireInstruction.processingDate(), is( std18Instruction.processingDate() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction = createWireInstruction(
				createWireAccountOrigin(), createWireAccountDestination() );

		final Instruction std18Instruction = wireService.fromWire( wireInstruction );

		assertThat( std18Instruction.index(), is( wireInstruction.index() ) );
		assertThat( std18Instruction.lineNo(), is( wireInstruction.lineNo() ) );
		assertThat( std18Instruction.origin()
				.sortCode(),
				is( wireInstruction.origin()
						.sortCode() ) );
		assertThat( std18Instruction.origin()
				.number(),
				is( wireInstruction.origin()
						.number() ) );
		assertThat( std18Instruction.origin()
				.name(),
				is( wireInstruction.origin()
						.name() ) );
		assertThat( std18Instruction.destination()
				.sortCode(),
				is( wireInstruction.destination()
						.sortCode() ) );
		assertThat( std18Instruction.destination()
				.number(),
				is( wireInstruction.destination()
						.number() ) );
		assertThat( std18Instruction.destination()
				.type(),
				is( wireInstruction.destination()
						.type() ) );
		assertThat( std18Instruction.reference(), is( wireInstruction.reference() ) );
		assertThat( std18Instruction.transactionType(), is( wireInstruction.transactionType() ) );
		assertThat( std18Instruction.rti(), is( wireInstruction.rti() ) );
		assertThat( std18Instruction.amount(), is( wireInstruction.amount() ) );
		assertThat( std18Instruction.processingDate(), is( wireInstruction.processingDate() ) );

	}

}
