package uk.co.bluegecko.pay.upload.readers;


import net.sf.flatpack.Record;
import uk.co.bluegecko.pay.portfolio.v1.wire.Account;
import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;


public class InstructionReader
{

	public Instruction read( final Record record, final int index )
	{
		return Instruction.builder()
				.index( index )
				.lineNo( record.getRowNo() )
				.origin( Account.builder()
						.sortCode( record.getString( "ORIG_SORT_CODE" ) )
						.number( record.getString( "ORIG_AC_NUMBER" ) )
						.name( record.getString( "USER_NAME" ) )
						.build() )
				.destination( Account.builder()
						.sortCode( record.getString( "DEST_SORT_CODE" ) )
						.number( record.getString( "DEST_AC_NUMBER" ) )
						.type( record.getString( "DEST_AC_TYPE" ) )
						.name( record.getString( "DEST_NAME" ) )
						.build() )
				.reference( record.getString( "DEST_REF" ) )
				.transactionType( record.getString( "TRANS_CODE" ) )
				.rti( record.getString( "RTI" ) )
				.pence( record.getString( "AMOUNT" ) )
				.julianDate( record.getLong( "PROCESS_DATE" ) )
				.build();
	}

}
