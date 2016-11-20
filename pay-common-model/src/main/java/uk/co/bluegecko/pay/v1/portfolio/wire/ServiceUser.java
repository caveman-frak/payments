package uk.co.bluegecko.pay.v1.portfolio.wire;


import java.util.Set;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.AuddisCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.CreditCode;
import uk.co.bluegecko.pay.bacs.std18.model.TransactionCode.DebitCode;


@JsonDeserialize( builder = ServiceUser.ServiceUserBuilder.class )
@Value
@Builder
@Accessors( fluent = true )
public class ServiceUser implements UserNumber
{

	@Pattern( regexp = "\\d{6}" )
	private final String userNumber;
	private final boolean indirect;
	@Singular
	private final Set< CreditCode > creditCodes;
	@Singular
	private final Set< DebitCode > debitCodes;
	@Singular
	private final Set< AuddisCode > auddisCodes;
	@Singular
	private final Set< CreditCode > fpsCodes;

	@JsonPOJOBuilder( withPrefix = "" )
	public static final class ServiceUserBuilder
	{}

}
