package org.xa.rbac.utils;


import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAUtils {
    //生成秘钥对
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    //获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    //将Base64编码后的公钥转换成PublicKey对象
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    //将Base64编码后的私钥转换成PrivateKey对象
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    //私钥解密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }


    //字节数组转Base64编码
    public static String byte2Base64(byte[] bytes){
//        BASE64Encoder encoder = new BASE64Encoder();
//        return encoder.encode(bytes);
        return new String(Base64.encodeBase64(bytes));
    }

    //Base64编码转字节数组
    public static byte[] base642Byte(String base64Key) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        return decoder.decodeBuffer(base64Key);
    }
}
//public class RSAUtils {
//
//    public static final String KEY_ALGORITHM = "RSA";
//    // public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
//    private static final String PUBLIC_KEY = "RSAPublicKey";
//    private static final String PRIVATE_KEY = "RSAPrivateKey";
//    private static final Base64.Encoder encoder=Base64.getEncoder();
//
//    // 获得公钥
//    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
//        // 获得map中的公钥对象 转为key对象
//        Key key = (Key) keyMap.get(PUBLIC_KEY);
//        // byte[] publicKey = key.getEncoded();
//        // 编码返回字符串
//        return new String(encoder.encode(key.getEncoded()));
//    }
//
//    // 获得私钥
//    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
//        // 获得map中的私钥对象 转为key对象
//        Key key = (Key) keyMap.get(PRIVATE_KEY);
//        // byte[] privateKey = key.getEncoded();
//        // 编码返回字符串
//        return new String(encoder.encode(key.getEncoded()),"UTF-8");
//    }
//
//    /**
//     * 生成公钥和私钥
//     *
//     * @throws NoSuchAlgorithmException
//     *
//     */
//    public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
//        HashMap<String, Object> map = new HashMap<String, Object>();
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
//        keyPairGen.initialize(1024);
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
//        map.put(PUBLIC_KEY, publicKey);
//        map.put(PRIVATE_KEY, privateKey);
//        return map;
//    }
//
//    /**
//     * 使用模和指数生成RSA公钥
//     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
//     * /None/NoPadding】
//     *
//     * @param modulus
//     *            模
//     * @param exponent
//     *            指数
//     * @return
//     */
//    public static RSAPublicKey getPublicKey(String modulus, String exponent) {
//        try {
//            BigInteger b1 = new BigInteger(modulus);
//            BigInteger b2 = new BigInteger(exponent);
//            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
//            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 使用模和指数生成RSA私钥
//     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA
//     * /None/NoPadding】
//     *
//     * @param modulus
//     *            模
//     * @param exponent
//     *            指数
//     * @return
//     */
//    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
//        try {
//            BigInteger b1 = new BigInteger(modulus);
//            BigInteger b2 = new BigInteger(exponent);
//            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
//            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    /**
//     * 公钥加密
//     *
//     * @param data
//     * @param publicKey
//     * @return
//     * @throws Exception
//     */
//    public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
//        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
//        // 模长
//        int key_len = publicKey.getModulus().bitLength() / 8;
//        // 加密数据长度 <= 模长-11
//        String[] datas = splitString(data, key_len - 11);
//        String mi = "";
//        // 如果明文长度大于模长-11则要分组加密
//        for (String s : datas) {
//            mi += bcd2Str(cipher.doFinal(s.getBytes()));
//        }
//        return mi;
//    }
//
//    /**
//     * 私钥解密
//     *
//     * @param data
//     * @param privateKey
//     * @return
//     * @throws Exception
//     */
//    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey) throws Exception {
//        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
//        cipher.init(Cipher.DECRYPT_MODE, privateKey);
//        // 模长
//        int key_len = privateKey.getModulus().bitLength() / 8;
//        byte[] bytes = data.getBytes();
//        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//        System.err.println(bcd.length);
//        // 如果密文长度大于模长则要分组解密
//        String ming = "";
//        byte[][] arrays = splitArray(bcd, key_len);
//        for (byte[] arr : arrays) {
//            ming += new String(cipher.doFinal(arr));
//        }
//        return ming;
//    }
//
//    /**
//     * ASCII码转BCD码
//     *
//     */
//    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
//        byte[] bcd = new byte[asc_len / 2];
//        int j = 0;
//        for (int i = 0; i < (asc_len + 1) / 2; i++) {
//            bcd[i] = asc_to_bcd(ascii[j++]);
//            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
//        }
//        return bcd;
//    }
//
//    public static byte asc_to_bcd(byte asc) {
//        byte bcd;
//
//        if ((asc >= '0') && (asc <= '9'))
//            bcd = (byte) (asc - '0');
//        else if ((asc >= 'A') && (asc <= 'F'))
//            bcd = (byte) (asc - 'A' + 10);
//        else if ((asc >= 'a') && (asc <= 'f'))
//            bcd = (byte) (asc - 'a' + 10);
//        else
//            bcd = (byte) (asc - 48);
//        return bcd;
//    }
//
//    /**
//     * BCD转字符串
//     */
//    public static String bcd2Str(byte[] bytes) {
//        char temp[] = new char[bytes.length * 2], val;
//
//        for (int i = 0; i < bytes.length; i++) {
//            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);
//            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
//
//            val = (char) (bytes[i] & 0x0f);
//            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');
//        }
//        return new String(temp);
//    }
//
//    /**
//     * 拆分字符串
//     */
//    public static String[] splitString(String string, int len) {
//        int x = string.length() / len;
//        int y = string.length() % len;
//        int z = 0;
//        if (y != 0) {
//            z = 1;
//        }
//        String[] strings = new String[x + z];
//        String str = "";
//        for (int i = 0; i < x + z; i++) {
//            if (i == x + z - 1 && y != 0) {
//                str = string.substring(i * len, i * len + y);
//            } else {
//                str = string.substring(i * len, i * len + len);
//            }
//            strings[i] = str;
//        }
//        return strings;
//    }
//
//    /**
//     * 拆分数组
//     */
//    public static byte[][] splitArray(byte[] data, int len) {
//        int x = data.length / len;
//        int y = data.length % len;
//        int z = 0;
//        if (y != 0) {
//            z = 1;
//        }
//        byte[][] arrays = new byte[x + z][];
//        byte[] arr;
//        for (int i = 0; i < x + z; i++) {
//            arr = new byte[len];
//            if (i == x + z - 1 && y != 0) {
//                System.arraycopy(data, i * len, arr, 0, y);
//            } else {
//                System.arraycopy(data, i * len, arr, 0, len);
//            }
//            arrays[i] = arr;
//        }
//        return arrays;
//    }
//
//    public static void main(String[] args) throws Exception {
//        HashMap<String, Object> keyMap = RSAUtils.getKeys();
//        String publicKey = getPublicKey(keyMap);
//        System.out.println(publicKey);
//        String privateKey = getPrivateKey(keyMap);
//        System.out.println(privateKey);
//
//		/*
//		HashMap<String, Object> map = RSAUtils.getKeys();
//
//		// 生成公钥和私钥
//		RSAPublicKey publicKey = (RSAPublicKey) map.get(PUBLIC_KEY);
//		RSAPrivateKey privateKey = (RSAPrivateKey) map.get(PRIVATE_KEY);
//
//		String publicKeyStr = Base64.encode(publicKey.getEncoded());
//		String privateKeyStr = Base64.encode(privateKey.getEncoded());
//
//		System.out.println(publicKeyStr);
//		System.out.println(privateKeyStr);
//
//		// 模
//		String modulus = publicKey.getModulus().toString();
//		// 公钥指数
//		String public_exponent = publicKey.getPublicExponent().toString();
//		// 私钥指数
//		String private_exponent = privateKey.getPrivateExponent().toString();
//		// 明文
//		String ming = "123456789";
//		// 使用模和指数生成公钥和私钥
//		RSAPublicKey pubKey = RSAUtils.getPublicKey(modulus, public_exponent);
//		RSAPrivateKey priKey = RSAUtils.getPrivateKey(modulus, private_exponent);
//		// 加密后的密文
//		String mi = RSAUtils.encryptByPublicKey(ming, pubKey);
//		System.err.println(mi);
//		// 解密后的明文
//		ming = RSAUtils.decryptByPrivateKey(mi, priKey);
//		System.err.println(ming);
//		*/
//    }
//}
