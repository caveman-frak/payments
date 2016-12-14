package uk.co.bluegecko.pay.common.service.base;


import java.io.IOException;
import java.io.Reader;

import org.beanio.BeanReader;
import org.beanio.StreamFactory;
import org.springframework.stereotype.Service;

import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingService;


@Service
public class ParsingServiceBase implements ParsingService
{

	@Override
	public StreamFactory factory()
	{
		final StreamFactory factory = StreamFactory.newInstance();
		return factory;
	}

	@Override
	public void parse( final Reader dataFile, final StreamFactory factory, final Mapper mapper ) throws IOException
	{
		final BeanReader reader = factory.createReader( mapper.name(), dataFile );
		try
		{
			Object obj;
			while ( ( obj = reader.read() ) != null )
			{
				mapper.map( obj, reader );
			}
		}
		finally
		{
			reader.close();
		}
	}

}
