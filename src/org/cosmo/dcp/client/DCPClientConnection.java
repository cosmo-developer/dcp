/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cosmo.dcp.internal.DCPConnection;
import org.cosmo.dcp.internal.Message;
import org.cosmo.dcp.internal.MessageBuilder;
import org.cosmo.dcp.security.RSASecurity;
import org.cosmo.dcp.security.Tool;
import org.cosmo.dcp.server.DCPServerConnection;

public class DCPClientConnection implements DCPConnection {

    Socket socket;
    BufferedOutputStream bos;
    BufferedInputStream bis;
    boolean secured;
    RSASecurity security;
    PublicKey key;

    @Override
    public void connect(String ip, int port, boolean secure) {
        try {
            socket = new Socket(ip, port);
            bos = new BufferedOutputStream(socket.getOutputStream());
            bis = new BufferedInputStream(socket.getInputStream());
            if (secure) {
                security = new RSASecurity();
                Message recieve = recieve();
                if (new String(recieve.getHeader()).equals("Key/PublicKey")) {
                    key = (PublicKey) Tool.deserialize(recieve.getBody());
                }
                Message message = MessageBuilder.getInstance()
                        .setHeader("Key/PublicKey".getBytes())
                        .setBody(Tool.serialize(security.getPublicKey()))
                        .getMessage();
                send(message);
            }
        } catch (IOException ex) {
            Logger.getLogger(DCPClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.secured = secure;
    }

    @Override
    public void send(Message message) {
        try {
            if (secured) {
                message = MessageBuilder.getInstance().setHeader(message.getHeader()).setBody(Tool.encrypt(message.getBody(), key)).getMessage();
            }
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.putInt(message.getHeaderSize());
            bos.write(buffer.array());
            bos.write(message.getHeader());
            buffer.clear();
            buffer.putInt(message.getBodySize());
            bos.write(buffer.array());
            bos.write(message.getBody());
            buffer.clear();
            bos.flush();
        } catch (IOException ex) {
            Logger.getLogger(DCPClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Message recieve() {
        try {
            byte[] size = new byte[4];
            bis.read(size);
            ByteBuffer buffer = ByteBuffer.allocate(4);
            buffer.put(size);
            buffer.flip();
            int headerSize = buffer.asIntBuffer().get();
            byte[] header = new byte[headerSize];
            bis.read(header);
            buffer.clear();
            bis.read(size);
            buffer.put(size);
            buffer.flip();
            int bodySize = buffer.asIntBuffer().get();
            byte[] body = new byte[bodySize];
            bis.read(body);
            Message message = MessageBuilder.getInstance().setHeader(header).setBody(body).getMessage();
            if (secured) {
                message = MessageBuilder.getInstance().setHeader(message.getHeader())
                        .setBody(Tool.decrypt(message.getBody(), security.getPrivateKey())).getMessage();
            }
            return message;
        } catch (IOException ex) {
            Logger.getLogger(DCPServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public void close() {
        try {
            bos.close();
            bis.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(DCPClientConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
