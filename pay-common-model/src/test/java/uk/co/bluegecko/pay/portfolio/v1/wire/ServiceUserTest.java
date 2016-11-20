package uk.co.bluegecko.pay.portfolio.v1.wire;


import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.util.EnumSet;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.AuddisCode;
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
	public final void testCreditCodes()
	{
		ServiceUser serviceUser = serviceUserBuilder.build();
		assertThat( serviceUser.creditCodes(), is( empty() ) );

		serviceUser = serviceUserBuilder.clearCreditCodes()
				.build();
		assertThat( serviceUser.creditCodes(), is( empty() ) );

		serviceUser = serviceUserBuilder.creditCode( CreditCode.CREDIT )
				.build();
		assertThat( serviceUser.creditCodes(), hasItems( CreditCode.CREDIT ) );

		serviceUser = serviceUserBuilder.creditCodes( EnumSet.of( CreditCode.INTEREST ) )
				.build();
		assertThat( serviceUser.creditCodes(), hasItems( CreditCode.CREDIT, CreditCode.INTEREST ) );

		serviceUser = serviceUserBuilder.creditCode( CreditCode.DIVIDEND )
				.build();
		assertThat( serviceUser.creditCodes(),
				hasItems( CreditCode.CREDIT, CreditCode.DIVIDEND, CreditCode.INTEREST ) );

		serviceUser = serviceUserBuilder.clearCreditCodes()
				.build();
		assertThat( serviceUser.creditCodes(), is( empty() ) );
	}

	@Test
	public final void testDebitCodes()
	{
		serviceUserBuilder.build();
		serviceUserBuilder.clearDebitCodes()
				.build();
		serviceUserBuilder.debitCodes( EnumSet.of( DebitCode.FINAL ) )
				.clearDebitCodes()
				.build();
		serviceUserBuilder.debitCode( DebitCode.REGULAR )
				.build();
		serviceUserBuilder.debitCodes( EnumSet.of( DebitCode.REPRESENT ) )
				.build();
		serviceUserBuilder.debitCode( DebitCode.FIRST )
				.build();
		serviceUserBuilder.clearDebitCodes()
				.build();
	}

	@Test
	public final void testAuddisCodes()
	{
		serviceUserBuilder.build();
		serviceUserBuilder.clearAuddisCodes()
				.build();
		serviceUserBuilder.auddisCodes( EnumSet.of( AuddisCode.NEW ) )
				.clearAuddisCodes()
				.build();
		serviceUserBuilder.auddisCode( AuddisCode.NEW )
				.build();
		serviceUserBuilder.auddisCodes( EnumSet.of( AuddisCode.CANCEL ) )
				.build();
		serviceUserBuilder.auddisCode( AuddisCode.CONVERT )
				.build();
		serviceUserBuilder.clearAuddisCodes()
				.build();
	}

	@Test
	public final void testFpsCodes()
	{
		serviceUserBuilder.build();
		serviceUserBuilder.clearFpsCodes()
				.build();
		serviceUserBuilder.fpsCodes( EnumSet.of( CreditCode.INTEREST ) )
				.clearFpsCodes()
				.build();
		serviceUserBuilder.fpsCode( CreditCode.DIVIDEND )
				.build();
		serviceUserBuilder.fpsCodes( EnumSet.of( CreditCode.CREDIT ) )
				.build();
		serviceUserBuilder.fpsCode( CreditCode.INTEREST )
				.build();
		serviceUserBuilder.clearFpsCodes()
				.build();
	}

	@Test
	public final void testMarshalling() throws IOException
	{
		final ServiceUser serviceUser = serviceUserBuilder.creditCodes( EnumSet.allOf( CreditCode.class ) )
				.debitCode( DebitCode.REGULAR )
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
