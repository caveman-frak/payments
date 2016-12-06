package uk.co.bluegecko.pay.test.data;


import java.math.BigDecimal;


public interface TestConstants
{

	public static final long ACCT_ID = 101L;
	public static final String SORT_CODE = "123456";
	public static final String ACCT_NO = "12345678";
	public static final String ACCT_NAME = "TEST AC1";
	public static final String ACCT_TYPE = "A";

	public static final long DEST_ACCT_ID = 102L;
	public static final String DEST_SORT_CODE = "654321";
	public static final String DEST_ACCT_NO = "87654321";
	public static final String DEST_ACCT_NAME = "TEST AC2";
	public static final String DEST_ACCT_TYPE = "1";

	public static final String SUN = "123456";
	public static final String SUN_2 = "456789";
	public static final String BUN = "B12345";
	public static final String SERIAL_NO = "321";
	public static final long PORTFOLIO_ID = 102L;
	public static final String PORTFOLIO_NAME = "PORTFOLIO #1";

	public static final long BATCH_ID = 104L;
	public static final String BATCH_NAME = "Batch #1";
	public static final int BATCH_IDX = 1;
	public static final String SET = "001";
	public static final int GENERATION = 1;
	public static final int SECTION = 2;
	public static final int SEQUENCE = 3;
	public static final int VERSION = 4;
	public static final String RTI = "/001";
	public static final String FILE = "12345";

	public static final long INSTRUCTION_ID = 105L;
	public static final int LINE_NO = 2;
	public static final int INSTRUCTION_IDX = 1;
	public static final String TRANSACTION_TYPE = "99";
	public static final BigDecimal AMOUNT = new BigDecimal( "10.01" );
	public static final String PENCE = "1001";
	public static final String REFERENCE = "A-REFERENCE";

	public static final String OFFSET = "0";
	public static final String RECORD = "00106";
	public static final String BLOCK = "0001";
	public static final String FORMAT = "F";
	public static final String SYSTEM_CODE = "CODE";
	public static final String BLOCK_COUNT = "00106";
	public static final String ACCESSIBILITY = "Y";

	public static final String DEST = "DEST";
	public static final String CCY = "GBP";
	public static final String COUNTRY = "GB";
	public static final String WORK_CODE = "1 DAILY";
	public static final String AUDIT = "01";

	public static final String LABEL = "L";

}
