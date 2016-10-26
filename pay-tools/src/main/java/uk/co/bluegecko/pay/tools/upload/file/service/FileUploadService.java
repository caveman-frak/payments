package uk.co.bluegecko.pay.tools.upload.file.service;


import java.io.IOException;
import java.nio.file.FileSystem;

import uk.co.bluegecko.pay.tools.upload.file.cli.UploadCmdLine;


public interface FileUploadService
{

	public void processFiles( UploadCmdLine commandLine, FileSystem fileSystem ) throws IOException;

}
