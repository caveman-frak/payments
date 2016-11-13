package uk.co.bluegecko.pay.portfolio.model.base;


import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;


@Data
@Accessors( fluent = true )
public class PortfolioBase implements Portfolio
{

	private final Long id;

}
