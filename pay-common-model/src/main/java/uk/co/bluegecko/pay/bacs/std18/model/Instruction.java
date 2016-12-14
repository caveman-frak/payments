package uk.co.bluegecko.pay.bacs.std18.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;
import uk.co.bluegecko.pay.view.View;


@Value
@Builder( toBuilder = true )
@Accessors( fluent = true )
public class Instruction
{

	@Min( 1 )
	private final int index;
	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Integer lineNo;
	private final Account origin;
	private final Account destination;
	@Pattern( regexp = "[A-Z0-9]{2}" )
	private final String transactionType;
	@Pattern( regexp = Constants.BACS_CHARACTERS )
	@Length( max = 4 )
	private final String rti;
	@Digits( fraction = 2, integer = 11 )
	private final BigDecimal amount;
	@Pattern( regexp = Constants.BACS_CHARACTERS )
	@Length( max = 18 )
	private final String reference;
	private final LocalDate processingDate;

	public static final class InstructionBuilder implements BuilderConstants
	{

		public InstructionBuilder processingDate( final LocalDate processingDate )
		{
			this.processingDate = processingDate;

			return this;
		}

		public InstructionBuilder processingDate( final long julianDate )
		{
			return processingDate( LocalDate.ofEpochDay( julianDate ) );
		}

		public InstructionBuilder amount( final BigDecimal amount )
		{
			this.amount = amount;

			return this;
		}

		public InstructionBuilder amount( final String pence )
		{
			return amount( new BigDecimal( pence ).divide( HUNDRED ) );
		}

	}

}
