package uk.co.bluegecko.pay.test.matcher;


import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;


/**
 * Check if the passed object matches the supplied test
 *
 * @param <V>
 *            the type of value to check
 */
public class PredicateMatcher< V > extends TypeSafeMatcher< V >
{

	private final Predicate< V > test;
	private final String description;

	protected PredicateMatcher( final Predicate< V > test, final String description )
	{
		this.test = test;
		this.description = description;
	}

	@Override
	public void describeTo( final Description description )
	{
		description.appendText( this.description );
	}

	@Override
	protected boolean matchesSafely( final V obj )
	{
		return test.test( obj );
	}

	@Override
	protected void describeMismatchSafely( final V model, final Description mismatchDescription )
	{
		mismatchDescription.appendText( "was " )
				.appendValue( model )
				.appendText( " expected " )
				.appendText( description );
	}

	/**
	 * Construct a new matcher, checking for the supplied test.
	 *
	 * @param <V>
	 *            the type of value to check
	 * @param test
	 *            the test to match
	 * @param description
	 *            description of the test
	 * @return the SimpleMatcher
	 */
	public static final < V > Matcher< V > matcher( final Predicate< V > test, final String description )
	{
		return new PredicateMatcher<>( test, description );
	}

	/**
	 * Construct a new matcher, checking for the supplied test.
	 *
	 * @param <V>
	 *            the type of value to check
	 * @param test
	 *            the test to match
	 * @return the SimpleMatcher
	 */
	public static final < V > Matcher< V > matcher( final Predicate< V > test )
	{
		return matcher( test, "test expression" );
	}

}