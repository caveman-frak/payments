package uk.co.bluegecko.pay.portfolio.model;


import java.math.BigDecimal;
import java.time.LocalDate;


public interface Instruction
{

	public Long id();

	public Batch batch();

	public int index();

	public Instruction index( int index );

	public Integer lineNo();

	public Account origin();

	public Instruction origin( Account origin );

	public Account destination();

	public Instruction destination( Account destination );

	public String transactionType();

	public Instruction transactionType( String transactionType );

	public String rti();

	public Instruction rti( String rti );

	public BigDecimal amount();

	public Instruction amount( BigDecimal amount );

	public String reference();

	public Instruction reference( String reference );

	public LocalDate processingDate();

	public Instruction processingDate( LocalDate processingDate );

}