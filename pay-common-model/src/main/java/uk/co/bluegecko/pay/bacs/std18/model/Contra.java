package uk.co.bluegecko.pay.bacs.std18.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;


@Value
@Builder( toBuilder = true )
@Accessors( fluent = true )
public class Contra
{

	@Min( 1 )
	private final int index;
	private final Integer lineNo;
	private final Account origin;
	private final Account destination;
	@Pattern( regexp = "[A-Z0-9]{2}" )
	@Length( min = 2, max = 2 )
	private final String transactionType;
	@Pattern( regexp = Constants.BACS_CHARACTERS )
	@Length( max = 4 )
	private final String freeFormat;
	private final BigDecimal amount;
	@Pattern( regexp = Constants.BACS_CHARACTERS )
	@Length( max = 18 )
	private final String narrative;
	private final LocalDate processingDate;

	public static final class ContraBuilder implements BuilderConstants
	{

		public ContraBuilder processingDate( final LocalDate processingDate )
		{
			this.processingDate = processingDate;

			return this;
		}

		public ContraBuilder processingDate( final long julianDate )
		{
			return processingDate( LocalDate.ofEpochDay( julianDate ) );
		}

		public ContraBuilder amount( final BigDecimal amount )
		{
			this.amount = amount;

			return this;
		}

		public ContraBuilder amount( final String pence )
		{
			return amount( new BigDecimal( pence ).divide( HUNDRED ) );
		}

	}

}
