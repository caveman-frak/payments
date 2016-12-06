package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.UserHeader.UserHeaderBuilder;
import uk.co.bluegecko.pay.test.data.FakeDataConstants;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class UserHeaderTest extends TestHarness implements FakeDataConstants
{

	private UserHeaderBuilder headerBuilder;

	@Before
	public void setUp() throws Exception
	{
		headerBuilder = UserHeader.builder()
				.processingDate( DATE.toEpochDay() )
				.dest( DEST )
				.currency( CCY )
				.country( COUNTRY )
				.workCode( WORK_CODE )
				.file( FILE )
				.audit( AUDIT );

	}

	@Test
	public final void testBuilder()
	{
		final UserHeader header = headerBuilder.build();

		assertThat( header.file(), is( FILE ) );
		assertThat( header.processingDate(), is( DATE ) );
		assertThat( header.dest(), is( DEST ) );
		assertThat( header.currency(), is( CCY ) );
		assertThat( header.country(), is( COUNTRY ) );
		assertThat( header.workCode(), is( WORK_CODE ) );
		assertThat( header.audit(), is( AUDIT ) );
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
