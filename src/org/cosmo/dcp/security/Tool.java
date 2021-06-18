/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public abstract class Tool {

    public static byte[] randomBytes(int size) {
        byte[] random = new byte[size];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(random);
        return random;
    }

    public static void setZero(byte[]... bytemems) {
        for (byte[] bytemem : bytemems) {
            for (int i = 0; i < bytemem.length; i++) {
                bytemem[i] = 0;
            }
        }
    }
    public static byte[] serialize(Object obj){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos=new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public static Object deserialize(byte[] stream){
        ByteArrayInputStream bais=new ByteArrayInputStream(stream);
        ObjectInputStream bis;
        try {
            bis = new ObjectInputStream(bais);
            return bis.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static byte[] encrypt(byte[] pt,Key key){
        try {
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipher.update(pt);
            return cipher.doFinal();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static byte[] decrypt(byte[] ct,Key key){
        try {
            Cipher cipher=Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            cipher.update(ct);
            return cipher.doFinal();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
