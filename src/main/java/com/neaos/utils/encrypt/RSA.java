package com.neaos.utils.encrypt;

import com.neaos.utils.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@SuppressWarnings({"WeakerAccess", "UnusedReturnValue"})
public class RSA {
    /**
     * 随机生成密钥对
     */
    public static RsaKeyPair genKeyPair() {
        try {
            // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
            return new RsaKeyPair(publicKey.getEncoded(), privateKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     */
    public static byte[] encrypt(byte[] str, byte[] publicKey) {
        try {
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKey));
            //RSA加密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String encryptBase64(byte[] str, byte[] publicKey) {
        return Base64.encode(encrypt(str, publicKey));
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     */
    public static byte[] decrypt(byte[] str, byte[] privateKey) {
        try {
            //base64编码的私钥
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(privateKey));
            //RSA解密
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decryptFromBase64(String str, byte[] privateKey) {
        return decrypt(Base64.decode(str), privateKey);
    }

    public static class RsaKeyPair {
        private byte[] publicKey;
        private byte[] privateKey;

        public RsaKeyPair(byte[] publicKey, byte[] privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public byte[] getPublicKey() {
            return publicKey;
        }

        public byte[] getPrivateKey() {
            return privateKey;
        }

        public String getPublicKeyBase64() {
            return Base64.encode(publicKey);
        }

        public String getPrivateKeyBase64() {
            return Base64.encode(privateKey);
        }
    }

}
