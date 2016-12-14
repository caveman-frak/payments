package uk.co.bluegecko.pay.bacs.std18.mapper;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.beanio.types.TypeConversionException;
import org.beanio.types.TypeHandler;
import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.test.FixedDates;


public class JulianDateHandlerTest implements FixedDates
{

	private static final String JULIAN = "16587";

	private TypeHandler handler;

	@Before
	public void setUp() throws Exception
	{
		handler = new JulianDateHandler();
	}

	@Test
	public final void testFormat()
	{
		assertThat( handler.format( DATE ), is( JULIAN ) );
	}

	@Test
	public final void testParse() throws TypeConversionException
	{
		assertThat( handler.parse( JULIAN ), is( DATE ) );
	}

	@Test( expected = TypeConversionException.class )
	public final void testParseWithError() throws TypeConversionException
	{
		assertThat( handler.parse( "   " ), is( DATE ) );
	}

	@Test
	public final void testType()
	{
		assertSame( handler.getType(), LocalDate.class );
	}

}
