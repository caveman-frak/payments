package uk.co.bluegecko.pay.common.service;


import java.io.IOException;
import java.io.Reader;


public interface ParsingService
{

	public < T extends ParsingContext > void parse( final Reader dataFile, final Mapper< T > mapper )
			throws IOException;

}
