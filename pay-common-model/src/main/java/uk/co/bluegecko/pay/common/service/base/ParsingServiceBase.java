package uk.co.bluegecko.pay.common.service.base;


import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.beanio.BeanReader;
import org.beanio.BeanReaderErrorHandler;
import org.beanio.BeanReaderException;
import org.beanio.InvalidRecordException;
import org.beanio.RecordContext;
import org.beanio.StreamFactory;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uk.co.bluegecko.pay.common.service.Mapper;
import uk.co.bluegecko.pay.common.service.ParsingContext;
import uk.co.bluegecko.pay.common.service.ParsingService;


@Service
@Slf4j
public class ParsingServiceBase implements ParsingService, BeanReaderErrorHandler
{

	public StreamFactory factory()
	{
		final StreamFactory factory = StreamFactory.newInstance();
		return factory;
	}

	@Override
	public < T extends ParsingContext > void parse( final Reader dataFile, final Mapper< T > mapper ) throws IOException
	{
		final StreamFactory factory = mapper.addMapping( factory() );

		final BeanReader reader = factory.createReader( mapper.name(), dataFile );
		try
		{
			reader.setErrorHandler( this );
			final T context = mapper.newContext( reader );

			Object obj;
			while ( ( obj = reader.read() ) != null )
			{
				mapper.map( obj, context );
			}
		}
		finally
		{
			reader.close();
		}
	}

	@Override
	public void handleError( final BeanReaderException ex ) throws Exception
	{
		if ( ex instanceof InvalidRecordException )
		{
			log.warn( ex.getLocalizedMessage() );
			final RecordContext recordContext = ex.getRecordContext();
			for ( final String error : recordContext.getRecordErrors() )
			{
				log.warn( error );
			}
			for ( final Map.Entry< String, Collection< String > > fieldErrors : recordContext.getFieldErrors()
					.entrySet() )
			{
				log.warn( String.format( "Field '%s': %s", fieldErrors.getKey(),
						StringUtils.join( fieldErrors.getValue(), ',' ) ) );
			}
		}
		else
		{
			log.warn( ex.getLocalizedMessage() );
			throw ex;
		}
	}

}
