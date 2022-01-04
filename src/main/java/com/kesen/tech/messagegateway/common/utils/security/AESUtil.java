package com.kesen.tech.messagegateway.common.utils.security;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;

import java.nio.charset.StandardCharsets;

/**
 * ASE对称加密算法，默认：AES/CBC/PKCS5Padding
 *
 * @version
 * 
 */
public class AESUtil {

    /**
     * 默认密钥
     */
    private static final String DEF_SECRET_KEY = "3F255C928B510BCFDC30642D35356AF6";

    /**
     * 加密数据并返回Hex字符串 encryptHex
     * 
     * @author yangfei
     * @date 2017年11月1日下午4:14:21
     * @param data
     *            被加密数据
     * @param secretKeyHex
     *            加密密钥Hex字符串，通过generateKey生成的加密密钥
     * @return 解密后的String
     */
    public static String encrypt(String data, String secretKeyHex) {
        return SecureUtil.aes(HexUtil.decodeHex(secretKeyHex)).encryptHex(data, StandardCharsets.UTF_8.name());
    }

    public static String encrypt(byte[] data, String secretKeyHex) {
        return SecureUtil.aes(HexUtil.decodeHex(secretKeyHex)).encryptHex(data);
    }

    /**
     * encrypt使用默认密钥加密数据
     * 
     * @author yangfei
     * @date 2017年11月1日下午4:31:59
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        return SecureUtil.aes(HexUtil.decodeHex(DEF_SECRET_KEY)).encryptHex(data, StandardCharsets.UTF_8.name());
    }

    public static String encrypt(byte[] data) {
        return SecureUtil.aes(HexUtil.decodeHex(DEF_SECRET_KEY)).encryptHex(data);
    }

    /**
     * 使用指定密钥解密数据 decrypt
     * 
     * @author yangfei
     * @date 2017年11月1日下午4:08:23
     * @param data
     *            解密数据
     * @param secretKeyHex
     *            加密密钥（Hex字符串）
     * @return 解密后的String
     */
    public static String decrypt(String data, String secretKeyHex) {
        return SecureUtil.aes(HexUtil.decodeHex(secretKeyHex)).decryptStr(data);
    }

    public static String decrypt(byte[] data, String secretKeyHex) {
        return SecureUtil.aes(HexUtil.decodeHex(secretKeyHex)).decryptStr(data);
    }

    /**
     * 使用默认密钥解密数据 decrypt
     * 
     * @author yangfei
     * @date 2017年11月1日下午4:24:24
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        return SecureUtil.aes(HexUtil.decodeHex(DEF_SECRET_KEY)).decryptStr(data);
    }

    public static String decrypt(byte[] data) {
        return SecureUtil.aes(HexUtil.decodeHex(DEF_SECRET_KEY)).decryptStr(data);
    }
}
