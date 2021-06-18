/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.internal;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public interface DCPConnection {
    public static enum Type{
        SERVER,CLIENT
    }
    public void connect(String ip,int port,boolean secure);
    public void send(Message message);
    public Message recieve();
    public void close();
}
