/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.adb;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class Device {
    final String deviceProduct;
    public Device(String deviceProduct){
        this.deviceProduct=deviceProduct;
    }
    public String getDeviceProduct(){
        return this.deviceProduct;
    }
}
