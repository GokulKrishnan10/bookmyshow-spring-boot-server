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

// MultiFactor Authentication for the App, and can be verified using Google Authenticator
// TODO:
@Service
public class MFAService {
    public String getSecretKey() {
        SecureRandom randomKey = new SecureRandom();
        byte[] keyBytes = new byte[4];
        randomKey.nextBytes(keyBytes);
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public String generateQRCodeImage(String secretKey, String data) throws QrGenerationException {
        QrData qrImage = new QrData.Builder().label(data).issuer("Spring Boot App").secret(secretKey)
                .algorithm(HashingAlgorithm.SHA1).digits(6).period(30).build();
        QrGenerator generator = new ZxingPngQrGenerator();
        byte[] imageData = generator.generate(qrImage);
        String pngStr = getDataUriForImage(imageData, "image/png");
        return pngStr;
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
