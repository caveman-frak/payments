package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.time.LocalDate;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;


public class JulianDateHandler implements TypeHandler
{

	@Override
	public Object parse( final String text ) throws TypeConversionException
	{
		final long day = Long.valueOf( text.trim() );
		return LocalDate.ofEpochDay( day );
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
