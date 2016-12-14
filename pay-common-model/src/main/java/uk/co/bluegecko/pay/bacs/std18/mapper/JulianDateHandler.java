package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.time.LocalDate;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;


public class JulianDateHandler implements TypeHandler
{

	@Override
	public Object parse( final String text ) throws TypeConversionException
	{
		try
		{
			final long day = Long.valueOf( text.trim() );
			return LocalDate.ofEpochDay( day );
		}
		catch ( final NumberFormatException ex )
		{
			throw new TypeConversionException( "Invalid Julian Date value '" + text + "'", ex );
		}
	}

	@Override
	public String format( final Object value )
	{
		return String.valueOf( ( ( LocalDate ) value ).toEpochDay() );
	}

	@Override
	public Class< ? > getType()
	{
		return LocalDate.class;
	}

}
