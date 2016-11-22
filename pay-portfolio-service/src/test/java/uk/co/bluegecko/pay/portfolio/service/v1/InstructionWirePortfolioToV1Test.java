package uk.co.bluegecko.pay.portfolio.service.v1;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Instruction;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;
import uk.co.bluegecko.pay.portfolio.model.base.AccountBase;
import uk.co.bluegecko.pay.portfolio.model.base.BatchBase;
import uk.co.bluegecko.pay.portfolio.model.base.InstructionBase;
import uk.co.bluegecko.pay.portfolio.model.base.PortfolioBase;
import uk.co.bluegecko.pay.test.harness.TestHarness;


public class InstructionWirePortfolioToV1Test extends TestHarness
{

	private uk.co.bluegecko.pay.v1.portfolio.wire.Account.AccountBuilder wireActBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.InstructionBuilder wireBuilder;
	private uk.co.bluegecko.pay.v1.portfolio.wire.Instruction wireInstruction;

	private InstructionWirePortfolioToV1 wireService;

	@Before
	public final void setUp()
	{
		wireActBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Account.builder();
		wireBuilder = uk.co.bluegecko.pay.v1.portfolio.wire.Instruction.builder();

		wireService = new InstructionWirePortfolioToV1( new AccountWirePortfolioToV1() );
	}

	@Test
	public final void testToWire()
	{
		final Account origin = new AccountBase( 101L ).sortCode( "123456" )
				.number( "12345678" )
				.name( "ACCOUNT #1" )
				.type( "1" );
		final Account destination = new AccountBase( 102L ).sortCode( "654321" )
				.number( "87654321" )
				.name( "ACCOUNT #2" )
				.type( "2" );

		final Portfolio portfolio = new PortfolioBase( 103L ).name( "Portfolio #1" )
				.serialNo( "321" )
				.userNumber( "123456" );

		final Batch batch = new BatchBase( 104L ).portfolio( portfolio )
				.index( 1 )
				.name( "Batch #1" )
				.userNumber( "123456" )
				.set( "00" )
				.generation( 1 )
				.section( 2 )
				.sequence( 3 )
				.version( 4 );

		final Instruction instruction = new InstructionBase( 105L, batch, 2 ).index( 1 )
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
				.amount( new BigDecimal( "00.02" ) )
				.processingDate( DATE )
				.reference( "A-REFERENCE" );

		wireInstruction = wireService.toWire( instruction );

		assertThat( wireInstruction.id(), is( instruction.id() ) );
		assertThat( wireInstruction.batch(), is( instruction.batch()
				.id() ) );
		assertThat( wireInstruction.index(), is( instruction.index() ) );
		assertThat( wireInstruction.lineNo(), is( instruction.lineNo() ) );
		assertThat( wireInstruction.origin()
				.sortCode(),
				is( instruction.origin()
						.sortCode() ) );
		assertThat( wireInstruction.origin()
				.number(),
				is( instruction.origin()
						.number() ) );
		assertThat( wireInstruction.origin()
				.name(),
				is( instruction.origin()
						.name() ) );
		assertThat( wireInstruction.destination()
				.sortCode(),
				is( instruction.destination()
						.sortCode() ) );
		assertThat( wireInstruction.destination()
				.number(),
				is( instruction.destination()
						.number() ) );
		assertThat( wireInstruction.destination()
				.type(),
				is( instruction.destination()
						.type() ) );
		assertThat( wireInstruction.reference(), is( instruction.reference() ) );
		assertThat( wireInstruction.transactionType(), is( instruction.transactionType() ) );
		assertThat( wireInstruction.rti(), is( instruction.rti() ) );
		assertThat( wireInstruction.amount(), is( instruction.amount() ) );
		assertThat( wireInstruction.processingDate(), is( instruction.processingDate() ) );
	}

	@Test
	public final void testFromWire()
	{
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account origin = wireActBuilder.sortCode( "123456" )
				.number( "12345678" )
				.name( "ACCOUNT #1" )
				.type( "1" )
				.build();
		final uk.co.bluegecko.pay.v1.portfolio.wire.Account destination = wireActBuilder.sortCode( "654321" )
				.number( "87654321" )
				.name( "ACCOUNT #2" )
				.type( "2" )
				.build();

		wireInstruction = wireBuilder.index( 1 )
				.lineNo( 3 )
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
				.amount( "1001" )
				.processingDate( DATE.toEpochDay() )
				.reference( "A-REFERENCE" )
				.build();

		final Instruction instruction = wireService.fromWire( wireInstruction );

		assertThat( instruction.index(), is( wireInstruction.index() ) );
		assertThat( instruction.lineNo(), is( wireInstruction.lineNo() ) );
		assertThat( instruction.origin()
				.sortCode(),
				is( wireInstruction.origin()
						.sortCode() ) );
		assertThat( instruction.origin()
				.number(),
				is( wireInstruction.origin()
						.number() ) );
		assertThat( instruction.origin()
				.name(),
				is( wireInstruction.origin()
						.name() ) );
		assertThat( instruction.destination()
				.sortCode(),
				is( wireInstruction.destination()
						.sortCode() ) );
		assertThat( instruction.destination()
				.number(),
				is( wireInstruction.destination()
						.number() ) );
		assertThat( instruction.destination()
				.type(),
				is( wireInstruction.destination()
						.type() ) );
		assertThat( instruction.reference(), is( wireInstruction.reference() ) );
		assertThat( instruction.transactionType(), is( wireInstruction.transactionType() ) );
		assertThat( instruction.rti(), is( wireInstruction.rti() ) );
		assertThat( instruction.amount(), is( wireInstruction.amount() ) );
		assertThat( instruction.processingDate(), is( wireInstruction.processingDate() ) );

	}

}
