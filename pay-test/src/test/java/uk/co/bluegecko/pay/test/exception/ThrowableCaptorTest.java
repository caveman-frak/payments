package uk.co.bluegecko.pay.test.exception;


import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;


public class ThrowableCaptorTest
{

	@Test
	public final void testWithException()
	{
		final Throwable ex = ThrowableCaptor.capture( () -> throwException() );

		assertThat( ex, is( instanceOf( IllegalArgumentException.class ) ) );
	}

	@Test( expected = AssertionError.class )
	public final void testWithoutException()
	{
		ThrowableCaptor.capture( "Message", () -> noException() );
	}

	private final void throwException()
	{
		throw new IllegalArgumentException();
	}

	private final void noException()
	{
		// do nothing
	}

}
