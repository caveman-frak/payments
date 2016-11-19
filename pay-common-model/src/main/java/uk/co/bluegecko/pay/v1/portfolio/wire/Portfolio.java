package uk.co.bluegecko.pay.v1.portfolio.wire;


import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@JsonDeserialize( builder = Portfolio.PortfolioBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public final class Portfolio
{

	@Min( 1 )
	private final Long id;
	@Length( max = 50 )
	private final String name;
	private final String serialNo;
	private final String userNumber;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class PortfolioBuilder
	{}

}
