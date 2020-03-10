/**
 * Epaygg.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.newing.utils.encrypt;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * 
 * @author huliang
 * @version $Id: RsaUtil.java, v 0.1 2017年6月22日 下午8:40:22 huliang Exp $
 */
public abstract class RsaUtil {

    /**
     * RSA公钥加密
     * 
     * @param pemEncodedPublicKey  RSA公钥
     * @param plaintext            需要加密的明文数据
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public static String encrypt(String pemEncodedPublicKey, String plaintext) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        pemEncodedPublicKey = pemEncodedPublicKey.replaceAll("(-+BEGIN (RSA )?PUBLIC KEY-+\\r?\\n|-+END (RSA )?PUBLIC KEY-+\\r?\\n?)", "");
        
        byte[] buffer = encodeBase64.decode(pemEncodedPublicKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] resultBytes = cipher.doFinal(plaintext.getBytes());
        return  HexUtil.byte2HexStr(resultBytes);
    }
    
    public static PublicKey getPublicKeyFromPem(String algorithm, String pemEncodedPublicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        pemEncodedPublicKey = pemEncodedPublicKey.replaceAll("(-+BEGIN (RSA )?PUBLIC KEY-+\\r?\\n|-+END (RSA )?PUBLIC KEY-+\\r?\\n?)", "");
        byte[] publicKeyBytes = encodeBase64.decode(pemEncodedPublicKey);

        return generatePublicKeyWithX509(algorithm, publicKeyBytes);
    }
    
    private static PublicKey generatePublicKeyWithX509(String algorithm, byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        return keyFactory.generatePublic(keySpec);
    }
    
    
    public static PrivateKey getPrivateKeyFromPem(String algorithm, String pemEncodedPrivateKey) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        pemEncodedPrivateKey = pemEncodedPrivateKey.replaceAll("(-+BEGIN (RSA )?PRIVATE KEY-+\\r?\\n|-+END (RSA )?PRIVATE KEY-+\\r?\\n?)", "");
        byte[] privateKeyBytes = encodeBase64.decode(pemEncodedPrivateKey);

//        try {
            return generatePrivateKeyWithPKCS8(algorithm, privateKeyBytes);
//        } catch (InvalidKeySpecException e) {
//            e.printStackTrace();
//            return generatePrivateKeyWithPKCS1(privateKeyBytes);
//        }
    }
    
    private static PrivateKey generatePrivateKeyWithPKCS8(String algorithm, byte[] privateKeyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory kf = KeyFactory.getInstance(algorithm);
        return kf.generatePrivate(keySpec);
    }
    
//    private static PrivateKey generatePrivateKeyWithPKCS1(byte[] privateKeyBytes) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
//        DerInputStream derReader = new DerInputStream(privateKeyBytes);
//        DerValue[] seq = derReader.getSequence(0);
//        if (seq.length < 9) {
//            System.out.println("Could not parse a PKCS1 private key.");
//            return null;
//        }
//        // skip version seq[0];
//        BigInteger modulus = seq[1].getBigInteger();
//        BigInteger publicExp = seq[2].getBigInteger();
//        BigInteger privateExp = seq[3].getBigInteger();
//        BigInteger prime1 = seq[4].getBigInteger();
//        BigInteger prime2 = seq[5].getBigInteger();
//        BigInteger exp1 = seq[6].getBigInteger();
//        BigInteger exp2 = seq[7].getBigInteger();
//        BigInteger crtCoef = seq[8].getBigInteger();
//        RSAPrivateCrtKeySpec spec = new RSAPrivateCrtKeySpec(modulus, publicExp, privateExp, prime1, prime2, exp1, exp2, crtCoef);
//        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//
//        return keyFactory.generatePrivate(spec);
//    }
    
}
