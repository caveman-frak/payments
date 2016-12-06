package uk.co.bluegecko.pay.upload.service.base;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import uk.co.bluegecko.pay.bacs.std18.model.Instruction;
import uk.co.bluegecko.pay.common.service.base.ParsingServiceBase;
import uk.co.bluegecko.pay.upload.service.StreamingService;
import uk.co.bluegecko.pay.upload.service.UploadService;


public class UploadServiceBaseTest
{

	private static final String FILE_NAME = "test.txt";
	private static final String VOL_LINE = "VOL1173922                               100101                                1                          ";
	private static final String INST_LINE = "0100390105996309940202421315692000000000000006BSDSAF 00000000006REF&LT 00000000006NAME   00000000006 14308     ";

	private StreamingService streamingService;
	private UploadService uploadService;

	@Before
	public void setUp() throws Exception
	{
		streamingService = mock( StreamingService.class );
		uploadService = new UploadServiceBase( new ParsingServiceBase(), streamingService );
	}

	@Test
	public final void testUploadWithInstruction() throws IOException
	{
		final MultipartFile file = new MockMultipartFile( FILE_NAME, INST_LINE.getBytes() );

		uploadService.processFile( file );

		verify( streamingService, only() ).sendInstruction( any( Instruction.class ) );
	}

	@Test
	public final void testUploadWithoutInstruction() throws IOException
	{
		final MultipartFile file = new MockMultipartFile( FILE_NAME, VOL_LINE.getBytes() );

		uploadService.processFile( file );

		verify( streamingService, never() ).sendInstruction( any( Instruction.class ) );
	}

	@Test
	public final void testJobStatus()
	{
		assertThat( uploadService.getJobStatus( 1 ), is( "COMPLETED: 1" ) );
	}

}
