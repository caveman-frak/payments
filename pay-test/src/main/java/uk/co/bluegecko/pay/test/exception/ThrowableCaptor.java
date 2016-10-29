package uk.co.bluegecko.pay.test.exception;


import static org.junit.Assert.fail;


/**
 * Class to trap and return an expected method for test evaluation
 */
public class ThrowableCaptor
{

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
		try
		{
			methodCapture.captureMethodCall();
		}
		catch ( final Throwable caught )
		{
			return caught;
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

}