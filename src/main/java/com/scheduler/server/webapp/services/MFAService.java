package com.scheduler.server.webapp.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.stereotype.Service;

import dev.samstevens.totp.code.CodeGenerator;
import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.QrGenerator;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import dev.samstevens.totp.time.TimeProvider;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
public class MFAService {

    private static final int KEY_BYTES_LENGTH = 4;
    private static final int QR_CODE_DIGITS = 6;
    private static final int QR_CODE_PERIOD = 30;
    private static final String QR_CODE_ISSUER = "Spring Boot App";
    private static final String QR_CODE_IMAGE_TYPE = "image/png";

    public String getSecretKey() {
        SecureRandom randomKey = new SecureRandom();
        byte[] keyBytes = new byte[KEY_BYTES_LENGTH];
        randomKey.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public String generateQRCodeImage(String secretKey, String data) throws QrGenerationException {
        QrData qrImage = new QrData.Builder()
                .label(data)
                .issuer(QR_CODE_ISSUER)
                .secret(secretKey)
                .algorithm(HashingAlgorithm.SHA1)
                .digits(QR_CODE_DIGITS)
                .period(QR_CODE_PERIOD)
                .build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(qrImage);
        return getDataUriForImage(imageData, QR_CODE_IMAGE_TYPE);
    }

    public String getClaimsForQRCodeImage() {
        return "";
    }

    public boolean verifyTOTP(String code, String secret) {
        TimeProvider timeProvider = new SystemTimeProvider();
        CodeGenerator codeGenerator = new DefaultCodeGenerator();
        CodeVerifier verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);
        return verifier.isValidCode(secret, code);
    }
}
