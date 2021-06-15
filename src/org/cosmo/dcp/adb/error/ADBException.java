/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.adb.error;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class ADBException extends Exception{
    public ADBException(String msg){
        super(msg);
    }
}
