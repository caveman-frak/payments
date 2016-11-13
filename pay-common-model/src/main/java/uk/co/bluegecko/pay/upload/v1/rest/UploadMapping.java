package uk.co.bluegecko.pay.upload.v1.rest;


public interface UploadMapping
{

	public static final String FILE = "/file/";
	public static final String UPLOAD = "/upload/";
	public static final String JOB_ID = "jobId";
	public static final String STATUS = "/status/{" + JOB_ID + "}";

}
