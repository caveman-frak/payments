package uk.co.bluegecko.pay.tools.upload.file.service;


import java.io.IOException;

import uk.co.bluegecko.pay.tools.upload.file.cli.UploadCmdLine;


public interface FileUploadService
{

	public void processFiles( final UploadCmdLine commandLine ) throws IOException;

}
