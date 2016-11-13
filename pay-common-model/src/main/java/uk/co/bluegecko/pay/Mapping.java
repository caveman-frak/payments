package uk.co.bluegecko.pay;


import uk.co.bluegecko.pay.portfolio.v1.rest.BatchMapping;
import uk.co.bluegecko.pay.upload.v1.rest.UploadMapping;


public interface Mapping
{

	public interface v1
	{

		public static final String VERSION = "v1";
	}

	public interface Portfolio
	{

		public interface v1 extends Mapping.v1
		{

			public interface Batch extends BatchMapping
			{}

		}

	}

	public interface Upload
	{

		public interface v1 extends Mapping.v1
		{

			public interface Uplaod extends UploadMapping
			{}

		}

	}

}
