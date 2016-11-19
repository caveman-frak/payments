package uk.co.bluegecko.pay.v1.portfolio.wire;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = Instruction.InstructionBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public class Instruction
{

	private static final String BACS_CHARACTERS = "[A-Z0-9\\.\\-/& ]*";

	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Long id;
	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Long batch;
	@Min( 1 )
	private final int index;
	@Min( 1 )
	@JsonView( View.Detailed.class )
	private final Integer lineNo;
	private final Account origin;
	private final Account destination;
	@Pattern( regexp = "[A-Z0-9]{2}" )
	private final String transactionType;
	@Pattern( regexp = BACS_CHARACTERS )
	@Length( max = 4 )
	private final String rti;
	@Digits( fraction = 2, integer = 11 )
	private final BigDecimal amount;
	@Pattern( regexp = BACS_CHARACTERS )
	@Length( max = 18 )
	private final String reference;
	private final LocalDate processingDate;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class InstructionBuilder implements BuilderConstants
	{

		public InstructionBuilder processingDate( final LocalDate processingDate )
		{
			this.processingDate = processingDate;

			return this;
		}

		public InstructionBuilder processingDate( final long julianDate )
		{
			processingDate = LocalDate.ofEpochDay( julianDate );

			return this;
		}

		public InstructionBuilder amount( final BigDecimal amount )
		{
			this.amount = amount;

			return this;
		}

		public InstructionBuilder amount( final String pence )
		{
			amount = new BigDecimal( pence ).divide( HUNDRED );

			return this;
		}

	}

}
