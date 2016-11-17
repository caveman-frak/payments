package uk.co.bluegecko.pay.bacs.std18.model;


import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.common.model.BuilderConstants;


@Value
@Builder
@Accessors( fluent = true )
public class Contra
{

	private final Account destination;
	private final Account origin;
	private final String transactionType;
	private final String freeFormat;
	private final BigDecimal amount;
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
			processingDate = LocalDate.ofEpochDay( julianDate );

			return this;
		}

		public ContraBuilder amount( final BigDecimal amount )
		{
			this.amount = amount;

			return this;
		}

		public ContraBuilder amount( final String pence )
		{
			amount = new BigDecimal( pence ).divide( HUNDRED );

			return this;
		}

	}

}
