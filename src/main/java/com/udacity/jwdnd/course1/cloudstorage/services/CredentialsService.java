package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.mapper.CredentialMapper;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialsService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public int insertCredential(Credential credential, int userId) {
        credential.setUserid(userId);
        Credential updateCredential = enCryptPasswordForCredential(credential);
        return credentialMapper.insert(updateCredential);
    }

    public int updateCredential(Credential credential) {
        Credential updateCredential = enCryptPasswordForCredential(credential);
        return credentialMapper.update(updateCredential);
    }

    public List<Credential> getCredentialsByUserid(int userId) {
        List<Credential> credentials = credentialMapper.getCredentials(userId);
        credentials.forEach(credential ->
                System.out.println("enCryptPasswordBeforeDecrypt: " + credential.getPassword())
        );
        return credentials;
    }

    public int deleteCredential(int credentialId) {
        return credentialMapper.delete(credentialId);
    }

    public Credential getCredentialById(int credentialId) {
        return credentialMapper.getCredentialById(credentialId);
    }

    private Credential enCryptPasswordForCredential(Credential credential) {
        String enCryptPassword;
        if (credential.getKey() != null) {
            enCryptPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        } else {
            SecureRandom random = new SecureRandom();
            byte[] key = new byte[16];
            random.nextBytes(key);
            String encodedKey = Base64.getEncoder().encodeToString(key);
            enCryptPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
            credential.setKey(encodedKey);
            System.out.println("enCryptPassword: " + enCryptPassword);
        }
        credential.setPassword(enCryptPassword);
        return credential;
    }
}
