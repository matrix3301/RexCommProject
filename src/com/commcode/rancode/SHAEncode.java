package com.commcode.rancode;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Rex Tan
 * @version 1.0
 * @since 2018/8/29
 * 产生一串希哈字符串
 */
public class SHAEncode {
    private String orgString;
    private String encodeString;
    private String encode;

    SHAEncode(String orgString, String encode) {
        this.orgString = orgString;
        this.encode = encode;
    }

    // 重写toString函数，返回加密后的字符串
    @Override
    public String toString() {
        return encodeString;
    }

    /*
     * 字符串转码
     */
    public String enCoding() throws NoSuchAlgorithmException {
        // 调用需要转码的构造函数
        MessageDigest mySHA = MessageDigest.getInstance(encode);
        // 将需要转码的字符串以字节数组的形式赋值转码对象
        mySHA.update(orgString.getBytes());
        // 获得转码后的字节数组
        byte[] encodeBytes = mySHA.digest();
        // 将字节数组转为字符串
        encodeString = bytes2Hex(encodeBytes);

        return encodeString;
    }

    /*
     * 二进制转字符串函数
     */
     private static String bytes2Hex(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }

        char[] res = new char[src.length * 2]; // 每个byte对应两个字符
        final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        for (int i = 0, j = 0; i < src.length; i++) {
            res[j++] = hexDigits[src[i] >> 4 & 0x0f]; // 先存byte的高4位
            res[j++] = hexDigits[src[i] & 0x0f]; // 再存byte的低4位
        }

        return new String(res);
    }
}
