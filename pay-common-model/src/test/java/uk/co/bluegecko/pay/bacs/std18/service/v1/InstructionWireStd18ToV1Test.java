package uk.co.bluegecko.pay.bacs.std18.service.v1;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.Account;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class InstructionWireStd18ToV1Test extends TestHarness implements TestConstants
{

	private Account.AccountBuilder std18ActBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder wireActBuilder;
	private Instruction.InstructionBuilder std18Builder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.InstructionBuilder wireBuilder;
	private Instruction std18Instruction;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction;

	private InstructionWireStd18ToV1 wireService;

	@Before
	public final void setUp()
	{
		std18ActBuilder = Account.builder();
		wireActBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder();
		std18Builder = Instruction.builder();
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder();

		wireService = new InstructionWireStd18ToV1( new AccountWireStd18ToV1() );
	}

	@Test
	public final void testToWire()
	{
		final Account origin = std18ActBuilder.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
		final Account destination = std18ActBuilder.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE )
				.build();

		std18Instruction = std18Builder.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
				.build();

		wireInstruction = wireService.toWire( std18Instruction );

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
		final Account origin = std18ActBuilder.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
		final Account destination = std18ActBuilder.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE )
				.build();

		std18Instruction = std18Builder.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
				.build();

		wireInstruction = wireService.toWire( std18Instruction, BATCH_ID );

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
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account origin = wireActBuilder.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account destination = wireActBuilder.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE )
				.build();

		wireInstruction = wireBuilder.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
				.build();

		std18Instruction = wireService.fromWire( wireInstruction );

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
