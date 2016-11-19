package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.UserHeader.UserHeaderBuilder;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class UserHeaderTest extends TestHarness
{

	private UserHeaderBuilder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = UserHeader.builder()
				.processingDate( DATE.toEpochDay() )
				.dest( "DEST" )
				.currency( "GBP" )
				.country( "GB" )
				.workCode( "1 DAILY" )
				.file( "12345" )
				.audit( "01" );

	}

	@Test
	public final void testBuilder()
	{
		final UserHeader header = headerBuilder.build();

		assertThat( header.file(), is( "12345" ) );
		assertThat( header.processingDate(), is( DATE ) );
		assertThat( header.dest(), is( "DEST" ) );
		assertThat( header.currency(), is( "GBP" ) );
		assertThat( header.country(), is( "GB" ) );
		assertThat( header.workCode(), is( "1 DAILY" ) );
		assertThat( header.audit(), is( "01" ) );
	}

	@Test
	public final void testBuilderAlternatives()
	{
		final UserHeader header = headerBuilder.processingDate( DATE.plusDays( 1 ) )
				.build();
		assertThat( header.processingDate(), is( DATE.plusDays( 1 ) ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( headerBuilder.build()
				.toString(), startsWith( "UserHeader(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( UserHeader.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( headerBuilder.toString(), startsWith( "UserHeader.UserHeaderBuilder(" ) );
	}

}
