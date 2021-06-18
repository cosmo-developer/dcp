/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.process;

import java.util.Optional;
import org.cosmo.dcp.security.AEAD;
import org.cosmo.dcp.security.RSASecurity;
import org.cosmo.dcp.security.Tool;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class SecurityTestMain {
    public static void main(String[] args){
        AEAD aead=AEAD.get(AEAD.ID.AES_256_GCM);
        byte[] key=Tool.randomBytes(aead.key_size);
        byte[] nonce=Tool.randomBytes(aead.nonce_size);
        byte[] seal = aead.seal(key, nonce, key, "Aesop".getBytes());
        System.out.println(seal.length);
        System.out.println(new String(seal));
        Optional<byte[]> open = aead.open(key, nonce, key, seal);
        Tool.setZero(key,nonce);
        System.out.println(new String(open.get()));
        RSASecurity security=new RSASecurity();
        byte[] encrypt = Tool.encrypt("laksjdlfkjasdl".getBytes(),security.getPublicKey());
        byte[] decrypt=Tool.decrypt(encrypt, security.getPrivateKey());
        System.out.println(new String(decrypt));
        
    }
}
