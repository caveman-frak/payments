package uk.co.bluegecko.pay.view;


public class View
{

	public interface Standard
	{}

	public interface Summary extends Standard
	{}

	public interface Detailed extends Standard
	{}

	public interface Audited extends Detailed
	{}

	public interface Hidden extends Audited
	{}

}
