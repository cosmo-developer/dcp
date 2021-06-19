/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.process;

import java.util.Arrays;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class MaskTest {

    public static void main(String[] args) {
        int len = 25695;
        byte[] buffer = new byte[4];
        buffer[0] = (byte) ((byte) (len >> 24)&0xFF);
        buffer[1] = (byte) ((byte) (len  >> 16)&0xFF);
        buffer[2] = (byte) ((byte) ((len) >> 8)&0xFF);
        buffer[3] = (byte) ((byte) (len)&0xFF);
        final int I_MASK=0x000000FF;
        System.out.println(Arrays.toString(buffer));
        int recovered=((int)buffer[0]<<24)|((int)buffer[1]<<16)|((int)buffer[2]<<8)|((int)buffer[3]&0x000000FF);
        System.out.println(recovered);
    }
}
