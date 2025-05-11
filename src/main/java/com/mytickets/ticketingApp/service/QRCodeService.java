package com.mytickets.ticketingApp.service;

import com.google.zxing.WriterException;
import java.awt.image.BufferedImage;
import java.io.IOException;

public interface QRCodeService {
    String generateQRCodeAsBase64(String content, int width, int height);

    BufferedImage generateQRCodeImage(String content, int width, int height) throws WriterException;

    String readQRCode(BufferedImage image);

    byte[] generateQRCodeBytes(String content, int width, int height);
}