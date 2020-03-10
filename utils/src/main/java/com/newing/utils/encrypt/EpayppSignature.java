/**
 * Epaygg.com Inc.
 * Copyright (c) 2016-2017 All Rights Reserved.
 */
package com.newing.utils.encrypt;



import com.newing.utils.codec.Base64;
import com.newing.utils.codec.EpayppConstants;
import com.newing.utils.codec.EpayppHashMap;
import com.newing.utils.codec.RequestParametersHolder;
import com.newing.utils.codec.StreamUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Cipher;


/**
 * 
 * @author huliang
 * @version $Id: EpayppSignature.java, v 0.1 2017年6月26日 下午3:33:01 huliang Exp $
 */
public class EpayppSignature {

    /** RSA最大加密明文大小  */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /** RSA最大解密密文大小   */
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String getSignatureContent(RequestParametersHolder requestHolder) {
        return getSignContent(getSortedMap(requestHolder));
    }

    public static Map<String, String> getSortedMap(RequestParametersHolder requestHolder) {
        Map<String, String> sortedParams = new TreeMap<String, String>();
        EpayppHashMap appParams = requestHolder.getApplicationParams();
        if (appParams != null && appParams.size() > 0) {
            sortedParams.putAll(appParams);
        }
        EpayppHashMap protocalMustParams = requestHolder.getProtocalMustParams();
        if (protocalMustParams != null && protocalMustParams.size() > 0) {
            sortedParams.putAll(protocalMustParams);
        }
        EpayppHashMap protocalOptParams = requestHolder.getProtocalOptParams();
        if (protocalOptParams != null && protocalOptParams.size() > 0) {
            sortedParams.putAll(protocalOptParams);
        }
        return sortedParams;
    }

    /**
     * @param treeMap
     * @return
     */
    private static String getSignContent(Map<String, String> treeMap) {
        StringBuffer content = new StringBuffer();
        for (String key : treeMap.keySet()) {
            content.append(key).append(StringUtils.trimToEmpty(treeMap.get(key)));
        }
        return content.toString();
    }

    /**
     * RSA签名
     *
     * @param content
     * @param privateKey
     * @param charset
     * @param signType
     * @return
     * @throws EpayppApiException
     */
    public static String rsaSign(String content, String privateKey, String charset,
                                 String signType) throws EpayppApiException {
        if (EpayppConstants.API_SIGN_METHOD_RSA.equals(signType)) {
            return rsaSign(content, privateKey, charset);
        } else if (EpayppConstants.API_SIGN_METHOD_RSA2.equals(signType)) {
            return rsa256Sign(content, privateKey, charset);
        } else {
            throw new EpayppApiException("Sign Type is Not Support : signType=" + signType);
        }
    }

    /**
     * sha256WithRsa 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws EpayppApiException
     */
    public static String rsa256Sign(String content, String privateKey,
                                    String charset) throws EpayppApiException {

        try {
            PrivateKey priKey = RsaUtil.getPrivateKeyFromPem(EpayppConstants.API_SIGN_METHOD_RSA, privateKey);

            Signature signature = Signature.getInstance(EpayppConstants.SIGN_SHA256RSA_ALGORITHMS);

            signature.initSign(priKey);

            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();

            return new String(Base64.encodeBase64(signed));
        } catch (Exception e) {
            throw new EpayppApiException("RSAcontent = " + content + "; charset = " + charset, e);
        }

    }

    /**
     * SHA1WithRSA 加签
     *
     * @param content
     * @param privateKey
     * @param charset
     * @return
     * @throws EpayppApiException
     */
    public static String rsaSign(String content, String privateKey, String charset) throws EpayppApiException {
        try {
            PrivateKey priKey = RsaUtil.getPrivateKeyFromPem(EpayppConstants.API_SIGN_METHOD_RSA, privateKey);

            Signature signature = Signature.getInstance(EpayppConstants.SIGN_ALGORITHMS);
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            byte[] signBytes = signature.sign();
            return StringUtils.byte2HexStr(signBytes);
        } catch (InvalidKeySpecException e) {
            throw new EpayppApiException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", e);
        } catch (Exception e) {
            throw new EpayppApiException(String.format("RSA content = %s; charset = %s", content, charset), e);
        }
    }

    public static String getSignCheckContentV1(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");
        params.remove("sign_type");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static String getSignCheckContentV2(Map<String, String> params) {
        if (params == null) {
            return null;
        }

        params.remove("sign");

        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            content.append((i == 0 ? "" : "&") + key + "=" + value);
        }

        return content.toString();
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey,
                                     String charset) throws EpayppApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV1(Map<String, String> params, String publicKey, String charset,
                                     String signType) throws EpayppApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV1(params);

        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey, String charset) throws EpayppApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);

        return rsaCheckContent(content, sign, publicKey, charset);
    }

    public static boolean rsaCheckV2(Map<String, String> params, String publicKey, String charset,
                                     String signType) throws EpayppApiException {
        String sign = params.get("sign");
        String content = getSignCheckContentV2(params);

        return rsaCheck(content, sign, publicKey, charset, signType);
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) throws EpayppApiException {
        if (EpayppConstants.API_SIGN_METHOD_RSA.equals(signType)) {
            return rsaCheckContent(content, sign, publicKey, charset);
        } else if (EpayppConstants.API_SIGN_METHOD_RSA2.equals(signType)) {
            return rsa256CheckContent(content, sign, publicKey, charset);
        } else {
            throw new EpayppApiException("Sign Type is Not Support : signType=" + signType);
        }
    }

    public static boolean rsa256CheckContent(String content, String sign, String publicKey, String charset) throws EpayppApiException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance(EpayppConstants.SIGN_SHA256RSA_ALGORITHMS);
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception e) {
            throw new EpayppApiException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static boolean rsaCheckContent(String content, String sign, String publicKey, String charset) throws EpayppApiException {
        try {
            PublicKey pubKey = RsaUtil.getPublicKeyFromPem("RSA", publicKey);

            Signature signature = Signature.getInstance(EpayppConstants.SIGN_ALGORITHMS);
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }
            return signature.verify(StringUtils.hexStr2Byte(sign));
        } catch (Exception e) {
            throw new EpayppApiException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, e);
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm,
                                                 InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);

        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);

        byte[] encodedKey = writer.toString().getBytes();

        encodedKey =Base64.decodeBase64(encodedKey);

        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }

    /**
     *
     *
     * @param params
     * @param epayppPublicKey
     * @param cusPrivateKey
     * @param isCheckSign
     * @param isDecrypt
     * @return
     * @throws EpayppApiException
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String epayppPublicKey,
                                             String cusPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt) throws EpayppApiException {
        String charset = params.get("charset");
        String bizContent = params.get("biz_content");
        if (isCheckSign) {
            if (!rsaCheckV2(params, epayppPublicKey, charset)) {
                throw new EpayppApiException("rsaCheck failure:rsaParams=" + params);
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, cusPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     *
     *
     * @param params
     * @param epayppPublicKey
     * @param cusPrivateKey
     * @param isCheckSign
     * @param isDecrypt
     * @param signType
     * @return
     * @throws EpayppApiException
     */
    public static String checkSignAndDecrypt(Map<String, String> params, String epayppPublicKey,
                                             String cusPrivateKey, boolean isCheckSign,
                                             boolean isDecrypt,
                                             String signType) throws EpayppApiException {
        String charset = params.get("charset");
        String bizContent = params.get("biz_content");
        if (isCheckSign) {
            if (!rsaCheckV2(params, epayppPublicKey, charset, signType)) {
                throw new EpayppApiException("rsaCheck failure:rsaParams=" + params);
            }
        }

        if (isDecrypt) {
            return rsaDecrypt(bizContent, cusPrivateKey, charset);
        }

        return bizContent;
    }

    /**
     *
     *
     * @param bizContent
     * @param epayppPublicKey
     * @param cusPrivateKey
     * @param charset
     * @param isEncrypt
     * @param isSign
     * @return
     * @throws EpayppApiException
     */
    public static String encryptAndSign(String bizContent, String epayppPublicKey,
                                        String cusPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign) throws EpayppApiException {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = EpayppConstants.CHARSET_GBK;
        }
        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        if (isEncrypt) {// 加密
            sb.append("<Epaypp>");
            String encrypted = rsaEncrypt(bizContent, epayppPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if (isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>RSA</sign_type>");
            }
            sb.append("</Epaypp>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<Epaypp>");
            sb.append("<response>" + bizContent + "</response>");
            String sign = rsaSign(bizContent, cusPrivateKey, charset);
            sb.append("<sign>" + sign + "</sign>");
            sb.append("<sign_type>RSA</sign_type>");
            sb.append("</Epaypp>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }

    /**
     *
     *
     * @param bizContent
     * @param epayppPublicKey
     * @param cusPrivateKey
     * @param charset
     * @param isEncrypt
     * @param isSign
     * @param signType
     * @return
     * @throws EpayppApiException
     */
    public static String encryptAndSign(String bizContent, String epayppPublicKey,
                                        String cusPrivateKey, String charset, boolean isEncrypt,
                                        boolean isSign, String signType) throws EpayppApiException {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isEmpty(charset)) {
            charset = EpayppConstants.CHARSET_GBK;
        }
        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
        if (isEncrypt) {// 加密
            sb.append("<Epaypp>");
            String encrypted = rsaEncrypt(bizContent, epayppPublicKey, charset);
            sb.append("<response>" + encrypted + "</response>");
            sb.append("<encryption_type>RSA</encryption_type>");
            if (isSign) {
                String sign = rsaSign(encrypted, cusPrivateKey, charset, signType);
                sb.append("<sign>" + sign + "</sign>");
                sb.append("<sign_type>");
                sb.append(signType);
                sb.append("</sign_type>");
            }
            sb.append("</Epaypp>");
        } else if (isSign) {// 不加密，但需要签名
            sb.append("<Epaypp>");
            sb.append("<response>" + bizContent + "</response>");
            String sign = rsaSign(bizContent, cusPrivateKey, charset, signType);
            sb.append("<sign>" + sign + "</sign>");
            sb.append("<sign_type>");
            sb.append(signType);
            sb.append("</sign_type>");
            sb.append("</Epaypp>");
        } else {// 不加密，不加签
            sb.append(bizContent);
        }
        return sb.toString();
    }

    /**
     * 公钥加密
     *
     * @param content   待加密内容
     * @param publicKey 公钥
     * @param charset   字符集，如UTF-8, GBK, GB2312
     * @return 密文内容
     * @throws EpayppApiException
     */
    public static String rsaEncrypt(String content, String publicKey,
                                    String charset) throws EpayppApiException {
        try {
            PublicKey pubKey = getPublicKeyFromX509(EpayppConstants.API_SIGN_METHOD_RSA, new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance(EpayppConstants.API_SIGN_METHOD_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();

            return StringUtils.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
        } catch (Exception e) {
            throw new EpayppApiException("EncryptContent = " + content + ",charset = " + charset, e);
        }
    }

    /**
     * 私钥解密
     *
     * @param content    待解密内容
     * @param privateKey 私钥
     * @param charset    字符集，如UTF-8, GBK, GB2312
     * @return 明文内容
     * @throws EpayppApiException
     */
    public static String rsaDecrypt(String content, String privateKey,
                                    String charset) throws EpayppApiException {
        try {
            PrivateKey priKey = RsaUtil.getPrivateKeyFromPem(EpayppConstants.API_SIGN_METHOD_RSA, privateKey);
            Cipher cipher = Cipher.getInstance(EpayppConstants.API_SIGN_METHOD_RSA);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64.decodeBase64(content.getBytes()) : Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            out.close();

            return StringUtils.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
        } catch (Exception e) {
            throw new EpayppApiException("EncodeContent = " + content + ",charset = " + charset, e);
        }
    }

}
