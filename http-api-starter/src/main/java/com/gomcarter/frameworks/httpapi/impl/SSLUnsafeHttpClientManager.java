package com.gomcarter.frameworks.httpapi.impl;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.Socket;

/**
 * @author gomcarter
 */
public class SSLUnsafeHttpClientManager extends DefaultHttpClientManager {

    @Override
    protected Registry<ConnectionSocketFactory> getSocketFactoryRegistry() {
        SSLContext sslContext;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        SSLConnectionSocketFactory sslConnectionSocketFactory = new SniSSLSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

        return RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", sslConnectionSocketFactory)
                .build();
    }

    /**
     * fix ssl handshake error, see
     * http://stackoverflow.com/questions/7615645/ssl-handshake-alert-unrecognized-name-error-since-upgrade-to-java-1-7-0
     */
    static class SniSSLSocketFactory extends SSLConnectionSocketFactory {

        public SniSSLSocketFactory(final SSLContext sslContext, final HostnameVerifier verifier) {
            super(sslContext, verifier);
        }

        @Override
        public Socket createLayeredSocket(
                final Socket socket,
                final String target,
                final int port,
                final HttpContext context) throws IOException {
            return super.createLayeredSocket(socket, "", port, context);
        }
    }
}
