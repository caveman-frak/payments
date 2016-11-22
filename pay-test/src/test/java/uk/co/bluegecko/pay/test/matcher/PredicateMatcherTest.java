package uk.co.bluegecko.pay.test.matcher;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.junit.Before;
import org.junit.Test;


public class PredicateMatcherTest
{

	private Matcher< String > matcher;

	private Description description;

	@Before
	public final void setUp()
	{
		matcher = PredicateMatcher.matcher( s -> s.startsWith( "Hello" ), "string starting with \"Hello\"" );

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
	public final void testNoDescription()
	{
		matcher = PredicateMatcher.matcher( s -> s.startsWith( "Hello" ) );
		final String string = "Hello World!";
		assertThat( matcher.matches( string ), is( true ) );
	}

	@Test
	public final void testFailure()
	{
		final String string = "Good Bye World!";
		assertThat( matcher.matches( string ), is( false ) );

		matcher.describeMismatch( string, description );
		assertThat( description.toString(), is( "was \"Good Bye World!\" expected string starting with \"Hello\"" ) );

		final StringDescription description = new StringDescription();
		matcher.describeTo( description );
		assertThat( description.toString(), is( "string starting with \"Hello\"" ) );
	}

}
