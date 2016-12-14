package uk.co.bluegecko.pay.common.service;


import java.io.IOException;
import java.io.Reader;

import org.beanio.StreamFactory;


public interface ParsingService
{

	public StreamFactory factory();

	public void parse( final Reader dataFile, final StreamFactory factory, final Mapper mapper ) throws IOException;

}
