/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.process;

import org.cosmo.dcp.factory.DCPFactory;
import org.cosmo.dcp.internal.DCPConnection;
import org.cosmo.dcp.internal.Message;
import org.cosmo.dcp.internal.MessageBuilder;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class ClientTestMain {
    public static void main(String[] args){
        DCPFactory factory=new DCPFactory();
        DCPConnection connection = factory.getConnection(DCPConnection.Type.CLIENT);
        connection.connect("localhost", 5565,true);
        Message message=connection.recieve();
        System.out.println(new String(message.getBody()));
        connection.send(MessageBuilder.getInstance().setHeader("HeaderType:text".getBytes()).setBody("Hello".getBytes()).getMessage());
        connection.send(MessageBuilder.getInstance().
                setHeader("Content-Type:text/HTML\nencoding:UTF-8".getBytes()).
                setBody("<html></html>".getBytes()).getMessage());
        connection.close();
    }
}
