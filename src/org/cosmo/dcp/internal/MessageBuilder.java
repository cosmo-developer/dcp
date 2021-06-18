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
public class MessageBuilder {
    private int headerSize;
    private byte[] header;
    private int bodySize;
    private byte[] body;
    private static final MessageBuilder builder=new MessageBuilder();
    public synchronized Message getMessage(){
        return new Message(header,headerSize,body,bodySize);
    }
    private MessageBuilder(){
        
    }
    public static MessageBuilder getInstance(){
        return builder;
    }

    public synchronized MessageBuilder setHeader(byte[] header) {
        this.header = header;
        this.headerSize=header.length;
        return this;
    }

    public synchronized MessageBuilder setBody(byte[] body) {
        this.body = body;
        this.bodySize=body.length;
        return this;
    }
}
