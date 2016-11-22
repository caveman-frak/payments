package uk.co.bluegecko.pay.test.matcher;


import java.util.function.Function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Check if the passed object has a field with of the correct value
 *
 * @param <M>
 *            the type of object to check
 * @param <V>
 *            the type of value to check
 */
public class FunctionMatcher< M, V > extends TypeSafeMatcher< M >
{

	private final V value;
	private final String objectName;
	private final String fieldName;
	private final Function< M, V > function;

	/**
	 * Create a new ValueMatcher with the following parameters.
	 *
	 * @param objectName
	 *            the name of the object
	 * @param fieldName
	 *            the name of the field
	 * @param value
	 *            the expected value of the field
	 * @param function
	 *            the function to extract the actual field value
	 */
	public FunctionMatcher( final String objectName, final String fieldName, final V value,
			final Function< M, V > function )
	{
		super();

		this.value = value;
		this.objectName = objectName;
		this.fieldName = fieldName;
		this.function = function;
	}

	@Override
	public void describeTo( final Description description )
	{
		description.appendText( getDescription() )
				.appendText( " " )
				.appendValue( value );
	}

	protected String getDescription()
	{
		return "a " + objectName + " with " + fieldName;
	}

	@Override
	protected boolean matchesSafely( final M obj )
	{
		return getValue( obj ).equals( value );
	}

	protected V getValue( final M obj )
	{
		return function.apply( obj );
	}

	@Override
	protected void describeMismatchSafely( final M model, final Description mismatchDescription )
	{
		mismatchDescription.appendText( "was " )
				.appendText( getDescription() )
				.appendText( " " )
				.appendValue( getValue( model ) )
				.appendText( " expected " )
				.appendValue( value );
	}

	/**
	 * Create a new ValueMatcher with the following parameters.
	 *
	 * @param <M>
	 *            the type of object to check
	 * @param <V>
	 *            the type of value to check
	 * @param objectName
	 *            the name of the object
	 * @param fieldName
	 *            the name of the field
	 * @param value
	 *            the expected value of the field
	 * @param function
	 *            the function to extract the actual field value
	 * @return the new Matcher
	 */
	public static < M, V > Matcher< M > matcher( final String objectName, final String fieldName, final V value,
			final Function< M, V > function )
	{
		return new FunctionMatcher<>( objectName, fieldName, value, function );
	}

	/**
	 * Create a new ValueMatcher with the following parameters.
	 *
	 * @param <M>
	 *            the type of object to check
	 * @param <V>
	 *            the type of value to check
	 * @param objectName
	 *            the name of the object
	 * @param fieldName
	 *            the name of the field
	 * @param value
	 *            the expected value of the field
	 * @param fieldFunction
	 *            the function to extract the actual field value
	 * @param valueFunction
	 *            the function to extract the expected value
	 * @return the new Matcher
	 */
	public static < M, V, R > Matcher< M > matcher( final String objectName, final String fieldName, final R value,
			final Function< M, V > fieldFunction, final Function< R, V > valueFunction )
	{
		return new FunctionMatcher<>( objectName, fieldName, valueFunction.apply( value ), fieldFunction );
	}

}
