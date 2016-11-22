package uk.co.bluegecko.pay.portfolio.model.base;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Account;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Instruction;


@Data
@Accessors( fluent = true )
public final class InstructionBase implements Instruction
{

	private static final String BACS_CHARACTERS = "[A-Z0-9\\.\\-/& ]*";

	@Min( 1 )
	private final Long id;
	private final Batch batch;
	@Min( 1 )
	private int index;
	@Min( 1 )
	private final Integer lineNo;
	private Account origin;
	private Account destination;
	@Pattern( regexp = "[A-Z0-9]{2}" )
	private String transactionType;
	@Pattern( regexp = BACS_CHARACTERS )
	@Length( max = 4 )
	private String rti;
	@Digits( fraction = 2, integer = 11 )
	private BigDecimal amount;
	@Pattern( regexp = BACS_CHARACTERS )
	@Length( max = 18 )
	private String reference;
	private LocalDate processingDate;

}
