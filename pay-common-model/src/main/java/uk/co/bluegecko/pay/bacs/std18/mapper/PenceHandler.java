package uk.co.bluegecko.pay.bacs.std18.mapper;


import java.math.BigDecimal;
import java.math.RoundingMode;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;


public class PenceHandler implements TypeHandler
{

	private static final BigDecimal HUNDRED = new BigDecimal( "100.00" );

	@Override
	public Object parse( final String text ) throws TypeConversionException
	{
		return new BigDecimal( text ).divide( HUNDRED );
	}

	@Override
	public String format( final Object value )
	{
		return ( ( BigDecimal ) value ).multiply( HUNDRED )
				.setScale( 0, RoundingMode.UNNECESSARY )
				.toPlainString();
	}

	@Override
	public Class< ? > getType()
	{
		return BigDecimal.class;
	}

}
