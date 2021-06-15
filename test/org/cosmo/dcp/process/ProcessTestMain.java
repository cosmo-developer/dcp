/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.process;

import org.cosmo.dcp.adb.DCPAdbConnection;
import org.cosmo.dcp.adb.error.ADBException;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class ProcessTestMain {
    public static void main(String[] args) throws ADBException {
        DCPAdbConnection.restartAdb();
        System.out.println(DCPAdbConnection.getDaemonStatus());
        DCPAdbConnection.stopAdb();
        System.out.println(DCPAdbConnection.getDaemonStatus());
    }
}
