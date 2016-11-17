package uk.co.bluegecko.pay.portfolio.model.base;


import java.util.Optional;

import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;


@Data
@Accessors( fluent = true )
public class BatchBase implements Batch
{

	private final Long id;
	private Integer index;
	private String userNumber;
	private Optional< Portfolio > portfolio;

	public BatchBase portfolio( final Portfolio portfolio )
	{
		this.portfolio = Optional.of( portfolio );

		return this;
	}

}
