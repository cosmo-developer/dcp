/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.security;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Dell
 */
public class AEADCipher extends AEAD {

    private final int tag_size;

    private static AEADCipher instance(AEAD.ID cipher_in) {
        return new AEADCipher(cipher_in, AEAD.cipherKeySize(cipher_in), AEAD.cipherNonceSize(cipher_in));
    }

    public AEADCipher(ID id_in, int key_size_in, int nonce_size_in) {
        super(id_in, key_size_in, nonce_size_in);
        this.tag_size = AEAD.cipherTagSize(id_in);
    }

    public static AEADCipher get(AEAD.ID cipher_in) {
        return instance(cipher_in);
    }

    @Override
    public byte[] seal(byte[] key, byte[] nonce, byte[] aad, byte[] pt) {
        String breakDown = id.name();
        String[] params = breakDown.split("_");
        if (params.length == 3) {
            GCMParameterSpec spec = new GCMParameterSpec(tag_size * 8, nonce);
            try {
                Cipher cipher = Cipher.getInstance(params[0] + "/" + params[2] + "/NoPadding");
                SecretKey sk = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.ENCRYPT_MODE, sk, spec);
                cipher.updateAAD(aad);
                return cipher.doFinal(pt);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
                Logger.getLogger(AEADCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Cipher cipher = Cipher.getInstance("CHACHA20-POLY1305");
                IvParameterSpec iv = new IvParameterSpec(nonce);
                SecretKey sk = new SecretKeySpec(key, "CHACHA20-POLY1305");
                cipher.init(Cipher.ENCRYPT_MODE, sk, iv);
                return cipher.doFinal(pt);
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(AEADCipher.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

    @Override
    public Optional<byte[]> open(byte[] key, byte[] nonce, byte[] aad, byte[] ct) {
        String breakDown = id.name();
        String[] params = breakDown.split("_");
        if (params.length == 3) {
            GCMParameterSpec spec = new GCMParameterSpec(tag_size * 8, nonce);
            try {
                Cipher cipher = Cipher.getInstance(params[0] + "/" + params[2] + "/NoPadding");
                SecretKey sk = new SecretKeySpec(key, "AES");
                cipher.init(Cipher.DECRYPT_MODE, sk, spec);
                cipher.updateAAD(aad);
                return Optional.ofNullable(cipher.doFinal(ct));
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException ex) {
                Logger.getLogger(AEADCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                Cipher cipher = Cipher.getInstance("CHACHA20-POLY1305");
                SecretKey sk = new SecretKeySpec(key, "CHACHA20-POLY1305");
                IvParameterSpec iv = new IvParameterSpec(nonce);
                cipher.init(Cipher.DECRYPT_MODE, sk, iv);
                return Optional.ofNullable(cipher.doFinal(ct));
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException ex) {
                Logger.getLogger(AEADCipher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

}
