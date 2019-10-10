package com.github.imanx.demo;

import java.math.BigInteger;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by ImanX.
 * zarinplus-application | Copyrights 2019 ZarinPal Crop.
 */
public class SSLPinningManager implements X509TrustManager {


    private String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv/uEc8oRd0u/euILsE+T\n" +
            "XeJkrLL2sI4aVhC/b5rc/W2Tb4q/RNrKF0GXiSuxOrqx+j9gjlx5qcvxdztJmsER\n" +
            "BugPt0OaTkHzHooqUZg0dlfgWdOAQ6L57eOYtCoU9FxjdWw6TUTIUD8GBlFScIZ4\n" +
            "oXENHso+2GuSMi6fMpMS7aHnw1m0qZGoEa3XSrdBzxg6rXdqCeP9B1ywVdraDbS7\n" +
            "5hd3uGDL58pk39QjUAPhzqvdh+RVVF4bBioBm5iy9YMmaYXl2m0Lfsy6nQ4QtQst\n" +
            "GMUj3pbmvQfk2A6ycxdExZddVlvl2o620l2YqpqCdKBE8Bk/tzQHU9JqNzpzh9BT\n" +
            "MwIDAQABMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv/uEc8oRd0u/euILsE+T\n" +
            "XeJkrLL2sI4aVhC/b5rc/W2Tb4q/RNrKF0GXiSuxOrqx+j9gjlx5qcvxdztJmsER\n" +
            "BugPt0OaTkHzHooqUZg0dlfgWdOAQ6L57eOYtCoU9FxjdWw6TUTIUD8GBlFScIZ4\n" +
            "oXENHso+2GuSMi6fMpMS7aHnw1m0qZGoEa3XSrdBzxg6rXdqCeP9B1ywVdraDbS7\n" +
            "5hd3uGDL58pk39QjUAPhzqvdh+RVVF4bBioBm5iy9YMmaYXl2m0Lfsy6nQ4QtQst\n" +
            "GMUj3pbmvQfk2A6ycxdExZddVlvl2o620l2YqpqCdKBE8Bk/tzQHU9JqNzpzh9BT\n" +
            "MwIDAQAB";


    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        if (chain == null) throw new IllegalArgumentException("chain is null");
        TrustManagerFactory tmf;
        try {
            tmf = TrustManagerFactory.getInstance("X509");
            tmf.init((KeyStore) null);

            for (TrustManager trustManager : tmf.getTrustManagers()) {
                ((X509TrustManager) trustManager).checkServerTrusted(
                        chain, authType);
            }

        } catch (Exception e) {
            throw new CertificateException(e.toString());
        }

// Hack ahead: BigInteger and toString(). We know a DER encoded Public
        // Key starts with 0x30 (ASN.1 SEQUENCE and CONSTRUCTED), so there is
        // no leading 0x00 to drop.
        RSAPublicKey pubkey = (RSAPublicKey) chain[0].getPublicKey();
        String encoded = new BigInteger(1 /* positive */, pubkey.getEncoded())
                .toString(16);

// Pin it!
        final boolean expected = publicKey.equalsIgnoreCase(encoded);
        // fail if expected public key is different from our public key
        if (!expected) {
            throw new CertificateException("not trusted.");
        }


    }


    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }

    public SSLSocketFactory getSocketFactory() {
        TrustManager     tm[]                   = {new SSLPinningManager()};
        SSLSocketFactory pinnedSSLSocketFactory = null;
        try {
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, tm, null);
            pinnedSSLSocketFactory = context.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return pinnedSSLSocketFactory;
    }
}
