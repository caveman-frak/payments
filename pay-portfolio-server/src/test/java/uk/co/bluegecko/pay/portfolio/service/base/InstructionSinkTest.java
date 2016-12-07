package uk.co.bluegecko.pay.portfolio.service.base;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import uk.co.bluegecko.pay.portfolio.PortfolioApplication;
import uk.co.bluegecko.pay.portfolio.service.InstructionService;
import uk.co.bluegecko.pay.portfolio.service.v1.AccountWirePortfolioToV1;
import uk.co.bluegecko.pay.portfolio.service.v1.InstructionWirePortfolioToV1;
import uk.co.bluegecko.pay.test.harness.TestHarness;
import uk.co.bluegecko.pay.v1.portfolio.wire.Account;
import uk.co.bluegecko.pay.v1.portfolio.wire.Instruction;


@SpringBootTest( classes = PortfolioApplication.class, webEnvironment = WebEnvironment.NONE )
public class InstructionSinkTest extends TestHarness
{

	@MockBean
	private InstructionService instructionService;

	private InstructionSink instructionSink;
	private Instruction instruction;

	@Before
	public final void setUp()
	{
		final AccountWirePortfolioToV1 accountWireService = new AccountWirePortfolioToV1();
		final InstructionWirePortfolioToV1 instructionWireService = new InstructionWirePortfolioToV1(
				accountWireService );
		instructionSink = new InstructionSink( instructionWireService, instructionService );

		final Account origin = Account.builder()
				.sortCode( "123456" )
				.number( "12345678" )
				.name( "B.BAGGINS" )
				.build();
		final Account destination = Account.builder()
				.sortCode( "654321" )
				.number( "87654321" )
				.type( "8" )
				.build();

		instruction = Instruction.builder()
				.id( 10L )
				.index( 1 )
				.lineNo( 3 )
				.origin( origin )
				.destination( destination )
				.transactionType( "99" )
				.amount( "1001" )
				.processingDate( DATE.toEpochDay() )
				.reference( "A-REFERENCE" )
				.build();
	}

	@Test
	public final void testInstructionSink()
	{
		instructionSink.instructionSink( instruction );

		verify( instructionService, only() ).save( any( uk.co.bluegecko.pay.portfolio.model.Instruction.class ) );
	}

}
