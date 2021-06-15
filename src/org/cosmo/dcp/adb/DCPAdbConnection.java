/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.adb;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cosmo.dcp.adb.error.ADBException;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public abstract class DCPAdbConnection {
    final List<Device> connectedDeviceList=new ArrayList<>();
    static final Runtime runtime=Runtime.getRuntime();
    public static enum DaemonStatus{
        RUNNING,STOPPED
    }
    static byte[] elog=new byte[2048];
    static byte[] ilog=new byte[2048];
    private static void empty(){
        for (int i=0;i<2048;i++){
            elog[i]=0;
            ilog[i]=0;
        }
    }
    private static int exec(String command){
        Process exec;
        try {
            exec = runtime.exec(command);
            InputStream errorStream = exec.getErrorStream();
            errorStream.read(elog);
            InputStream inputStream = exec.getInputStream();
            inputStream.read(ilog);
            exec.waitFor();
            return exec.exitValue();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(DCPAdbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0xFFFFFFF;   
    }
    
    public static void startAdb() throws ADBException{
        exec("adb start-server");
        if (!new String(elog).contains("daemon started successfully")){
            throw new ADBException("Already connected to ADB daemon");
        }
        empty();
    }
    
    public static void stopAdb() throws ADBException{
        exec("adb kill-server");
        if (new String(elog).trim().contains("cannot connect to daemon")){
           throw new ADBException("Can't connect to ADB Daemon");
        }
        empty();
    }
    public static DaemonStatus getDaemonStatus(){
        try {
            startAdb();
        } catch (ADBException ex) {
            return DaemonStatus.RUNNING;
        }
        try {
            stopAdb();
        } catch (ADBException ex) {
        }
        return DaemonStatus.STOPPED;
    }
    public static void restartAdb(){
        if (getDaemonStatus()==DaemonStatus.RUNNING){
            try {
                stopAdb();
                startAdb();
            } catch (ADBException ex) {
            }
        }else{
            try {
                startAdb();
            } catch (ADBException ex) {
            }
        }
    }
    
}
