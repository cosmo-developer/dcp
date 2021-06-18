/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.security;

import java.util.Optional;

/**
 *
 * @author Dell
 */
public class AEAD {

    public static enum ID {
        AES_128_GCM(0x0001),
        AES_256_GCM(0x0002),
        CHACHA20_POLY1305(0x0003);
        public final int id;

        private ID(int d) {
            id = d;
        }
    }

    public static AEAD get(ID id) {
        return AEADCipher.get(id);
    }
    public final ID id;
    public final int key_size;
    public final int nonce_size;

    public byte[] seal(byte[] key, byte[] nonce, byte[] aad, byte[] pt) {
        return null;
    }

    public Optional<byte[]> open(byte[] key, byte[] nonce, byte[] aad, byte[] ct) {
        return null;
    }

    protected AEAD(ID id_in, int key_size_in, int nonce_size_in) {
        this.id = id_in;
        this.key_size = key_size_in;
        this.nonce_size = nonce_size_in;
    }

    public static int cipherKeySize(ID cipher) {
        switch (cipher) {
            case AES_128_GCM:
                return 16;
            case AES_256_GCM:
            case CHACHA20_POLY1305:
                return 32;
            default:
                throw new RuntimeException("Unsupported algorithm:" + cipher.name());
        }
    }

    public static int cipherTagSize(ID cipher) {
        switch (cipher) {
            case AES_128_GCM:
            case AES_256_GCM:
            case CHACHA20_POLY1305:
                return 16;
            default:
                throw new RuntimeException("Unsupported algorithm:" + cipher.name());
        }
    }

    public static int cipherNonceSize(ID cipher) {
        switch (cipher) {
            case AES_128_GCM:
            case AES_256_GCM:
            case CHACHA20_POLY1305:
                return 12;
            default:
                throw new RuntimeException("Unsupported algorithm");
        }
    }
}
