package uk.co.bluegecko.pay.upload.readers;


import java.util.Arrays;
import java.util.List;

import net.sf.flatpack.Record;
import uk.co.bluegecko.pay.portfolio.v1.wire.Account;
import uk.co.bluegecko.pay.portfolio.v1.wire.Instruction;


public class InstructionReader implements Reader< Instruction >
{

	@Override
	public Instruction read( final Record record )
	{
		return Instruction.builder()
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
				.pence( record.getString( "AMOUNT" ) )
				.julianDate( record.getLong( "PROCESS_DATE" ) )
				.build();
	}

	public static void main( final String[] args )
	{
		final List< String > columns = Arrays.asList( "DEST_SORT_CODE-1-6", "DEST_AC_NUMBER-7-14", "DEST_AC_TYPE-15-15",
				"TRANS_CODE-16-17", "ORIG_SORT_CODE-18-23", "ORIG_AC_NUMBER-24-31", "SNGL-32-35", "AMOUNT-36-46",
				"USER_NAME-47-64", "DEST_REF-65-82", "DEST_NAME-83-100", "PROCESS_DATE-101-106" );

		int position = 1;
		for ( final String line : columns )
		{
			final String[] range = line.split( "\\-" );
			final String key = range[0];
			final int start = Integer.valueOf( range[1] );
			final int end = Integer.valueOf( range[2] );
			final int length = end + 1 - start;

			if ( start != position )
			{
				System.out
						.println( "position=" + position + ", start=" + start + ", end=" + end + ", length=" + length );
				throw new IllegalArgumentException( key + " out of step, start=" + start + ", position=" + position );
			}

			System.out.println( "\t<COLUMN name=\"" + key + "\" length=\"" + length + "\" />" );
			position += length;
		}

	}

}
