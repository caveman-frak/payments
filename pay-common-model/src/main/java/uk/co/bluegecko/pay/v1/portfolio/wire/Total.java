package uk.co.bluegecko.pay.v1.portfolio.wire;


import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Value;
import lombok.experimental.Accessors;


@JsonDeserialize( builder = Total.TotalBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public class Total
{

	public enum Type
	{
		DEBIT, CREDIT, CONTRA, AUDDIS, WARNING, ERROR
	}

	@NotNull
	private final Type type;
	@Min( 0 )
	private final int count;
	@NotNull
	@Digits( fraction = 2, integer = 19 )
	@Min( 0 )
	private final BigDecimal amount;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class TotalBuilder
	{}

}
