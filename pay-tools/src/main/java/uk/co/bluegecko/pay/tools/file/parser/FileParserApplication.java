package uk.co.bluegecko.pay.tools.file.parser;


import java.nio.file.FileSystems;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.lexicalscope.jewel.cli.ArgumentValidationException;
import com.lexicalscope.jewel.cli.Cli;
import com.lexicalscope.jewel.cli.CliFactory;
import com.lexicalscope.jewel.cli.HelpRequestedException;

import uk.co.bluegecko.pay.common.service.base.ParsingServiceBase;
import uk.co.bluegecko.pay.tools.file.parser.cli.ParserCmdLine;
import uk.co.bluegecko.pay.tools.file.parser.service.FileParserService;


@SpringBootApplication
public class FileParserApplication
{

	private static final Logger logger = LoggerFactory.getLogger( FileParserApplication.class );

	public static void main( final String... args )
	{
		new SpringApplicationBuilder().sources( FileParserApplication.class, ParsingServiceBase.class )
				.profiles( "dev" )
				.properties( "spring.application.name:file-parser" )
				.web( false )
				.run( args );
	}

	@Bean
	public RestTemplate restTemplate( final RestTemplateBuilder builder )
	{
		return builder.build();
	}

	@Bean
	public CommandLineRunner processCommandLine( final FileParserService fileParserService )
	{
		return ( args ) ->
			{
				final Cli< ParserCmdLine > cli = CliFactory.createCli( ParserCmdLine.class );
				try
				{
					fileParserService.processFiles( cli.parseArguments( args ), FileSystems.getDefault() );
				}
				catch ( final HelpRequestedException ex )
				{
					logger.error( ex.getLocalizedMessage() );
				}
				catch ( final ArgumentValidationException | IllegalArgumentException ex )
				{
					logger.error( ex.getLocalizedMessage() );
					logger.error( "args {}", ArrayUtils.toString( args ) );
				}
			};
	}

}
