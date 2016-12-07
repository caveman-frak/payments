package uk.co.bluegecko.pay.bacs.std18.service.v1.test;


import uk.co.bluegecko.pay.bacs.std18.model.Account;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;


public abstract class FakeDataFactory implements FakeDataConstants
{

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction createWireInstruction(
			final uk.co.bluegecko.pay.v1.portfolio.wire.Account accountOrigin,
			final uk.co.bluegecko.pay.v1.portfolio.wire.Account accountDestination )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder()
				.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( accountOrigin )
				.destination( accountDestination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
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

	protected final uk.co.bluegecko.pay.v1.portfolio.wire.Account createWireAccountOrigin()
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder()
				.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
	}

	protected final Instruction createInstruction( final Account accountOrigin, final Account accountDestination )
	{
		return Instruction.builder()
				.index( INSTRUCTION_IDX )
				.lineNo( LINE_NO )
				.origin( accountOrigin )
				.destination( accountDestination )
				.transactionType( TRANSACTION_TYPE )
				.amount( PENCE )
				.processingDate( DATE.toEpochDay() )
				.reference( REFERENCE )
				.rti( RTI )
				.build();
	}

	protected final Account createAccountDestination()
	{
		return Account.builder()
				.sortCode( DEST_SORT_CODE )
				.number( DEST_ACCT_NO )
				.name( DEST_ACCT_NAME )
				.type( DEST_ACCT_TYPE )
				.build();
	}

	protected final Account createAccountOrigin()
	{
		return Account.builder()
				.sortCode( SORT_CODE )
				.number( ACCT_NO )
				.name( ACCT_NAME )
				.type( ACCT_TYPE )
				.build();
	}

}
