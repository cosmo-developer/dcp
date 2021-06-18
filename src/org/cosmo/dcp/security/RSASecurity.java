/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.security;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class RSASecurity {
    KeyPair keyPair;
    public RSASecurity(){
        try {
            KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
            kpg.initialize(1024, new SecureRandom());
            keyPair=kpg.genKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSASecurity.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public PrivateKey getPrivateKey(){
        return keyPair.getPrivate();
    }
    public PublicKey getPublicKey(){
        return keyPair.getPublic();
    }
}
