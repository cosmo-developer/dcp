/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.process;

import org.cosmo.dcp.adb.error.ADBException;
import org.cosmo.dcp.factory.DCPFactory;
import org.cosmo.dcp.internal.DCPConnection;
import org.cosmo.dcp.internal.Message;
import org.cosmo.dcp.internal.MessageBuilder;
/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class ProcessTestMain {
    public static void main(String[] args) throws ADBException {
//        System.out.println(DCPAdbConnection.getDaemonStatus());
        MessageBuilder mBuilder=MessageBuilder.getInstance();
        Message message = mBuilder.setBody("Aesop".getBytes()).setHeader("Type/Text".getBytes()).getMessage();
        DCPFactory dcpFactory=new DCPFactory();
        DCPConnection connection = dcpFactory.getConnection(DCPConnection.Type.SERVER);
        connection.connect("localhost", 5565,true);
        connection.send(message);
        Message recieve = connection.recieve();
        Message newmsg=connection.recieve();
        System.out.println(new String(recieve.getBody()));
        System.out.println(new String(newmsg.getBody()));
        connection.close();
    }
}
