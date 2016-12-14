package uk.co.bluegecko.pay.common.service;


import java.io.IOException;
import java.io.Reader;


public interface ParsingService
{

	public void parse( final Reader dataFile, final Mapper mapper ) throws IOException;

}
