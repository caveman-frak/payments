package uk.co.bluegecko.pay.tools.upload.file.service;


import java.io.File;
import java.io.IOException;
import java.net.URI;


public interface FileUploadService
{

	public void checkConnection( final URI host ) throws IOException;

	public boolean isFileValid( final File file );

	public void uploadFile( final URI host, final File file );

}
