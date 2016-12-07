package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;

import net.sf.flatpack.Record;
import uk.co.bluegecko.pay.bacs.std18.model.Account;
import uk.co.bluegecko.pay.bacs.std18.model.Contra;
import uk.co.bluegecko.pay.bacs.std18.model.Header1;
import uk.co.bluegecko.pay.bacs.std18.model.Header2;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.bacs.std18.model.UserHeader;
import uk.co.bluegecko.pay.bacs.std18.model.UserTrailer;
import uk.co.bluegecko.pay.bacs.std18.model.Volume;
import uk.co.bluegecko.pay.common.service.Mapper;


public class Standard18Mapper implements Mapper
{

	private static final String MAPPING_FILE = "/mapping/standard18.pzmap.xml";

	private static final String SERIAL_NO = "SERIAL_NO";
	private static final String USER_NUMBER = "USER_NUMBER";
	private static final String LABEL = "LABEL";
	private static final String SET = "SET";
	private static final String SECTION = "SECTION";
	private static final String SEQUENCE = "SEQUENCE";
	private static final String GENERATION = "GENERATION";
	private static final String VERSION = "VERSION";
	private static final String CREATED = "CREATED";
	private static final String EXPIRES = "EXPIRES";
	private static final String ACCESSIBILITY = "ACCESSIBILITY";
	private static final String BLOCK_COUNT = "BLOCK_COUNT";
	private static final String SYSTEM_CODE = "SYSTEM_CODE";
	private static final String FORMAT = "FORMAT";
	private static final String BLOCK = "BLOCK";
	private static final String RECORD = "RECORD";
	private static final String OFFSET = "OFFSET";
	private static final String DEST = "DEST";
	private static final String CURRENCY = "CURRENCY";
	private static final String COUNTRY = "COUNTRY";
	private static final String WORK_CODE = "WORK_CODE";
	private static final String FILE = "FILE";
	private static final String AUDIT = "AUDIT";
	private static final String USER_NAME = "USER_NAME";
	private static final String DEST_NAME = "DEST_NAME";
	private static final String DEST_REF = "DEST_REF";
	private static final String RTI = "RTI";
	private static final String PROCESS_DATE = "PROCESS_DATE";
	private static final String DEST_SORT_CODE = "DEST_SORT_CODE";
	private static final String DEST_AC_NUMBER = "DEST_AC_NUMBER";
	private static final String DEST_AC_TYPE = "DEST_AC_TYPE";
	private static final String TRANS_CODE = "TRANS_CODE";
	private static final String ORIG_SORT_CODE = "ORIG_SORT_CODE";
	private static final String ORIG_AC_NUMBER = "ORIG_AC_NUMBER";
	private static final String ORIG_AC_NAME = "ORIG_AC_NAME";
	private static final String FREE = "FREE";
	private static final String AMOUNT = "AMOUNT";
	private static final String NARRATIVE = "NARRATIVE";
	private static final String PROCESSING = "PROCESSING";
	private static final String DEBIT_VALUE = "DEBIT_VALUE";
	private static final String CREDIT_VALUE = "CREDIT_VALUE";
	private static final String DEBIT_COUNT = "DEBIT_COUNT";
	private static final String CREDIT_COUNT = "CREDIT_COUNT";
	private static final String DDI_COUNT = "DDI_COUNT";
	private static final String SERVICE_USER = "SERVICE_USER";

	private final Map< Row, BiConsumer< Row, Object > > consumers;

	private int index = 0;

	public Standard18Mapper()
	{
		consumers = new EnumMap<>( Row.class );
	}

	public Standard18Mapper add( final Row row, final BiConsumer< Row, Object > consumer )
	{
		consumers.put( row, consumer );
		return this;
	}

	public boolean isSet( final Row row )
	{
		return consumers.containsKey( row );
	}

	@Override
	public void map( final Record record )
	{
		if ( record.isRecordID( Row.VOL1.name() ) )
		{
			consume( record, Row.VOL1, ( final Record r ) -> volume( r ) );
		}
		else if ( record.isRecordID( Row.HDR1.name() ) )
		{
			consume( record, Row.HDR1, ( final Record r ) -> header1( r, Row.HDR1 ) );
		}
		else if ( record.isRecordID( Row.HDR2.name() ) )
		{
			consume( record, Row.HDR2, ( final Record r ) -> header2( r, Row.HDR2 ) );
		}
		else if ( record.isRecordID( Row.UHL1.name() ) )
		{
			consume( record, Row.UHL1, ( final Record r ) -> userHeader( r ) );
		}
		else if ( record.isRecordID( Row.EOF1.name() ) )
		{
			consume( record, Row.EOF1, ( final Record r ) -> header1( r, Row.EOF1 ) );
		}
		else if ( record.isRecordID( Row.EOF2.name() ) )
		{
			consume( record, Row.EOF2, ( final Record r ) -> header2( r, Row.EOF2 ) );
		}
		else if ( record.isRecordID( Row.UTL1.name() ) )
		{
			consume( record, Row.UTL1, ( final Record r ) -> userTrailer( r ) );
		}
		else if ( record.isRecordID( Row.CONTRA.name() ) )
		{
			consume( record, Row.CONTRA, ( final Record r ) -> contra( r ) );
		}
		else
		{
			consume( record, Row.INSTR, ( final Record r ) -> instruction( r ) );
		}
	}

	public Reader mappingFile()
	{
		return new InputStreamReader( getClass().getResourceAsStream( MAPPING_FILE ), StandardCharsets.UTF_8 );
	}

	protected void consume( final Record record, final Row row, final Function< Record, Object > mapper )
	{
		if ( consumers.containsKey( row ) )
		{
			consumers.get( row )
					.accept( row, mapper.apply( record ) );
		}
	}

	protected Volume volume( final Record record )
	{
		return Volume.builder()
				.serialNo( getString( record, SERIAL_NO ) )
				.accessibility( getString( record, ACCESSIBILITY ) )
				.userNumber( getString( record, USER_NUMBER ) )
				.label( getString( record, LABEL ) )
				.build();
	}

	protected Header1 header1( final Record record, final Row row )
	{
		return Header1.builder()
				.indicator( row )
				.file( getString( record, FILE ) )
				.set( getString( record, SET ) )
				.section( getInt( record, SECTION ) )
				.sequence( getInt( record, SEQUENCE ) )
				.generation( getInt( record, GENERATION ) )
				.version( getInt( record, VERSION ) )
				.created( getLong( record, CREATED ) )
				.expires( getLong( record, EXPIRES ) )
				.accessibility( getString( record, ACCESSIBILITY ) )
				.blockCount( getString( record, BLOCK_COUNT ) )
				.systemCode( getString( record, SYSTEM_CODE ) )
				.build();
	}

	protected Header2 header2( final Record record, final Row row )
	{
		return Header2.builder()
				.indicator( row )
				.format( getString( record, FORMAT ) )
				.block( getString( record, BLOCK ) )
				.record( getString( record, RECORD ) )
				.offset( getString( record, OFFSET ) )
				.build();
	}

	protected UserHeader userHeader( final Record record )
	{
		return UserHeader.builder()
				.processingDate( getLong( record, PROCESSING ) )
				.dest( getString( record, DEST ) )
				.currency( getString( record, CURRENCY ) )
				.country( getString( record, COUNTRY ) )
				.workCode( getString( record, WORK_CODE ) )
				.file( getString( record, FILE ) )
				.audit( getString( record, AUDIT ) )
				.build();
	}

	protected Instruction instruction( final Record record )
	{
		return Instruction.builder()
				.index( index++ )
				.lineNo( record.getRowNo() )
				.origin( Account.builder()
						.sortCode( getString( record, ORIG_SORT_CODE ) )
						.number( getString( record, ORIG_AC_NUMBER ) )
						.name( getString( record, USER_NAME ) )
						.build() )
				.destination( Account.builder()
						.sortCode( getString( record, DEST_SORT_CODE ) )
						.number( getString( record, DEST_AC_NUMBER ) )
						.type( getString( record, DEST_AC_TYPE ) )
						.name( getString( record, DEST_NAME ) )
						.build() )
				.reference( getString( record, DEST_REF ) )
				.transactionType( getString( record, TRANS_CODE ) )
				.rti( getString( record, RTI ) )
				.amount( getString( record, AMOUNT ) )
				.processingDate( getLong( record, PROCESS_DATE ) )
				.build();
	}

	protected Contra contra( final Record record )
	{
		return Contra.builder()
				.index( index++ )
				.lineNo( record.getRowNo() )
				.destination( Account.builder()
						.sortCode( getString( record, DEST_SORT_CODE ) )
						.number( getString( record, DEST_AC_NUMBER ) )
						.type( getString( record, DEST_AC_TYPE ) )
						.build() )
				.transactionType( getString( record, TRANS_CODE ) )
				.origin( Account.builder()
						.sortCode( getString( record, ORIG_SORT_CODE ) )
						.number( getString( record, ORIG_AC_NUMBER ) )
						.name( getString( record, ORIG_AC_NAME ) )
						.build() )
				.freeFormat( getString( record, FREE ) )
				.amount( getString( record, AMOUNT ) )
				.narrative( getString( record, NARRATIVE ) )
				.processingDate( getLong( record, PROCESSING ) )
				.build();
	}

	protected UserTrailer userTrailer( final Record record )
	{
		return UserTrailer.builder()
				.debitValue( getString( record, DEBIT_VALUE ) )
				.creditValue( getString( record, CREDIT_VALUE ) )
				.debitCount( getInt( record, DEBIT_COUNT ) )
				.creditCount( getInt( record, CREDIT_COUNT ) )
				.ddiCount( getInt( record, DDI_COUNT ) )
				.serviceUser( getString( record, SERVICE_USER ) )
				.build();
	}

	protected String getString( final Record record, final String key )
	{
		return record.contains( key ) ? record.getString( key ) : null;
	}

	protected Integer getInt( final Record record, final String key )
	{
		return isNotBlank( record, key ) ? record.getInt( key ) : 0;
	}

	protected Long getLong( final Record record, final String key )
	{
		return isNotBlank( record, key ) ? record.getLong( key ) : null;
	}

	protected boolean isNotBlank( final Record record, final String key )
	{
		return record.contains( key ) && StringUtils.isNotBlank( record.getString( key ) );
	}

}
