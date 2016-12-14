package uk.co.bluegecko.pay.bacs.std18.mapper;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.test.FixedDates;


public class PenceHandlerTest implements FixedDates
{

	private static final BigDecimal AMOUNT = new BigDecimal( "165.87" );
	private static final String PENCE = "16587";

	private TypeHandler handler;

	@Before
	public void setUp() throws Exception
	{
		handler = new PenceHandler();
	}

	@Test
	public final void testFormat()
	{
		assertThat( handler.format( AMOUNT ), is( PENCE ) );
	}

	@Test
	public final void testParse() throws TypeConversionException
	{
		assertThat( handler.parse( PENCE ), is( AMOUNT ) );
	}

	@Test( expected = TypeConversionException.class )
	public final void testParseWithError() throws TypeConversionException
	{
		handler.parse( "   " );
	}

	@Test
	public final void testType()
	{
		assertSame( handler.getType(), BigDecimal.class );
	}

}
