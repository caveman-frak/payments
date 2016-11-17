package uk.co.bluegecko.pay.common.model;


import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.common.model.Message.Classification;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class MessageTest extends TestHarness
{

	private static final String KEY_1 = "foo1";
	private static final String KEY_2 = "foo2";
	private static final String MESSAGE_1 = "bar1";
	private static final String MESSAGE_2 = "bar2";

	private Message message;

	@Before
	public final void setUp()
	{
		message = new Message();
	}

	@Test
	public final void testHasClassificationEmpty()
	{
		assertThat( message.has( Classification.ERROR ), is( false ) );
	}

	@Test
	public final void testPopulateSingleStrings()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		assertThat( message.has( Classification.ERROR ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasSize( 1 ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasItem( MESSAGE_1 ) );
	}

	@Test
	public final void testPopulateMultipleStrings()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1, MESSAGE_2 );
		assertThat( message.has( Classification.ERROR ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasSize( 2 ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasItems( MESSAGE_1, MESSAGE_2 ) );
	}

	@Test
	public final void testPopulateMultipleKeys()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		message.add( Classification.ERROR, KEY_2, MESSAGE_2 );
		assertThat( message.has( Classification.ERROR ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasSize( 1 ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasItem( MESSAGE_1 ) );
		assertThat( message.has( Classification.ERROR, KEY_2 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_2 ), hasSize( 1 ) );
		assertThat( message.text( Classification.ERROR, KEY_2 ), hasItem( MESSAGE_2 ) );
	}

	@Test
	public final void testPopulateMultipleClassification()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		message.add( Classification.WARN, KEY_2, MESSAGE_2 );
		assertThat( message.has( Classification.ERROR ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasSize( 1 ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasItem( MESSAGE_1 ) );
		assertThat( message.has( Classification.WARN, KEY_2 ), is( true ) );
		assertThat( message.text( Classification.WARN, KEY_2 ), hasSize( 1 ) );
		assertThat( message.text( Classification.WARN, KEY_2 ), hasItem( MESSAGE_2 ) );
	}

	@Test
	public final void testClearAll()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		message.clear();
		assertThat( message.has( Classification.ERROR ), is( false ) );
	}

	@Test
	public final void testClearByClassification()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		message.clear( Classification.ERROR );
		assertThat( message.has( Classification.ERROR ), is( false ) );
	}

	@Test
	public final void testClearByClassificationAndKey()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		message.add( Classification.ERROR, KEY_2, MESSAGE_2 );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_2 ), is( true ) );
		message.clear( Classification.ERROR, KEY_1 );
		assertThat( message.has( Classification.ERROR ), is( true ) );
		assertThat( message.has( Classification.ERROR, KEY_1 ), is( false ) );
		assertThat( message.has( Classification.ERROR, KEY_2 ), is( true ) );
	}

	@Test
	public final void testToStringEmpty()
	{
		assertThat( message.toString(), is( "Message[]" ) );
	}

	@Test
	public final void testToStringOneKey()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		assertThat( message.toString(), is( "Message[\n\tERROR\tfoo1 : bar1\n]" ) );
	}

	@Test
	public final void testToStringOneKeyMultipleText()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1, MESSAGE_2 );
		assertThat( message.toString(), is( "Message[\n\tERROR\tfoo1 : bar1; bar2\n]" ) );
	}

	@Test
	public final void testToStringMultipleKeys()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		message.add( Classification.ERROR, KEY_2, MESSAGE_2 );
		assertThat( message.toString(), is( "Message[\n\tERROR\n\t\tfoo1 : bar1\n\t\tfoo2 : bar2\n]" ) );
	}

	@Test
	public final void testToStringMultipleClassification()
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		message.add( Classification.WARN, KEY_2, MESSAGE_2 );
		assertThat( message.toString(), is( "Message[\n\tERROR\tfoo1 : bar1\n\tWARN\tfoo2 : bar2\n]" ) );
	}

	@Test( expected = AssertionError.class )
	public final void testNullClassification()
	{
		message.add( null, KEY_1, MESSAGE_1 );
	}

	@Test( expected = AssertionError.class )
	public final void testNullKey()
	{
		message.add( Classification.ERROR, null, MESSAGE_1 );
	}

	@Test( expected = AssertionError.class )
	public final void testNullText()
	{
		message.add( Classification.ERROR, KEY_1, ( String[] ) null );
	}

	@Test( expected = AssertionError.class )
	public final void testMissingText()
	{
		message.add( Classification.ERROR, KEY_1 );
	}

	@Test
	public final void testMarshallingEmpty() throws IOException
	{
		final String str = write( message );

		assertThat( stripWhitespace( str ), is( "{}" ) );

		final Message result = read( str, Message.class );

		assertThat( result.has( Classification.ERROR ), is( false ) );
	}

	@Test
	public final void testMarshallingWithText() throws IOException
	{
		message.add( Classification.ERROR, KEY_1, MESSAGE_1 );
		final String str = write( message );

		assertThat( stripWhitespace( str ), is( "{\"messages\":{\"ERROR\":{\"foo1\":[\"bar1\"]}}}" ) );

		final Message result = read( str, Message.class );

		assertThat( result.text( Classification.ERROR, KEY_1 ), hasItem( MESSAGE_1 ) );
	}

	@Test
	public final void testBuilder()
	{
		final Message message = Message.builder()
				.classification( Classification.ERROR )
				.message( KEY_1, MESSAGE_1 )
				.classification( Classification.WARN )
				.message( KEY_2, MESSAGE_2 )
				.build();

		assertThat( message.has( Classification.ERROR, KEY_1 ), is( true ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasSize( 1 ) );
		assertThat( message.text( Classification.ERROR, KEY_1 ), hasItem( MESSAGE_1 ) );
		assertThat( message.has( Classification.WARN, KEY_2 ), is( true ) );
		assertThat( message.text( Classification.WARN, KEY_2 ), hasSize( 1 ) );
		assertThat( message.text( Classification.WARN, KEY_2 ), hasItem( MESSAGE_2 ) );
		assertThat( message.has( Classification.INFO ), is( false ) );
	}

}
