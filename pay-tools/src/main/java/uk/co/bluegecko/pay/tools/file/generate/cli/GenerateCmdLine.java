package uk.co.bluegecko.pay.tools.file.generate.cli;


import com.lexicalscope.jewel.cli.Option;


public interface GenerateCmdLine
{

	@Option( defaultToNull = true, longName = "spring.output.ansi.enabled", hidden = true )
	public String ansi();

	@Option( defaultValue = "100123", shortName = "s", longName = "sun", description = "Service User Number" )
	public String sun();

	@Option( defaultValue = "", shortName = "d", longName = "directory", description = "directory of files" )
	public String directory();

	@Option( defaultValue = "10", shortName = "i", longName = "instructions", description = "number of instructions" )
	public int instructions();

	@Option( defaultValue = "false", shortName = "a", longName = "auddis",
			description = "generate auddis instructions" )
	public boolean auddis();

	@Option( defaultValue = "true", shortName = "c", longName = "contras", description = "generate contras" )
	public boolean contras();

	@Option( defaultToNull = true, shortName = "m", longName = "mapping", description = "Mapping File" )
	public String mappingFile();

	@Option( helpRequest = true, shortName = "?", longName = "help" )
	public boolean help();

}
