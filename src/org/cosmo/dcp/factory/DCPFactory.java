/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cosmo.dcp.factory;

import org.cosmo.dcp.internal.DCPConnection;
import org.cosmo.dcp.client.DCPClientConnection;
import org.cosmo.dcp.server.DCPServerConnection;

/**
 *
 * @author Sonu Aryan <cosmo-developer@github.com>
 */
public class DCPFactory {
    public DCPConnection getConnection(DCPConnection.Type type){
        switch(type){
            case SERVER:
                return new DCPServerConnection();
            case CLIENT:
                return new DCPClientConnection();
        }
        return null;
    }
}
