/*
 * Copyright (c) 2015 Torsten Krause, Markenwerk GmbH.
 * 
 * This file is part of 'A S/MIME library for JavaMail', hereafter
 * called 'this library', identified by the following coordinates:
 * 
 *    groupID: net.markenwerk
 *    artifactId: utils-mail-smime
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 * 
 * See the LICENSE and NOTICE files in the root directory for further
 * information.
 */
package net.markenwerk.utils.mail.smime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.mockito.Mockito;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import org.junit.jupiter.api.*;

public class MimeUtilCanonicalizeTest {
    @Test
    @Tag("valid")
    public void canonicalizeValidMimeMessage() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties());
        MimeMessage original = new MimeMessage(session);
        original.setText("Hello, World!");
        
        MimeMessage result = MimeUtil.canonicalize(session, original);
        
        ByteArrayOutputStream originalStream = new ByteArrayOutputStream();
        ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
        original.writeTo(originalStream);
        result.writeTo(resultStream);
        
        assertNotNull(result);
        assertEquals(new String(originalStream.toByteArray()), new String(resultStream.toByteArray()));
    }
    @Test
    @Tag("integration")
    public void canonicalizeMimeMessageWithAttachments() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties());
        MimeMessage original = new MimeMessage(session);
        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setText("This is text");
        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setContent("This is an attachment", "text/plain");
        original.setContent(new MimeBodyPart[]{textPart, attachmentPart});
        
        MimeMessage result = MimeUtil.canonicalize(session, original);
        
        assertNotNull(result);
        assertEquals(original.getCount(), result.getCount());
    }
    @Test
    @Tag("invalid")
    public void canonicalizeWithIOException() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties());
        MimeMessage original = mock(MimeMessage.class);
        doThrow(new IOException()).when(original).writeTo(Mockito.any(OutputStream.class));
        
        try {
            MimeUtil.canonicalize(session, original);
        } catch (IOException e) {
            assertNotNull(e);
        }
    }
    @Test
    @Tag("invalid")
    public void canonicalizeWithMessagingException() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties());
        MimeMessage original = mock(MimeMessage.class);
        doThrow(new MessagingException()).when(original).writeTo(Mockito.any(OutputStream.class));
        
        try {
            MimeUtil.canonicalize(session, original);
        } catch (MessagingException e) {
            assertNotNull(e);
        }
    }
    @Test
    @Tag("boundary")
    public void canonicalizeEmptyMimeMessage() throws MessagingException, IOException {
        Session session = Session.getInstance(System.getProperties());
        MimeMessage original = new MimeMessage(session);
        
        MimeMessage result = MimeUtil.canonicalize(session, original);
        
        assertNotNull(result);
        assertEquals(0, result.getSize());
    }
}