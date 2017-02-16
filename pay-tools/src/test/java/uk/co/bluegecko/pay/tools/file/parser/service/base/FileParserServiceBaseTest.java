package uk.co.bluegecko.pay.tools.file.parser.service.base;


import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;

import uk.co.bluegecko.pay.bacs.std18.mapper.Standard18Mapper;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.bacs.std18.model.Volume;
import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingService;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.test.rule.FileSystemRule;
import uk.co.bluegecko.pay.tools.file.parser.cli.ParserCmdLine;
import uk.co.bluegecko.pay.tools.file.parser.cli.ParserSettings;


public class FileParserServiceBaseTest extends TestHarness
{

	private static final String FILE_1 = "/test1.txt";
	private static final String FILE_2 = "/test2,txt";

	private static final List< String > LINES_1 = Arrays.asList( "Line 1.1", "Line 1.2" );
	private static final List< String > LINES_2 = Arrays.asList( "Line 2.1", "Line 2.2" );

	@Rule
	public final FileSystemRule fileSystemRule = new FileSystemRule();

	@MockBean
	private ParsingService parsingService;

	private FileParserServiceBase fileParserService;

	private Cli< ParserCmdLine > cli;

	private ParserCmdLine parserSettings;

	@Before
	public void setUp() throws Exception
	{
		fileParserService = new FileParserServiceBase( parsingService );
		cli = CliFactory.createCli( ParserCmdLine.class );
		parserSettings = cli.parseArguments( FILE_1 );
	}

	@Test
	public final void testCreateMapperWithEnabled()
	{
		final ParserSettings parserSettings = cli.parseArguments( "-i", "-c", "-h", "-t", FILE_1 );

		final Standard18Mapper mapper = fileParserService.createMapper( parserSettings );

		assertThat( mapper.isSet( Row.INSTR ), is( true ) );
		assertThat( mapper.isSet( Row.CONTRA ), is( true ) );
		assertThat( mapper.isSet( Row.HDR1 ), is( true ) );
		assertThat( mapper.isSet( Row.EOF1 ), is( true ) );
	}

	@Test
	public final void testCreateMapperWithoutEnabled()
	{
		final Standard18Mapper mapper = fileParserService.createMapper( parserSettings );

		assertThat( mapper.isSet( Row.INSTR ), is( false ) );
		assertThat( mapper.isSet( Row.CONTRA ), is( false ) );
		assertThat( mapper.isSet( Row.HDR1 ), is( false ) );
		assertThat( mapper.isSet( Row.EOF1 ), is( false ) );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public final void testProcessFiles() throws IOException
	{
		final String[] fileNames =
			{ FILE_1, FILE_2 };
		try (final FileSystem fileSystem = fileSystemRule.getFileSystem())
		{
			Files.write( fileSystem.getPath( fileNames[0] ), LINES_1, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE );
			Files.write( fileSystem.getPath( fileNames[1] ), LINES_2, StandardCharsets.UTF_8,
					StandardOpenOption.CREATE );

			fileParserService.processFiles( Arrays.asList( fileNames )
					.stream(), "", fileSystem, parserSettings );
		}
		verify( parsingService, times( 2 ) ).parse( any( Reader.class ), any( Mapper.class ) );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public final void testParseFile() throws IOException
	{
		final String fileName = FILE_1;
		try (final FileSystem fileSystem = fileSystemRule.getFileSystem())
		{
			final Path file = fileSystem.getPath( fileName );

			fileParserService.parseFile( file, parserSettings );
		}
		verify( parsingService, never() ).parse( any( Reader.class ), any( Mapper.class ) );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public final void testProcessFilesCmdLine() throws IOException
	{
		final String fileName = FILE_1;
		try (final FileSystem fileSystem = fileSystemRule.getFileSystem())
		{
			Files.write( fileSystem.getPath( fileName ), LINES_1, StandardCharsets.UTF_8, StandardOpenOption.CREATE );

			fileParserService.processFiles( parserSettings, fileSystem );
		}
		verify( parsingService, times( 1 ) ).parse( any( Reader.class ), any( Mapper.class ) );
	}

	@Test
	public final void testConsumer()
	{
		final BiConsumer< Row, Object > consumer = fileParserService.printRow();
		assertThat( consumer, is( not( nullValue() ) ) );
		consumer.accept( Row.VOL1, Volume.builder()
				.build() );
	}

}
