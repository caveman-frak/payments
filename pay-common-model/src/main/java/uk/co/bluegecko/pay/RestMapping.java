package uk.co.bluegecko.pay;


import uk.co.bluegecko.pay.v1.portfolio.rest.BatchMapping;
import uk.co.bluegecko.pay.v1.upload.rest.UploadMapping;


public interface RestMapping
{

	public interface View
	{

		public static final String PARAM = "view";

		public static final String STANDARD = "uk.co.bluegecko.pay.view.View.Standard";
		public static final String DETAILED = "uk.co.bluegecko.pay.view.View.Detailed";
		public static final String SUMMARY = "uk.co.bluegecko.pay.view.View.Summary";
		public static final String AUDITED = "uk.co.bluegecko.pay.view.View.Audited";
		public static final String HIDDEN = "uk.co.bluegecko.pay.view.View.Hidden";

	}

	public interface v1
	{

		public static final String VERSION = "v1";

	}

	public interface Portfolio
	{

		public interface v1 extends RestMapping.v1
		{

			public interface Batch extends BatchMapping
			{}

		}

	}

	public interface Upload
	{

		public interface v1 extends RestMapping.v1
		{

			public interface Uplaod extends UploadMapping
			{}

		}

	}

}
