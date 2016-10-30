package uk.co.bluegecko.pay.upload.service;


import java.io.IOException;
import java.io.InputStream;


public interface ParsingService
{

	public void parse( InputStream inputStream ) throws IOException;

}
