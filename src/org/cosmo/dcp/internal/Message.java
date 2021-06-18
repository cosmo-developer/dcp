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
public class Message {
    private final byte[] header;
    private final int headerSize;
    private final byte[] body;
    private final int bodySize;

    public Message(byte[] header, int headerSize, byte[] body, int bodySize) {
        this.header = header;
        this.headerSize = headerSize;
        this.body = body;
        this.bodySize = bodySize;
    }
    

    public byte[] getHeader() {
        return header;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public byte[] getBody() {
        return body;
    }

    public int getBodySize() {
        return bodySize;
    }
}
