package uk.co.bluegecko.pay.portfolio.v1.wire;


import java.util.Set;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import uk.co.bluegecko.pay.view.View;


@JsonDeserialize( builder = BureauUser.BureauUserBuilder.class )
@Data
@Builder
@Accessors( fluent = true )
public class BureauUser implements UserNumber
{

	@Pattern( regexp = "B\\d{5}" )
	private final String userNumber;
	@JsonView( View.Detailed.class )
	private Set< ServiceUser > serviceUsers;

}
