package uk.co.bluegecko.pay.portfolio.model;


public interface Account
{

	public Long id();

	public String sortCode();

	public Account sortCode( String sortCode );

	public String number();

	public Account number( String number );

	public String name();

	public Account name( String name );

	public String type();

	public Account type( String type );

}