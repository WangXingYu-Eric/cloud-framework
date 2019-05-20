package com.sinosoft.newstandard.common.util;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.Objects;


/**
 * RSA加解密工具类,使用说明:
 * <pre>
 *         //public1.cer,private1.pfx放在项目的resources下即可
 *         PublicKey publicKey = RSAUtil.getPublicKey("public1.cer");
 *         PrivateKey privateKey = RSAUtil.getPrivateKey("private1.pfx", "Aa123456");
 *         String plainText = "这是明文";
 *
 *         //公钥加密,私钥解密
 *         byte[] enBytes1 = RSAUtil.encrypt(plainText.getBytes(), publicKey);
 *         byte[] deBytes1 = RSAUtil.decrypt(enBytes1, privateKey);
 *
 *         //私钥加密,公钥解密
 *         byte[] enBytes2 = RSAUtil.encrypt(plainText.getBytes(), privateKey);
 *         byte[] deBytes2 = RSAUtil.decrypt(enBytes2, publicKey);
 *
 *         //加签、验签
 *         byte[] signBytes = RSAUtil.sign(enBytes2, privateKey);
 *         boolean verify = RSAUtil.verify(enBytes2, publicKey, signBytes);
 * </pre>
 *
 * @Author: Eric
 * @Date: 2019-04-08
 **/
public class RSAUtils {

    private static final Logger logger = LoggerFactory.getLogger(RSAUtils.class);

    /**
     * 加密算法RSA.
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 签名算法.
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    /**
     * RSA加密明文分段最大长度.
     */
    private static final int MAX_ENCRYPT_SPLIT = 117;

    /**
     * RSA解密密分段最大长度,1024位加密用128,1028位用256.
     */
    private static final int MAX_DECRYPT_SPLIT = 256;

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance(KEY_ALGORITHM);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据*.cer文件获取公钥.
     *
     * @param name 文件名
     */
    public static PublicKey getPublicKey(String name) {

        PublicKey publicKey;

        try {
            InputStream resourceAsStream1 = Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResourceAsStream(name);
            // 创建X509工厂类
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            // 创建证书对象
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(resourceAsStream1);
            resourceAsStream1.close();
            publicKey = x509Certificate.getPublicKey();
        } catch (Exception e) {
            logger.error("解析证书出错.");
            e.printStackTrace();
            publicKey = null;
        }

        return publicKey;
    }

    /**
     * 根据*.pfx文件获取公钥.
     *
     * @param name     文件名
     * @param password 密码
     */
    public static PublicKey getPublicKey(String name, String password) {

        PublicKey publicKey;

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceAsStream2 = Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResourceAsStream(name);
            keyStore.load(resourceAsStream2, password.toCharArray());

            Enumeration enumas = keyStore.aliases();
            String aliases = null;
            if (enumas.hasMoreElements()) {
                aliases = (String) enumas.nextElement();
            }
            publicKey = keyStore.getCertificate(aliases).getPublicKey();
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException e) {
            logger.error("解析证书出错.");
            e.printStackTrace();
            publicKey = null;
        }
        return publicKey;
    }

    /**
     * 根据*.pfx文件获取私钥.
     *
     * @param name     文件名
     * @param password 密码
     */
    public static PrivateKey getPrivateKey(String name, String password) {

        PrivateKey privateKey;

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            InputStream resourceAsStream2 = Objects.requireNonNull(ClassUtils.getDefaultClassLoader()).getResourceAsStream(name);
            keyStore.load(resourceAsStream2, password.toCharArray());

            Enumeration enumas = keyStore.aliases();
            String aliases = null;
            if (enumas.hasMoreElements()) {
                aliases = (String) enumas.nextElement();
            }
            privateKey = (PrivateKey) keyStore.getKey(aliases, password.toCharArray());
        } catch (KeyStoreException | IOException | CertificateException | NoSuchAlgorithmException | UnrecoverableKeyException e) {
            logger.error("解析证书出错.");
            e.printStackTrace();
            privateKey = null;
        }
        return privateKey;
    }

    /**
     * RSA加密.
     *
     * @param plainBytes 明文
     * @param key        key
     */
    public static byte[] encrypt(byte[] plainBytes, Key key) throws InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return sectionAnalysis(plainBytes, MAX_ENCRYPT_SPLIT);
    }

    /**
     * RSA解密.
     *
     * @param cipherBytes 密文
     * @param key         key
     */
    public static byte[] decrypt(byte[] cipherBytes, Key key) throws InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        return sectionAnalysis(cipherBytes, MAX_DECRYPT_SPLIT);
    }

    /**
     * 分段解析.
     *
     * @param bytes       需要解析的内容
     * @param splitLength 分段最大长度
     */
    private static byte[] sectionAnalysis(byte[] bytes, int splitLength) throws BadPaddingException, IllegalBlockSizeException, IOException {
        int totalLength = bytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] temp;
        int i = 0;
        // 对数据分段解密
        while (totalLength - offSet > 0) {
            if (totalLength - offSet > splitLength) {
                temp = cipher.doFinal(bytes, offSet, splitLength);
            } else {
                temp = cipher.doFinal(bytes, offSet, totalLength - offSet);
            }
            out.write(temp, 0, temp.length);
            i++;
            offSet = i * splitLength;
        }
        byte[] cipherBytes = out.toByteArray();
        out.close();
        return cipherBytes;
    }


    /**
     * 加签.
     */
    public static byte[] sign(byte[] bytes, PrivateKey privateKey) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        return sign(bytes, privateKey, RSAUtils.SIGNATURE_ALGORITHM);
    }

    public static byte[] sign(byte[] bytes, PrivateKey privateKey, String algorithm) throws InvalidKeyException, NoSuchAlgorithmException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(bytes);
        return Base64.encodeBase64(signature.sign());
    }

    /**
     * 验签.
     */
    public static boolean verify(byte[] bytes, PublicKey publicKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return verify(bytes, publicKey, sign, RSAUtils.SIGNATURE_ALGORITHM);
    }

    public static boolean verify(byte[] bytes, PublicKey publicKey, byte[] sign, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(publicKey);
        signature.update(bytes);
        return signature.verify(Base64.decodeBase64(sign));
    }

}
