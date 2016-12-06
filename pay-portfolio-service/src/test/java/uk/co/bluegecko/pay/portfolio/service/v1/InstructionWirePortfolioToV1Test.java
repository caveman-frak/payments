package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.AccountBase;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.base.InstructionBase;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;
import uk.co.bluegecko.pay.test.data.TestConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class InstructionWirePortfolioToV1Test extends TestHarness implements TestConstants
{

	private uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder wireActBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.InstructionBuilder wireBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction;

	private InstructionWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireActBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder();
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder();

		wireService = new InstructionWirePortfolioToV1( new AccountWirePortfolioToV1() );
	}

	@Test
	public final void testToWire()
	{
		final Account origin = new AccountBase( ACCT_ID ).sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE );
		final Account destination = new AccountBase( DEST_ACCT_ID ).sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE );

		final Portfolio portfolio = new PortfolioBase( PORTFOLIO_ID ).name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN );

		final Batch batch = new BatchBase( BATCH_ID ).portfolio( portfolio )
				.index( BATCH_IDX )
				.name( BATCH_NAME )
				.userNumber( SUN )
				.set( SET )
				.generation( GENERATION )
				.section( SECTION )
				.sequence( SEQUENCE )
				.version( VERSION );

		final Instruction instruction = new InstructionBase( INSTRUCTION_ID, batch, LINE_NO ).index( INSTRUCTION_IDX )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( AMOUNT )
				.processingDate( DATE )
				.reference( REFERENCE )
				.rti( RTI );

		wireInstruction = wireService.toWire( instruction );

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
