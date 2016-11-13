package uk.co.bluegecko.pay.portfolio.v1.wire;


import javax.validation.constraints.Min;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;


@JsonDeserialize( builder = Portfolio.PortfolioBuilder.class )
@Data
@Builder
@Accessors( fluent = true )
public final class Portfolio
{

	@Min( 1 )
	private final Long id;
	private final String serialNo;
	private final String userNumber;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class PortfolioBuilder
	{}

}
