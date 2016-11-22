package uk.co.bluegecko.pay.portfolio.model.base;


import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.model.Portfolio;


@Data
@Accessors( fluent = true )
public final class PortfolioBase implements Portfolio
{

	private final Long id;
	@Length( max = 50 )
	private String name;
	private String serialNo;
	private String userNumber;

}
