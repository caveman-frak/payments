package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.EnumSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.CreditCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.DebitCode;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.ServiceUser;
import uk.co.bluegecko.pay.v1.portfolio.wire.ServiceUser.ServiceUserBuilder;


public class ServiceUserTest extends TestHarness
{

	private ServiceUserBuilder serviceUserBuilder;

	@Before
	public void setUp() throws Exception
	{
		serviceUserBuilder = ServiceUser.builder()
				.userNumber( "123456" )
				.indirect( false );
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final ServiceUser serviceUser = serviceUserBuilder.creditCodes( EnumSet.allOf( CreditCode.class ) )
				.debitCodes( Stream.of( DebitCode.REGULAR )
						.collect( Collectors.toSet() ) )
				.build();

		final String str = write( serviceUser );

		assertThat( stripWhitespace( str ), is( "{\"userNumber\":\"123456\",\"indirect\":false,"
				+ "\"creditCodes\":[\"CREDIT\",\"INTEREST\",\"DIVIDEND\"],\"debitCodes\":[\"REGULAR\"]}" ) );

		final ServiceUser result = read( str, ServiceUser.class );

		assertThat( result.userNumber(), is( "123456" ) );
		assertThat( result.indirect(), is( false ) );
		assertThat( result.creditCodes(), hasItems( CreditCode.CREDIT, CreditCode.DIVIDEND, CreditCode.INTEREST ) );
		assertThat( result.debitCodes(), hasItems( DebitCode.REGULAR ) );
		assertThat( result.auddisCodes(), is( empty() ) );
		assertThat( result.fpsCodes(), is( empty() ) );
	}

	@Test
	public final void testToString()
	{
		assertThat( serviceUserBuilder.build()
				.toString(), startsWith( "ServiceUser(" ) );
	}

	@Test
	public final void testEquals()
	{
		EqualsVerifier.forClass( ServiceUser.class )
				.verify();
	}

	@Test
	public final void testBuilderToString()
	{
		assertThat( serviceUserBuilder.toString(), startsWith( "ServiceUser.ServiceUserBuilder(" ) );
	}
}
