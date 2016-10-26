package uk.co.bluegecko.pay.test.exception;


import static org.junit.Assert.fail;


/**
 * Class to trap and return an expected method for test evaluation
 */
public class ThrowableCaptor
{

	private static final ThreadLocal< Throwable > LAST_THROWN = new ThreadLocal< >();

	/**
	 * Catch and return any exceptions thrown by the wrapped method.
	 *
	 * @param message
	 *            the message for the assert
	 * @param methodCapture
	 *            the functional method to catch the exception from
	 * @return the thrown exception
	 */
	public static Throwable capture( final String message, final MethodCapture methodCapture )
	{
		// clear last thrown exception
		LAST_THROWN.set( null );
		try
		{
			methodCapture.captureMethodCall();
		}
		catch ( final Throwable caught )
		{
			return caught( caught );
		}
		// fail if no exception was thrown
		fail( message );
		return null;
	}

	/**
	 * Catch and return any exceptions thrown by the wrapped method.
	 *
	 * @param methodCapture
	 *            the functional method to catch the exception from
	 * @return the thrown exception
	 */
	public static Throwable capture( final MethodCapture methodCapture )
	{
		return capture( "failed-to-catch-exception", methodCapture );
	}

	private static Throwable caught( final Throwable caught )
	{
		LAST_THROWN.set( caught );
		return LAST_THROWN.get();
	}

	/**
	 * Return the last thrown exception.
	 *
	 * @return the last thrown exception
	 */
	public static Throwable caught()
	{
		return LAST_THROWN.get();
	}
}