package uk.co.bluegecko.pay.upload.service;


import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;


public interface UploadService
{

	public long processFile( MultipartFile file ) throws IOException;

	public String getJobStatus( long jobId );

}
