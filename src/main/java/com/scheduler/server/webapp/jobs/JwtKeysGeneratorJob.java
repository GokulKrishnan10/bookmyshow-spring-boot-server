package com.scheduler.server.webapp.jobs;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import org.springframework.stereotype.Component;

import com.scheduler.server.webapp.enums.JobType;
import com.scheduler.server.webapp.jobs.defns.ScheduledJob;
import com.scheduler.server.webapp.services.SysConfigService;

@Component
public class JwtKeysGeneratorJob extends ScheduledJob {

    private final SysConfigService service;

    public JwtKeysGeneratorJob(SysConfigService service) {
        this.service = service;
    }

    @Override
    public String executeJob() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        service.updateKeys(publicKeyString, privateKeyString);

        return "Success";
    }

    @Override
    public JobType getJobType() {
        return JobType.UPDATE_JWT_SIGNING_KEYS;
    }

}
