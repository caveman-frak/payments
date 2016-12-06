package uk.co.bluegecko.pay.portfolio.service.v1.test;


import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.AccountBase;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.base.InstructionBase;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;
import uk.co.bluegecko.pay.test.data.TestConstants;


public abstract class FakeDataFactory implements TestConstants
{

	protected final PortfolioBase createPortfolio()
	{
		return new PortfolioBase( 103L ).name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN );
	}

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio createWirePortfolio()
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Portfolio.builder()
				.id( 103L )
				.name( PORTFOLIO_NAME )
				.serialNo( SERIAL_NO )
				.userNumber( SUN )
				.build();
	}

	protected final BatchBase createBatch( final Portfolio portfolio )
	{
		return new BatchBase( BATCH_ID ).portfolio( portfolio )
				.index( BATCH_IDX )
				.name( BATCH_NAME )
				.userNumber( SUN )
				.set( SET )
				.generation( GENERATION )
				.section( SECTION )
				.sequence( SEQUENCE )
				.version( VERSION );
	}

	protected final BatchBase createBatch()
	{
		return createBatch( null );
	}

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Batch createWireBatch( final Long portfolioId )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Batch.builder()
				.id( BATCH_ID )
				.portfolio( portfolioId )
				.index( BATCH_IDX )
				.name( BATCH_NAME )
				.set( SET )
				.generation( GENERATION )
				.section( SECTION )
				.sequence( SEQUENCE )
				.version( VERSION )
				.build();
	}

	protected uk.co.bluegecko.pay.v1.portfolio.wire.Batch createWireBatch()
	{
		return createWireBatch( null );
	}

	protected final AccountBase createAccountOrigin()
	{
		return new AccountBase( ACCT_ID ).sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE );
	}

	protected final AccountBase createAccountDestination()
	{
		return new AccountBase( DEST_ACCT_ID ).sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE );
	}

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Account createWireAccountOrigin()
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder()
				.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
	}

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Account createWireAccountDestination()
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder()
				.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE )
				.build();
	}

	protected final InstructionBase createInstruction( final Account origin, final Account destination,
			final Batch batch )
	{
		return new InstructionBase( INSTRUCTION_ID, batch, LINE_NO ).index( INSTRUCTION_IDX )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( AMOUNT )
				.processingDate( DATE )
				.reference( REFERENCE )
				.rti( RTI );
	}

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction createWireInstruction(
			final uk.co.bluegecko.pay.v1.portfolio.wire.Account origin,
			final uk.co.bluegecko.pay.v1.portfolio.wire.Account destination )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder()
				.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( origin )
				.destination( destination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
				.build();
	}

}
