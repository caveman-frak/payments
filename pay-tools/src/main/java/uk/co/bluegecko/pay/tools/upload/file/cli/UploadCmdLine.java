package uk.co.bluegecko.pay.tools.upload.file.cli;


import java.net.URI;
import java.util.List;

import com.lexicalscope.jewel.cli.Option;
import com.lexicalscope.jewel.cli.Unparsed;


public interface UploadCmdLine
{

	@Option( defaultToNull = true, longName = "spring.output.ansi.enabled", hidden = true )
	public String ansi();

	@Option( defaultValue = "http://red-dragon.local:8081", shortName = "h", longName = "host",
			description = "host URL of upload server" )
	public URI host();

	@Option( defaultValue = "", shortName = "d", longName = "directory", description = "directory of files" )
	public String directory();

	@Unparsed( minimum = 1, name = "file(s) to upload (at least one)",
			description = "list of file(s) to upload (at least one)" )
	public List< String > arguments();

	@Option( helpRequest = true, shortName = "?", longName = "help" )
	public boolean help();

}
