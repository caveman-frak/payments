package uk.co.bluegecko.pay.test.matcher;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.function.Predicate;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;


public class PredicateMatcherTest
{

	private static final String DESCRIPTION = "string starting with \"Hello\"";

	private Description description;
	private Matcher< String > matcher;
	private Predicate< String > predicate;

	@Before
	public final void setUp()
	{
		predicate = s -> s.startsWith( "Hello" );
		matcher = PredicateMatcher.matcher( predicate, DESCRIPTION );

		description = new StringDescription();
	}

	@Test
	public final void testSuccess()
	{
		final String string = "Hello World!";
		assertThat( matcher.matches( string ), is( true ) );

		matcher.describeMismatch( string, description );
		assertThat( description.toString(), is( "was \"Hello World!\" expected string starting with \"Hello\"" ) );
	}

	@Test
	public final void testDescribeTo()
	{
		matcher.describeTo( description );
		assertThat( description.toString(), is( DESCRIPTION ) );
	}

	@Test
	public final void testNoDescription()
	{
		matcher = PredicateMatcher.matcher( predicate );
		final String string = "Hello Everyone!";
		assertThat( matcher.matches( string ), is( true ) );

		matcher.describeTo( description );
		assertThat( description.toString(), is( "test expression" ) );
	}

	@Test
	public final void testFailure()
	{
		final String string = "Good Bye World!";
		assertThat( matcher.matches( string ), is( false ) );

		matcher.describeMismatch( string, description );
		assertThat( description.toString(), is( "was \"Good Bye World!\" expected string starting with \"Hello\"" ) );
	}

}
