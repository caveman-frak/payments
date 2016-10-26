package uk.co.bluegecko.pay.test.exception;


/**
 * Lambda marker for constructors and methods that will throw an exception.
 */
@FunctionalInterface
public interface MethodCapture
{

	/**
	 * Marker for constructor or method.
	 *
	 * @throws Throwable
	 *             the thrown exception
	 */
	void captureMethodCall() throws Throwable;

}
