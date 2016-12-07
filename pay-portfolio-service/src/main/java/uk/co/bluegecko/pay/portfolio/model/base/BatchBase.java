package uk.co.bluegecko.pay.portfolio.model.base;


import java.util.Optional;

import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;


@Data
@Accessors( fluent = true )
public final class BatchBase implements Batch
{

	private final Long id;
	private Integer index;
	private String userNumber;
	private Optional< Portfolio > portfolio = Optional.empty();
	private String name;
	private String set;
	private int section;
	private int sequence;
	private int generation;
	private int version;

	public BatchBase portfolio( final Portfolio portfolio )
	{
		this.portfolio = Optional.ofNullable( portfolio );

		return this;
	}

}
