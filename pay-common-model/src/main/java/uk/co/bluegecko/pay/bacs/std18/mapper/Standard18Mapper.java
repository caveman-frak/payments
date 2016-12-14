package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.beanio.builder.FieldBuilder;
import org.beanio.builder.GroupBuilder;
import org.beanio.builder.RecordBuilder;
import org.beanio.builder.SegmentBuilder;
import org.beanio.builder.StreamBuilder;
import org.beanio.stream.fixedlength.FixedLengthRecordParserFactory;

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

	// argument position
	private static final String ARG_1 = "#1";
	private static final String ARG_2 = "#2";
	private static final String ARG_3 = "#3";
	private static final String ARG_4 = "#4";
	private static final String ARG_5 = "#5";
	private static final String ARG_6 = "#6";
	private static final String ARG_7 = "#7";
	private static final String ARG_8 = "#8";
	private static final String ARG_9 = "#9";
	private static final String ARG_10 = "#10";
	private static final String ARG_11 = "#11";
	private static final String ARG_12 = "#12";

	// fields
	private static final String BATCH = "batch";
	private static final String INDICATOR = "indicator";
	private static final String SERIAL_NO = "serialNo";
	private static final String USER_NUMBER = "userNumber";
	private static final String LABEL = "label";
	private static final String SET = "set";
	private static final String SECTION = "section";
	private static final String SEQUENCE = "sequence";
	private static final String GENERATION = "generation";
	private static final String VERSION = "version";
	private static final String CREATED = "created";
	private static final String EXPIRES = "expires";
	private static final String ACCESSIBILITY = "accessibility";
	private static final String BLOCK_COUNT = "blockCount";
	private static final String SYSTEM_CODE = "systemCode";
	private static final String FORMAT = "format";
	private static final String BLOCK = "block";
	private static final String RECORD = "record";
	private static final String OFFSET = "offset";
	private static final String DEST = "dest";
	private static final String CURRENCY = "currency";
	private static final String COUNTRY = "country";
	private static final String WORK_CODE = "workCode";
	private static final String FILE = "file";
	private static final String AUDIT = "audit";
	private static final String DEBIT_VALUE = "debitValue";
	private static final String CREDIT_VALUE = "creditValue";
	private static final String DEBIT_COUNT = "debitCount";
	private static final String CREDIT_COUNT = "creditCount";
	private static final String DDI_COUNT = "ddiCount";
	private static final String SERVICE_USER = "serviceUser";
	private static final String FREE_FORMAT = "freeFormat";
	private static final String NARRATIVE = "narrative";
	private static final String CONTRA = "contra";
	private static final String DESTINATION = "destination";
	private static final String TYPE = "type";
	private static final String TRANSACTION_TYPE = "transactionType";
	private static final String ORIGIN = "origin";
	private static final String SORT_CODE = "sortCode";
	private static final String NUMBER = "number";
	private static final String NAME = "name";
	private static final String RTI = "rti";
	private static final String AMOUNT = "amount";
	private static final String REFERENCE = "reference";
	private static final String PROCESSING_DATE = "processingDate";
	private static final String LINE_NO = "lineNo";
	private static final String INDEX = "index";

	// values
	private static final String ZERO = "0";
	private static final String EMPTY = "";

	private static final String STANDARD_18 = "standard18";
	private static final String FIXED_LENGTH = "fixedlength";
	private static final String PENCE_HANDLER = "penceHandler";
	private static final String JULIAN_DATE_HANDLER = "julianDateHandler";
	private final Map< Row, BiConsumer< Row, Object > > consumers;

	public Standard18Mapper()
	{
		consumers = new EnumMap<>( Row.class );
	}

	@Override
	public void map( final Object record, final BeanReader reader )
	{
		final String recordName = reader.getRecordName();
		if ( recordName.equals( Row.VOL1.name() ) )
		{
			consume( record, Row.VOL1, r -> ( Volume ) r );
		}
		else if ( recordName.equals( Row.HDR1.name() ) )
		{
			consume( record, Row.HDR1, r -> ( Header1 ) r );
		}
		else if ( recordName.equals( Row.HDR2.name() ) )
		{
			consume( record, Row.HDR2, r -> ( Header2 ) r );
		}
		else if ( recordName.equals( Row.UHL1.name() ) )
		{
			consume( record, Row.UHL1, r -> ( UserHeader ) r );
		}
		else if ( recordName.equals( Row.EOF1.name() ) )
		{
			consume( record, Row.EOF1, r -> ( Header1 ) r );
		}
		else if ( recordName.equals( Row.EOF2.name() ) )
		{
			consume( record, Row.EOF2, r -> ( Header2 ) r );
		}
		else if ( recordName.equals( Row.UTL1.name() ) )
		{
			consume( record, Row.UTL1, r -> ( UserTrailer ) r );
		}
		else if ( recordName.equals( Row.CONTRA.name() ) )
		{
			consume( record, Row.CONTRA, r -> ( ( Contra ) r ).toBuilder()
					.lineNo( reader.getLineNumber() )
					.build() );
		}
		else
		{
			consume( record, Row.INSTR, r -> ( ( Instruction ) r ).toBuilder()
					.lineNo( reader.getLineNumber() )
					.build() );
		}
	}

	@Override
	public String name()
	{
		return STANDARD_18;
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

	public StreamFactory addMapping( final StreamFactory factory )
	{
		factory.define( new StreamBuilder( name() ).format( FIXED_LENGTH )
				.addTypeHandler( JULIAN_DATE_HANDLER, LocalDate.class, new JulianDateHandler() )
				.addTypeHandler( PENCE_HANDLER, BigDecimal.class, new PenceHandler() )
				.parser( new FixedLengthRecordParserFactory() )
				.addRecord( createVolumeRecord().order( 1 ) )
				.addGroup( new GroupBuilder( BATCH ).order( 2 )
						.addRecord( createHeader1Record( Row.HDR1 ).order( 3 ) )
						.addRecord( createHeader2Record( Row.HDR2 ).order( 4 ) )
						.addRecord( createUserHeaderRecord().order( 5 ) )
						.addRecord( createContraRecord().order( 6 ) )
						.addRecord( createInstructionRecord().order( 6 ) )
						.addRecord( createHeader1Record( Row.EOF1 ).order( 7 ) )
						.addRecord( createHeader2Record( Row.EOF2 ).order( 8 ) )
						.addRecord( createUserTrailerRecord().order( 9 ) ) ) );
		return factory;
	}

	protected void consume( final Object record, final Row row, final Function< Object, Object > mapper )
	{
		if ( consumers.containsKey( row ) )
		{
			consumers.get( row )
					.accept( row, mapper.apply( record ) );
		}
	}

	protected RecordBuilder createVolumeRecord()
	{
		return new RecordBuilder( Row.VOL1.name() ).type( Volume.class )
				.length( 80 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.at( 0 )
						.length( 4 )
						.rid()
						.literal( Row.VOL1.name() )
						.ignore() )
				.addField( new FieldBuilder( SERIAL_NO ).getter( SERIAL_NO )
						.setter( ARG_1 )
						.occurs( 1 )
						.at( 4 )
						.length( 6 ) )
				.addField( new FieldBuilder( ACCESSIBILITY ).getter( ACCESSIBILITY )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 40 )
						.length( 1 ) )
				.addField( new FieldBuilder( USER_NUMBER ).getter( USER_NUMBER )
						.setter( ARG_3 )
						.occurs( 1 )
						.at( 41 )
						.length( 6 ) )
				.addField( new FieldBuilder( LABEL ).getter( LABEL )
						.setter( ARG_4 )
						.occurs( 1 )
						.at( 79 )
						.length( 1 ) );
	}

	protected RecordBuilder createHeader1Record( final Row row )
	{
		return new RecordBuilder( row.name() ).type( Header1.class )
				.length( 80 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.getter( INDICATOR )
						.setter( ARG_1 )
						.at( 0 )
						.length( 4 )
						.rid()
						.literal( row.name() ) )
				.addField( new FieldBuilder( FILE ).getter( FILE )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 4 )
						.length( 17 ) )
				.addField( new FieldBuilder( SET ).getter( SET )
						.setter( ARG_3 )
						.occurs( 1 )
						.at( 21 )
						.length( 6 ) )
				.addField( new FieldBuilder( SECTION ).getter( SECTION )
						.setter( ARG_4 )
						.occurs( 1 )
						.at( 27 )
						.length( 4 )
						.defaultValue( ZERO ) )
				.addField( new FieldBuilder( SEQUENCE ).getter( SEQUENCE )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 31 )
						.length( 4 )
						.defaultValue( ZERO ) )
				.addField( new FieldBuilder( GENERATION ).getter( GENERATION )
						.setter( ARG_6 )
						.occurs( 1 )
						.at( 35 )
						.length( 4 ) )
				.addField( new FieldBuilder( VERSION ).getter( VERSION )
						.setter( ARG_7 )
						.occurs( 1 )
						.at( 39 )
						.length( 2 )
						.defaultValue( ZERO ) )
				.addField( new FieldBuilder( CREATED ).getter( CREATED )
						.setter( ARG_8 )
						.trim()
						.typeHandler( JULIAN_DATE_HANDLER )
						.occurs( 1 )
						.at( 41 )
						.length( 6 ) )
				.addField( new FieldBuilder( EXPIRES ).getter( EXPIRES )
						.setter( ARG_9 )
						.trim()
						.typeHandler( JULIAN_DATE_HANDLER )
						.occurs( 1 )
						.at( 47 )
						.length( 6 ) )
				.addField( new FieldBuilder( ACCESSIBILITY ).getter( ACCESSIBILITY )
						.setter( ARG_10 )
						.occurs( 1 )
						.at( 53 )
						.length( 1 ) )
				.addField( new FieldBuilder( BLOCK_COUNT ).getter( BLOCK_COUNT )
						.setter( ARG_11 )
						.occurs( 1 )
						.at( 54 )
						.length( 6 ) )
				.addField( new FieldBuilder( SYSTEM_CODE ).getter( SYSTEM_CODE )
						.setter( ARG_12 )
						.occurs( 1 )
						.at( 60 )
						.length( 13 ) );
	}

	protected RecordBuilder createHeader2Record( final Row row )
	{
		return new RecordBuilder( row.name() ).type( Header2.class )
				.length( 80 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.getter( INDICATOR )
						.setter( ARG_1 )
						.at( 0 )
						.length( 4 )
						.rid()
						.literal( row.name() ) )
				.addField( new FieldBuilder( FORMAT ).getter( FORMAT )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 4 )
						.length( 1 ) )
				.addField( new FieldBuilder( BLOCK ).getter( BLOCK )
						.setter( ARG_3 )
						.occurs( 1 )
						.at( 5 )
						.length( 5 ) )
				.addField( new FieldBuilder( RECORD ).getter( RECORD )
						.setter( ARG_4 )
						.occurs( 1 )
						.at( 10 )
						.length( 5 ) )
				.addField( new FieldBuilder( OFFSET ).getter( OFFSET )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 50 )
						.length( 2 ) );
	}

	protected RecordBuilder createUserHeaderRecord()
	{
		return new RecordBuilder( Row.UHL1.name() ).type( UserHeader.class )
				.length( 80 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.at( 0 )
						.length( 4 )
						.rid()
						.literal( Row.UHL1.name() )
						.ignore() )
				.addField( new FieldBuilder( PROCESSING_DATE ).getter( PROCESSING_DATE )
						.setter( ARG_1 )
						.trim()
						.typeHandler( JULIAN_DATE_HANDLER )
						.occurs( 1 )
						.at( 4 )
						.length( 6 ) )
				.addField( new FieldBuilder( DEST ).getter( DEST )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 10 )
						.length( 10 ) )
				.addField( new FieldBuilder( CURRENCY ).getter( CURRENCY )
						.setter( ARG_3 )
						.occurs( 1 )
						.at( 20 )
						.length( 2 ) )
				.addField( new FieldBuilder( COUNTRY ).getter( COUNTRY )
						.setter( ARG_4 )
						.occurs( 1 )
						.at( 22 )
						.length( 6 ) )
				.addField( new FieldBuilder( WORK_CODE ).getter( WORK_CODE )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 28 )
						.length( 9 ) )
				.addField( new FieldBuilder( FILE ).getter( FILE )
						.setter( ARG_6 )
						.occurs( 1 )
						.at( 37 )
						.length( 3 ) )
				.addField( new FieldBuilder( AUDIT ).getter( AUDIT )
						.setter( ARG_7 )
						.occurs( 1 )
						.at( 47 )
						.length( 7 ) );
	}

	protected RecordBuilder createUserTrailerRecord()
	{
		return new RecordBuilder( Row.UTL1.name() ).type( UserTrailer.class )
				.length( 80 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.at( 0 )
						.length( 4 )
						.rid()
						.literal( Row.UTL1.name() )
						.ignore() )
				.addField( new FieldBuilder( DEBIT_VALUE ).getter( DEBIT_VALUE )
						.setter( ARG_1 )
						.trim()
						.typeHandler( PENCE_HANDLER )
						.occurs( 1 )
						.at( 4 )
						.length( 13 ) )
				.addField( new FieldBuilder( CREDIT_VALUE ).getter( CREDIT_VALUE )
						.setter( ARG_2 )
						.trim()
						.typeHandler( PENCE_HANDLER )
						.occurs( 1 )
						.at( 17 )
						.length( 13 ) )
				.addField( new FieldBuilder( DEBIT_COUNT ).getter( DEBIT_COUNT )
						.setter( ARG_3 )
						.occurs( 1 )
						.at( 30 )
						.length( 7 ) )
				.addField( new FieldBuilder( CREDIT_COUNT ).getter( CREDIT_COUNT )
						.setter( ARG_4 )
						.occurs( 1 )
						.at( 37 )
						.length( 7 ) )
				.addField( new FieldBuilder( DDI_COUNT ).getter( DDI_COUNT )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 52 )
						.length( 7 ) )
				.addField( new FieldBuilder( SERVICE_USER ).getter( SERVICE_USER )
						.setter( ARG_6 )
						.occurs( 1 )
						.at( 59 )
						.length( 21 ) );
	}

	protected RecordBuilder createContraRecord()
	{
		return new RecordBuilder( Row.CONTRA.name() ).type( Contra.class )
				.minLength( 100 )
				.maxLength( 106 )
				.occurs( 0, -1 )
				.addField( new FieldBuilder( CONTRA ).occurs( 1 )
						.at( 64 )
						.length( 6 )
						.rid()
						.literal( Row.CONTRA.name() )
						.ignore() )
				.addField( new FieldBuilder( INDEX ).getter( INDEX )
						.setter( ARG_1 )
						.occurs( 1 )
						.at( 1 )
						.length( 0 )
						.defaultValue( ZERO ) )
				.addField( new FieldBuilder( LINE_NO ).getter( LINE_NO )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 1 )
						.length( 0 )
						.defaultValue( ZERO ) )
				.addSegment( new SegmentBuilder( ORIGIN ).type( Account.class )
						.getter( ORIGIN )
						.setter( ARG_3 )
						.addField( new FieldBuilder( SORT_CODE ).getter( SORT_CODE )
								.setter( ARG_1 )
								.occurs( 1 )
								.at( 17 )
								.length( 6 ) )
						.addField( new FieldBuilder( NUMBER ).getter( NUMBER )
								.setter( ARG_2 )
								.occurs( 1 )
								.at( 23 )
								.length( 8 ) )
						.addField( new FieldBuilder( NAME ).getter( NAME )
								.setter( ARG_3 )
								.occurs( 1 )
								.at( 82 )
								.length( 18 ) )
						.addField( new FieldBuilder( TYPE ).getter( TYPE )
								.setter( ARG_4 )
								.occurs( 1 )
								.at( 1 )
								.length( 0 )
								.defaultValue( EMPTY ) ) )
				.addSegment( new SegmentBuilder( DESTINATION ).type( Account.class )
						.getter( DESTINATION )
						.setter( ARG_4 )
						.addField( new FieldBuilder( SORT_CODE ).getter( SORT_CODE )
								.setter( ARG_1 )
								.occurs( 1 )
								.at( 0 )
								.length( 6 ) )
						.addField( new FieldBuilder( NUMBER ).getter( NUMBER )
								.setter( ARG_2 )
								.occurs( 1 )
								.at( 6 )
								.length( 8 ) )
						.addField( new FieldBuilder( NAME ).getter( NAME )
								.setter( ARG_3 )
								.occurs( 1 )
								.at( 1 )
								.length( 0 )
								.defaultValue( EMPTY ) )
						.addField( new FieldBuilder( TYPE ).getter( TYPE )
								.setter( ARG_4 )
								.occurs( 1 )
								.at( 14 )
								.length( 1 ) ) )
				.addField( new FieldBuilder( TRANSACTION_TYPE ).getter( TRANSACTION_TYPE )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 15 )
						.length( 2 ) )
				.addField( new FieldBuilder( FREE_FORMAT ).getter( FREE_FORMAT )
						.setter( ARG_6 )
						.occurs( 1 )
						.at( 31 )
						.length( 4 ) )
				.addField( new FieldBuilder( AMOUNT ).getter( AMOUNT )
						.setter( ARG_7 )
						.typeHandler( PENCE_HANDLER )
						.occurs( 1 )
						.at( 35 )
						.length( 11 ) )
				.addField( new FieldBuilder( NARRATIVE ).getter( NARRATIVE )
						.setter( ARG_8 )
						.occurs( 1 )
						.at( 46 )
						.length( 18 ) )
				.addField( new FieldBuilder( PROCESSING_DATE ).getter( PROCESSING_DATE )
						.setter( ARG_9 )
						.typeHandler( JULIAN_DATE_HANDLER )
						.occurs( 0, 1 )
						.at( 100 )
						.length( 6 ) );
	}

	protected RecordBuilder createInstructionRecord()
	{
		return new RecordBuilder( Row.INSTR.name() ).type( Instruction.class )
				.minLength( 100 )
				.maxLength( 106 )
				.occurs( 0, 1 )
				.addField( new FieldBuilder( INDICATOR ).occurs( 1 )
						.rid()
						.at( 0 )
						.length( 4 )
						.regex( "\\d{4}" )
						.ignore() )
				.addField( new FieldBuilder( CONTRA ).occurs( 1 )
						.rid()
						.at( 0 )
						.length( 6 )
						.regex( "((?!CONTRA).)*" )
						.ignore() )
				.addField( new FieldBuilder( INDEX ).getter( INDEX )
						.setter( ARG_1 )
						.occurs( 1 )
						.at( 1 )
						.length( 0 )
						.defaultValue( ZERO ) )
				.addField( new FieldBuilder( LINE_NO ).getter( LINE_NO )
						.setter( ARG_2 )
						.occurs( 1 )
						.at( 1 )
						.length( 0 )
						.defaultValue( ZERO ) )
				.addSegment( new SegmentBuilder( ORIGIN ).type( Account.class )
						.getter( ORIGIN )
						.setter( ARG_3 )
						.addField( new FieldBuilder( SORT_CODE ).getter( SORT_CODE )
								.setter( ARG_1 )
								.occurs( 1 )
								.at( 17 )
								.length( 6 ) )
						.addField( new FieldBuilder( NUMBER ).getter( NUMBER )
								.setter( ARG_2 )
								.occurs( 1 )
								.at( 23 )
								.length( 8 ) )
						.addField( new FieldBuilder( NAME ).getter( NAME )
								.setter( ARG_3 )
								.occurs( 1 )
								.at( 46 )
								.length( 18 ) )
						.addField( new FieldBuilder( TYPE ).getter( TYPE )
								.setter( ARG_4 )
								.occurs( 1 )
								.at( 1 )
								.length( 0 )
								.defaultValue( EMPTY ) ) )
				.addSegment( new SegmentBuilder( DESTINATION ).type( Account.class )
						.getter( DESTINATION )
						.setter( ARG_4 )
						.addField( new FieldBuilder( SORT_CODE ).getter( SORT_CODE )
								.setter( ARG_1 )
								.occurs( 1 )
								.at( 0 )
								.length( 6 ) )
						.addField( new FieldBuilder( NUMBER ).getter( NUMBER )
								.setter( ARG_2 )
								.occurs( 1 )
								.at( 6 )
								.length( 8 ) )
						.addField( new FieldBuilder( NAME ).getter( NAME )
								.setter( ARG_3 )
								.occurs( 1 )
								.at( 82 )
								.length( 18 ) )
						.addField( new FieldBuilder( TYPE ).getter( TYPE )
								.setter( ARG_4 )
								.occurs( 1 )
								.at( 14 )
								.length( 1 ) ) )
				.addField( new FieldBuilder( TRANSACTION_TYPE ).getter( TRANSACTION_TYPE )
						.setter( ARG_5 )
						.occurs( 1 )
						.at( 15 )
						.length( 2 ) )
				.addField( new FieldBuilder( RTI ).getter( RTI )
						.setter( ARG_6 )
						.occurs( 1 )
						.at( 31 )
						.length( 4 ) )
				.addField( new FieldBuilder( AMOUNT ).getter( AMOUNT )
						.setter( ARG_7 )
						.typeHandler( PENCE_HANDLER )
						.occurs( 1 )
						.at( 35 )
						.length( 11 ) )
				.addField( new FieldBuilder( REFERENCE ).getter( REFERENCE )
						.setter( ARG_8 )
						.occurs( 1 )
						.at( 64 )
						.length( 18 ) )
				.addField( new FieldBuilder( PROCESSING_DATE ).getter( PROCESSING_DATE )
						.setter( ARG_9 )
						.typeHandler( JULIAN_DATE_HANDLER )
						.occurs( 0, 1 )
						.at( 100 )
						.length( 6 ) );
	}

}
