package uk.co.bluegecko.pay.portfolio.model;


import java.util.Optional;


public interface Batch
{

	public Long id();

	public Integer index();

	public Optional< Portfolio > portfolio();

	public String userNumber();

	public String name();

	public String set();

	public int section();

	public int sequence();

	public int generation();

	public int version();

}
