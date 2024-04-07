package com.livedocs.server.webapp.services;

import org.springframework.stereotype.Service;

// MultiFactor Authentication for the App, and can be verified using Google Authenticator
// TODO:
@Service
public class MFAService {
    public String getSecretKey() {
        return "";
    }

    public String generateQRCodeImage() {
        return "";
    }

    public String getClaimsForQRCodeImage() {
        return "";
    }

    public boolean verifyTOTP(String code) {
        return false;
    }
}
