package uk.co.bluegecko.pay.portfolio.wire.v1;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


@JsonDeserialize( builder = Instruction.InstructionBuilder.class )
@Data
@Builder
@Accessors( fluent = true )
public class Instruction
{

	private static final String BACS_CHARACTERS = "[A-Z0-9\\.\\-/& ]*";

	private final Account origin;
	private final Account destination;
	@Pattern( regexp = "[A-Z0-9]{2}" )
	private final String transactionType;
	@Digits( fraction = 2, integer = 11 )
	private final BigDecimal amount;
	@Pattern( regexp = "\\d{6}" )
	private final String serviceUserNumber;
	@Pattern( regexp = BACS_CHARACTERS )
	@Length( max = 18 )
	private final String reference;
	private final LocalDate processingDate;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class InstructionBuilder
	{

		private static final BigDecimal HUNDRED = new BigDecimal( "100.00" );

		public InstructionBuilder julianDate( final long julianDate )
		{
			processingDate = LocalDate.ofEpochDay( julianDate );

			return this;
		}

		public InstructionBuilder pence( final String pence )
		{
			amount = new BigDecimal( pence ).divide( HUNDRED );

			return this;
		}

	}

}
