// ********RoostGPT********
/*
Test generated by RoostGPT for test java-customannotation-test using AI Type  and AI Model

ROOST_METHOD_HASH=write_f60797a75a
ROOST_METHOD_SIG_HASH=write_6f2f269543

Scenario 1: Write with a valid byte array

Details:
  TestName: writeWithValidByteArray
  Description: This test verifies that the write method handles a valid byte array correctly without throwing an exception.
Execution:
  Arrange: Create a non-empty byte array.
  Act: Call the write method with the created byte array.
  Assert: Verify that no exceptions are thrown and the byte array is written correctly to the output.
Validation:
  This assertion checks that the method can handle typical input correctly. It's significant because it confirms the method's ability to perform its primary function under normal conditions.

Scenario 2: Write with a null byte array

Details:
  TestName: writeWithNullByteArray
  Description: This test checks how the write method handles a null input, expecting it to throw a NullPointerException.
Execution:
  Arrange: Pass a null byte array to the write method.
  Act: Invoke the write method with the null byte array.
  Assert: Expect a NullPointerException to be thrown.
Validation:
  The test validates that the method throws an appropriate exception when given invalid input (null), which is crucial for avoiding undefined behaviors in the application.

Scenario 3: Write with an empty byte array

Details:
  TestName: writeWithEmptyByteArray
  Description: This test ensures that the write method can handle an empty byte array without errors and doesn't perform any operation.
Execution:
  Arrange: Create an empty byte array.
  Act: Call the write method with the empty byte array.
  Assert: Verify that no exceptions are thrown and no data is written.
Validation:
  This test checks the method's robustness with edge case inputs, ensuring that it gracefully handles cases where there's nothing to write, without causing errors.

Scenario 4: Write with maximum array length

Details:
  TestName: writeWithMaxArrayLength
  Description: This test checks the capability of the write method to handle a byte array of maximum possible length.
Execution:
  Arrange: Create a byte array filled with the maximum size allowed by the JVM.
  Act: Call the write method with this large byte array.
  Assert: Verify that no exceptions are thrown and the data is written correctly.
Validation:
  This test is important to assess the method's performance and behavior under stress conditions, ensuring it can handle large data sizes without failure.

Scenario 5: Write throws IOException

Details:
  TestName: writeThrowsIOException
  Description: This test ensures that the method properly propagates IOExceptions thrown by underlying output operations.
Execution:
  Arrange: Set up a scenario where the underlying OutputStream would throw an IOException (using mocks or a special test OutputStream).
  Act: Call the write method and expect it to propagate the IOException.
  Assert: Verify that an IOException is thrown.
Validation:
  The test confirms that IOExceptions are not swallowed or incorrectly handled, which is crucial for proper error handling and recovery in applications that use this method.
*/

// ********RoostGPT********

package net.markenwerk.utils.mail.smime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

public class MimeUtilWrite571Test {

	@Test
	@Tag("valid")
	public void writeWithValidByteArray() {
		byte[] validByteArray = new byte[] { 1, 2, 3, 4, 5 };
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MimeUtil mimeUtil = new MimeUtil(outputStream);
		Assertions.assertDoesNotThrow(() -> mimeUtil.write(validByteArray));
		Assertions.assertArrayEquals(validByteArray, outputStream.toByteArray());
	}

	@Test
	@Tag("invalid")
	public void writeWithNullByteArray() {
		MimeUtil mimeUtil = new MimeUtil(new ByteArrayOutputStream());

		Assertions.assertThrows(NullPointerException.class, () -> mimeUtil.write(null));
	}

	@Test
	@Tag("boundary")
	public void writeWithEmptyByteArray() {
		byte[] emptyByteArray = new byte[0];
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MimeUtil mimeUtil = new MimeUtil(outputStream);
		Assertions.assertDoesNotThrow(() -> mimeUtil.write(emptyByteArray));
		Assertions.assertEquals(0, outputStream.size());
	}

	@Test
	@Tag("boundary")
	public void writeWithMaxArrayLength() {
		byte[] maxByteArray = new byte[Integer.MAX_VALUE - 8]; // JVM array size limit
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		MimeUtil mimeUtil = new MimeUtil(outputStream);
		Assertions.assertDoesNotThrow(() -> mimeUtil.write(maxByteArray));
		Assertions.assertEquals(maxByteArray.length, outputStream.size());
	}

	@Test
	@Tag("integration")
	public void writeThrowsIOException() {
		OutputStream outputStream = Mockito.mock(OutputStream.class);
		MimeUtil mimeUtil = new MimeUtil(outputStream);
		byte[] data = new byte[] { 1, 2, 3 };
		try {
			Mockito.doThrow(IOException.class)
				.when(outputStream)
				.write(Mockito.any(byte[].class), Mockito.anyInt(), Mockito.anyInt());
		}
		catch (IOException e) {
			// No action needed, this is just for setting up the mock
		}
		Assertions.assertThrows(IOException.class, () -> mimeUtil.write(data));
	}

}