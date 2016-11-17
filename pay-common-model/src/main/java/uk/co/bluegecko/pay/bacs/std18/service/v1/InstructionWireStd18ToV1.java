package uk.co.bluegecko.pay.bacs.std18.service.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.common.service.WireService;


@Service
public class InstructionWireStd18ToV1
		implements WireService< Instruction, uk.co.bluegecko.pay.v1.portfolio.wire.Instruction >
{

	private final AccountWireStd18ToV1 wireServiceAccount;

	@Autowired
	public InstructionWireStd18ToV1( final AccountWireStd18ToV1 wireServiceAccount )
	{
		super();

		this.wireServiceAccount = wireServiceAccount;
	}

	@Override
	public Instruction fromWire( final uk.co.bluegecko.pay.v1.portfolio.wire.Instruction instruction,
			final Object... params )
	{
		return Instruction.builder()
				.lineNo( instruction.lineNo() )
				.origin( wireServiceAccount.fromWire( instruction.origin() ) )
				.destination( wireServiceAccount.fromWire( instruction.destination() ) )
				.index( instruction.index() )
				.amount( instruction.amount() )
				.processingDate( instruction.processingDate() )
				.transactionType( instruction.transactionType() )
				.reference( instruction.reference() )
				.rti( instruction.rti() )
				.build();
	}

	@Override
	public uk.co.bluegecko.pay.v1.portfolio.wire.Instruction toWire( final Instruction instruction,
			final Object... params )
	{
		final Long batch = params.length > 0 ? ( Long ) params[0] : null;

		return uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder()
				.batch( batch )
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
