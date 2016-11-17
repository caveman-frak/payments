package uk.co.bluegecko.pay.tools.file.parser.cli;


import java.util.List;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;


public interface ParserCmdLine extends ParserSettings
{

	@Option( defaultToNull = true, longName = "spring.output.ansi.enabled", hidden = true )
	public String ansi();

	@Option( defaultValue = "", shortName = "d", longName = "directory", description = "directory of files" )
	public String directory();

	@Override
	@Option( shortName = "h", longName = "header", description = "print headers" )
	public boolean headers();

	@Override
	@Option( shortName = "t", longName = "trailer", description = "print trailers" )
	public boolean trailers();

	@Override
	@Option( shortName = "i", longName = "instructions", description = "print instructions" )
	public boolean instructions();

	@Override
	@Option( shortName = "c", longName = "contras", description = "print contras" )
	public boolean contras();

	@Unparsed( minimum = 1, name = "file(s) to parse (at least one)",
			description = "list of file(s) to parse (at least one)" )
	public List< String > arguments();

	@Option( helpRequest = true, shortName = "?", longName = "help" )
	public boolean help();

}
