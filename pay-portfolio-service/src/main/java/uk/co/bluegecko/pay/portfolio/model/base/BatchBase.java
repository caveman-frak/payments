package uk.co.bluegecko.pay.portfolio.model.base;


import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Batch;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;


@Data
@Accessors( fluent = true )
public class BatchBase implements Batch
{

	private final Long id;
	private int index;
	private Portfolio portfolio;

}
