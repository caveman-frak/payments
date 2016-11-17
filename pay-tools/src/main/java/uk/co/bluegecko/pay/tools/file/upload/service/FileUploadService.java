package uk.co.bluegecko.pay.tools.file.upload.service;


import java.io.IOException;
import java.nio.file.FileSystem;

import uk.co.bluegecko.pay.tools.file.upload.cli.UploadCmdLine;


public interface FileUploadService
{

	public void processFiles( UploadCmdLine commandLine, FileSystem fileSystem ) throws IOException;

}
