package uk.co.bluegecko.pay.tools.file.parser.service;


import java.io.IOException;
import java.nio.file.FileSystem;

import uk.co.bluegecko.pay.tools.file.parser.cli.ParserCmdLine;


public interface FileParserService
{

	public void processFiles( ParserCmdLine parseArguments, FileSystem default1 ) throws IOException;

}
