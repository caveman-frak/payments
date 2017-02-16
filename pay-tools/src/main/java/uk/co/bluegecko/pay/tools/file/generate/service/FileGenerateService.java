package uk.co.bluegecko.pay.tools.file.generate.service;


import java.io.IOException;
import java.nio.file.FileSystem;

import uk.co.bluegecko.pay.tools.file.generate.cli.GenerateCmdLine;


public interface FileGenerateService
{

	public void generateFile( final GenerateCmdLine commandLine, final FileSystem fileSystem ) throws IOException;

}
