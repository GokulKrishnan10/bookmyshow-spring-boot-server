package com.livedocs.server.webapp.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/security")
// TODO:
public class SecurityController {

    @GetMapping("/qrcode")
    public String getMethodName() {
        return new String();
    }

    @PostMapping("/qrcode")
    public String verifyTOTPFromQRCode(@RequestBody Object entity) {
        return new String();
    }

    @PostMapping("/qrcode")
    public String enableOrDisableMFA(@RequestParam boolean enable) {
        // TODO: process POST request

        return new String();
    }

}
