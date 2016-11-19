package uk.co.bluegecko.pay.bacs.std18.mapper;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.Properties;
import java.util.function.BiConsumer;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import net.sf.flatpack.RowRecord;
import net.sf.flatpack.xml.MetaData;
import uk.co.bluegecko.pay.bacs.std18.model.Contra;
import uk.co.bluegecko.pay.bacs.std18.model.Header1;
import uk.co.bluegecko.pay.bacs.std18.model.Header2;
import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.bacs.std18.model.UserHeader;
import uk.co.bluegecko.pay.bacs.std18.model.UserTrailer;
import uk.co.bluegecko.pay.bacs.std18.model.Volume;
import uk.co.bluegecko.pay.common.service.ParsingService;
import uk.co.bluegecko.pay.common.service.base.ParsingServiceBase;


public class Standard18MapperTest
{

	private static final String[] LINES =
		{ "VOL1173922                               100101                                1                          ",
				"HDR1A100101S  110010117392200010001       08194 08192 000000                                              ",
				"HDR2F0051200106                                   00                                                      ",
				"UHL1 14308999999    000000004 MULTI  001       AUD0000                                                    ",
				"0100390105996309940202421315692000000000000006BSDSAF 00000000006REF&LT 00000000006NAME   00000000006 14308     ",
				"4020242131569201740202421315692/00000000000001OSTEXT 09         CONTRA            OA NAME 09         16116",
				"EOF1A100101S  11001011739220001000108194 08192 000000                                              ",
				"EOF2F0051200106                                   00                                                      ",
				"UTL10000000000055000000000005500000010000010        0000000                                              " };

	private Standard18Mapper standard18Mapper;
	private BiConsumer< Row, Object > consumer;

	private ParsingService parsingService;

	@Before
	public void setUp() throws Exception
	{
		standard18Mapper = new Standard18Mapper();

		parsingService = new ParsingServiceBase();

		consumer = mock( BiConsumer.class );
	}

	@Test
	public final void testParseVol1() throws IOException
	{
		final Volume value = parseAndVerify( Row.VOL1, Volume.class );

		assertThat( value.serialNo(), is( "173922" ) );
		assertThat( value.accessibility(), is( nullValue() ) );
		assertThat( value.userNumber(), is( "100101" ) );
		assertThat( value.label(), is( "1" ) );
	}

	@Test
	public final void testParseHeader1() throws IOException
	{
		final Header1 value = parseAndVerify( Row.HDR1, Header1.class );

		assertThat( value.indicator(), is( Row.HDR1 ) );
		assertThat( value.file(), is( "A100101S  1100101" ) );
		assertThat( value.set(), is( "173922" ) );
		assertThat( value.section(), is( "0001" ) );
		assertThat( value.sequence(), is( "0001" ) );
		assertThat( value.generation(), is( nullValue() ) );
		assertThat( value.version(), is( nullValue() ) );
		assertThat( value.created(), is( LocalDate.of( 1992, Month.JUNE, 8 ) ) );
		assertThat( value.expires(), is( LocalDate.of( 1992, Month.JUNE, 6 ) ) );
		assertThat( value.accessibility(), is( nullValue() ) );
		assertThat( value.blockCount(), is( "000000" ) );
		assertThat( value.systemCode(), is( nullValue() ) );
	}

	@Test
	public final void testParseHeader2() throws IOException
	{
		final Header2 value = parseAndVerify( Row.HDR2, Header2.class );

		assertThat( value.indicator(), is( Row.HDR2 ) );
		assertThat( value.format(), is( "F" ) );
		assertThat( value.block(), is( "00512" ) );
		assertThat( value.offset(), is( "00" ) );
		assertThat( value.record(), is( "00106" ) );
	}

	@Test
	public final void testParseUserHeader() throws IOException
	{
		final UserHeader value = parseAndVerify( Row.UHL1, UserHeader.class );

		assertThat( value.processingDate(), is( LocalDate.of( 2009, Month.MARCH, 5 ) ) );
		assertThat( value.dest(), is( "999999" ) );
		assertThat( value.currency(), is( "00" ) );
		assertThat( value.country(), is( "000000" ) );
		assertThat( value.workCode(), is( "4 MULTI" ) );
		assertThat( value.file(), is( "001" ) );
		assertThat( value.audit(), is( "AUD0000" ) );
	}

	@Test
	public final void testParseInstruction() throws IOException
	{
		final Instruction value = parseAndVerify( Row.INSTR, Instruction.class );

		assertThat( value.index(), is( 0 ) );
		assertThat( value.lineNo(), is( 1 ) );
		assertThat( value.origin()
				.sortCode(), is( "402024" ) );
		assertThat( value.origin()
				.number(), is( "21315692" ) );
		assertThat( value.origin()
				.name(), is( "BSDSAF 00000000006" ) );
		assertThat( value.destination()
				.sortCode(), is( "010039" ) );
		assertThat( value.destination()
				.number(), is( "01059963" ) );
		assertThat( value.destination()
				.type(), is( "0" ) );
		assertThat( value.reference(), is( "REF&LT 00000000006" ) );
		assertThat( value.transactionType(), is( "99" ) );
		assertThat( value.rti(), is( "0000" ) );
		assertThat( value.amount(), is( new BigDecimal( "0.06" ) ) );
		assertThat( value.processingDate(), is( LocalDate.of( 2009, Month.MARCH, 5 ) ) );
	}

	@Test
	public final void testParseContra() throws IOException
	{
		final Contra value = parseAndVerify( Row.CONTRA, Contra.class );

		assertThat( value.index(), is( 0 ) );
		assertThat( value.lineNo(), is( 1 ) );
		assertThat( value.destination()
				.sortCode(), is( "402024" ) );
		assertThat( value.destination()
				.number(), is( "21315692" ) );
		assertThat( value.destination()
				.type(), is( "0" ) );
		assertThat( value.transactionType(), is( "17" ) );
		assertThat( value.origin()
				.sortCode(), is( "402024" ) );
		assertThat( value.origin()
				.number(), is( "21315692" ) );
		assertThat( value.origin()
				.name(), is( "OA NAME 09" ) );
		assertThat( value.amount(), is( new BigDecimal( "0.01" ) ) );
		assertThat( value.freeFormat(), is( "/000" ) );
		assertThat( value.narrative(), is( "OSTEXT 09" ) );
		assertThat( value.processingDate(), is( LocalDate.of( 2014, Month.FEBRUARY, 15 ) ) );
	}

	@Test
	public final void testParseEndOfFile1() throws IOException
	{
		final Header1 value = parseAndVerify( Row.EOF1, Header1.class );

		assertThat( value.indicator(), is( Row.EOF1 ) );
		assertThat( value.file(), is( "A100101S  1100101" ) );
		assertThat( value.set(), is( "173922" ) );
		assertThat( value.section(), is( "0001" ) );
		assertThat( value.sequence(), is( "0001" ) );
		assertThat( value.generation(), is( "0819" ) );
		assertThat( value.version(), is( "4" ) );
		assertThat( value.created(), is( LocalDate.of( 1992, Month.JUNE, 6 ) ) );
		assertThat( value.expires(), is( LocalDate.of( 1970, Month.JANUARY, 1 ) ) );
		assertThat( value.accessibility(), is( nullValue() ) );
		assertThat( value.blockCount(), is( nullValue() ) );
		assertThat( value.systemCode(), is( nullValue() ) );
	}

	@Test
	public final void testParseEndOfFile2() throws IOException
	{
		final Header2 value = parseAndVerify( Row.EOF2, Header2.class );

		assertThat( value.indicator(), is( Row.EOF2 ) );
		assertThat( value.format(), is( "F" ) );
		assertThat( value.block(), is( "00512" ) );
		assertThat( value.offset(), is( "00" ) );
		assertThat( value.record(), is( "00106" ) );
	}

	@Test
	public final void testParseUserTrailer() throws IOException
	{
		final UserTrailer value = parseAndVerify( Row.UTL1, UserTrailer.class );

		assertThat( value.creditCount(), is( 10 ) );
		assertThat( value.creditValue(), is( new BigDecimal( "0.55" ) ) );
		assertThat( value.debitCount(), is( 1 ) );
		assertThat( value.debitValue(), is( new BigDecimal( "0.55" ) ) );
		assertThat( value.ddiCount(), is( 0 ) );
		assertThat( value.serviceUser(), is( nullValue() ) );
	}

	@Test
	public final void testParseNoConsumer() throws IOException
	{
		standard18Mapper.add( Row.VOL1, consumer );

		parse( reader( LINES[Row.INSTR.ordinal()] ), standard18Mapper.mappingFile() );

		verify( consumer, never() ).accept( any(), any() );
	}

	@Test
	public final void testParseAlternateDefinition() throws IOException
	{
		standard18Mapper.add( Row.VOL1, consumer );

		parse( reader( LINES[Row.VOL1.ordinal()] ), new InputStreamReader(
				getClass().getResourceAsStream( "/mapping/alternate-vol.pzmap.xml" ), StandardCharsets.UTF_8 ) );

		final ArgumentCaptor< Volume > argument = ArgumentCaptor.forClass( Volume.class );
		verify( consumer ).accept( eq( Row.VOL1 ), argument.capture() );
		final Volume value = argument.getValue();

		assertThat( value.serialNo(), is( "173922" ) );
		assertThat( value.accessibility(), is( nullValue() ) );
		assertThat( value.userNumber(), is( "100101" ) );
		assertThat( value.label(), is( nullValue() ) );
	}

	@Test
	public final void testGetStringMissing()
	{
		assertThat( standard18Mapper.getString( createDummyRecord(), "MISSING" ), is( nullValue() ) );
	}

	@Test
	public final void testGetLongMissing()
	{
		assertThat( standard18Mapper.getLong( createDummyRecord(), "MISSING" ), is( 0L ) );
	}

	@Test
	public final void testGetIntMissing()
	{
		assertThat( standard18Mapper.getInt( createDummyRecord(), "MISSING" ), is( 0 ) );
	}

	protected RowRecord createDummyRecord()
	{
		return new RowRecord( new net.sf.flatpack.structure.Row(),
				new MetaData( Collections.emptyList(), Collections.emptyMap() ), false, new Properties(), false, false,
				false, false );
	}

	protected < T > T parseAndVerify( final Row row, final Class< T > type ) throws IOException
	{
		standard18Mapper.add( row, consumer );

		parse( reader( LINES[row.ordinal()] ), standard18Mapper.mappingFile() );

		final ArgumentCaptor< T > argument = ArgumentCaptor.forClass( type );
		verify( consumer ).accept( eq( row ), argument.capture() );
		return argument.getValue();
	}

	protected void parse( final Reader dataFile, final Reader mappingFile ) throws IOException
	{
		parsingService.parse( dataFile, mappingFile, standard18Mapper );
	}

	protected Reader reader( final String... lines )
	{
		return new StringReader( StringUtils.join( lines, '\n' ) );
	}

}
