package uk.co.bluegecko.pay.portfolio.service.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.WireService;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.base.InstructionBase;


@Service
public class InstructionWirePortfolioToV1
		implements WireService< Instruction, uk.co.bluegecko.pay.v1.portfolio.wire.Instruction >
{

	private final AccountWirePortfolioToV1 wireServiceAccount;

	@Autowired
	public InstructionWirePortfolioToV1( final AccountWirePortfolioToV1 wireServiceAccount )
	{
		super();

		this.wireServiceAccount = wireServiceAccount;
	}

	@Override
	public Instruction fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction instruction,
			final Object... params )
	{
		final Batch batch = new BatchBase( instruction.batch() );

		return new InstructionBase( instruction.id(), batch, instruction.lineNo() )
				.origin( wireServiceAccount.fromWire( instruction.origin() ) )
				.destination( wireServiceAccount.fromWire( instruction.destination() ) )
				.index( instruction.index() )
				.amount( instruction.amount() )
				.processingDate( instruction.processingDate() )
				.transactionType( instruction.transactionType() )
				.reference( instruction.reference() )
				.rti( instruction.rti() );
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Instruction toWire( final Instruction instruction,
			final Object... params )
	{
		return uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder()
				.id( instruction.id() )
				.batch( instruction.batch()
						.id() )
				.lineNo( instruction.lineNo() )
				.origin( wireServiceAccount.toWire( instruction.origin() ) )
				.destination( wireServiceAccount.toWire( instruction.destination() ) )
				.index( instruction.index() )
				.amount( instruction.amount() )
				.processingDate( instruction.processingDate() )
				.transactionType( instruction.transactionType() )
				.reference( instruction.reference() )
				.rti( instruction.rti() )
				.build();
	}

}
