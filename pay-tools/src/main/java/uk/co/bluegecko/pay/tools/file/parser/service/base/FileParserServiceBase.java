package uk.co.bluegecko.pay.tools.file.parser.service.base;


import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.bacs.std18.mapper.Standard18Mapper;
import uk.co.bluegecko.pay.bacs.std18.model.Row;
import uk.co.bluegecko.pay.common.service.ParsingService;
import uk.co.bluegecko.pay.tools.file.common.service.AbstractFileService;
import uk.co.bluegecko.pay.tools.file.parser.cli.ParserCmdLine;
import uk.co.bluegecko.pay.tools.file.parser.cli.ParserSettings;
import uk.co.bluegecko.pay.tools.file.parser.service.FileParserService;


@Service
public class FileParserServiceBase extends AbstractFileService implements FileParserService
{

	private static final Logger logger = LoggerFactory.getLogger( FileParserService.class );

	private final ParsingService parsingService;

	@Autowired
	public FileParserServiceBase( final ParsingService parsingService )
	{
		super();

		this.parsingService = parsingService;
	}

	@Override
	public void processFiles( final ParserCmdLine commandLine, final FileSystem fileSystem ) throws IOException
	{
		processFiles( commandLine.arguments()
				.stream(), commandLine.directory(), fileSystem, commandLine );
	}

	protected void processFiles( final Stream< String > stream, final String baseDir, final FileSystem fileSystem,
			final ParserSettings parserSettings )
	{
		stream.map( arg -> fileSystem.getPath( baseDir, arg ) )
				.filter( file -> isFileValid( file ) )
				.forEach( file -> parseFile( file, parserSettings ) );
	}

	protected void parseFile( final Path file, final ParserSettings parserSettings )
	{
		try
		{
			parse( Files.newBufferedReader( file, StandardCharsets.UTF_8 ), parserSettings );
			logger.warn( "Parsed '{}'", file.toString() );
		}
		catch ( final IOException e )
		{
			logger.error( String.format( "Unable to parse '{}'" ), file.toString() );
		}
	}

	protected void parse( final Reader dataFile, final ParserSettings parserSettings ) throws IOException
	{
		final Standard18Mapper standard18Mapper = createMapper( parserSettings );

		parsingService.parse( dataFile, standard18Mapper );
	}

	protected Standard18Mapper createMapper( final ParserSettings parserSettings )
	{
		final Standard18Mapper standard18Mapper = new Standard18Mapper();

		if ( parserSettings.instructions() )
		{
			standard18Mapper.addRow( Row.INSTR, printRow() );
		}
		if ( parserSettings.contras() )
		{
			standard18Mapper.addRow( Row.CONTRA, printRow() );
		}
		if ( parserSettings.headers() )
		{
			standard18Mapper.addRow( Row.VOL1, printRow() );
			standard18Mapper.addRow( Row.HDR1, printRow() );
			standard18Mapper.addRow( Row.HDR2, printRow() );
			standard18Mapper.addRow( Row.UHL1, printRow() );
		}
		if ( parserSettings.trailers() )
		{
			standard18Mapper.addRow( Row.EOF1, printRow() );
			standard18Mapper.addRow( Row.EOF2, printRow() );
			standard18Mapper.addRow( Row.UTL1, printRow() );
		}

		return standard18Mapper;
	}

	protected BiConsumer< Row, Object > printRow()
	{
		return ( final Row row, final Object value ) -> logger.warn( row.name() + " " + value );
	}
}
