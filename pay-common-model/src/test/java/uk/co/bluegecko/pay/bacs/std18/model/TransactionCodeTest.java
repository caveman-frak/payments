package uk.co.bluegecko.pay.bacs.std18.model;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.AuddisCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.CreditCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.DebitCode;


public class TransactionCodeTest
{

	@Test
	public final void testByCodePassCredit()
	{
		assertThat( TransactionCode.byCode( CreditCode.class, "Z4" ), is( CreditCode.INTEREST ) );
	}

	@Test
	public final void testByCodePassDebit()
	{
		assertThat( TransactionCode.byCode( DebitCode.class, "17" ), is( DebitCode.REGULAR ) );
	}

	@Test
	public final void testByCodePassAudis()
	{
		assertThat( TransactionCode.byCode( AuddisCode.class, "0N" ), is( AuddisCode.NEW ) );
	}

	@Test
	public final void testValueOfCredit()
	{
		assertThat( CreditCode.valueOf( "INTEREST" ), is( CreditCode.INTEREST ) );
	}

	@Test
	public final void testValueOfDebit()
	{
		assertThat( DebitCode.valueOf( "REGULAR" ), is( DebitCode.REGULAR ) );
	}

	@Test
	public final void testValueOfAudis()
	{
		assertThat( AuddisCode.valueOf( "NEW" ), is( AuddisCode.NEW ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void testByCodeFail()
	{
		TransactionCode.byCode( CreditCode.class, "18" );
	}

	@Test
	public final void testAllByCodePass()
	{
		assertThat( TransactionCode.byCode( "Z5" ), is( CreditCode.DIVIDEND ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void testAllByCodeFail()
	{
		TransactionCode.byCode( "XX" );
	}

	@Test
	public final void testCreditType()
	{
		assertThat( CreditCode.CREDIT.transactionType(), is( TransactionType.CREDIT ) );
	}

	@Test
	public final void testDebitType()
	{
		assertThat( DebitCode.REGULAR.transactionType(), is( TransactionType.DEBIT ) );
	}

	@Test
	public final void testAuddisType()
	{
		assertThat( AuddisCode.NEW.transactionType(), is( TransactionType.AUDDIS ) );
	}

}
