package uk.co.bluegecko.pay.portfolio.v1.wire;


import java.util.Set;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.portfolio.v1.wire.TransactionCode.AuddisCode;
import uk.co.bluegecko.pay.portfolio.v1.wire.TransactionCode.CreditCode;
import uk.co.bluegecko.pay.portfolio.v1.wire.TransactionCode.DebitCode;


@JsonDeserialize( builder = ServiceUser.ServiceUserBuilder.class )
@Data
@Builder
@Accessors( fluent = true )
public class ServiceUser implements UserNumber
{

	@Pattern( regexp = "\\d{6}" )
	private final String userNumber;

	private final boolean indirect;

	private final Set< CreditCode > creditCodes;
	private final Set< DebitCode > debitCodes;
	private final Set< AuddisCode > auddisCodes;
	private final Set< CreditCode > fpsCodes;

}
