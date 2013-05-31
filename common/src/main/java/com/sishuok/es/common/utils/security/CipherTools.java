package com.sishuok.es.common.utils.security;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

public class CipherTools {

    private final static Logger log = LoggerFactory.getLogger(CipherTools.class);
    private static final String CHARSET_NAME = "UTF-8";

    public static String initKey(String key) {
        try {
            return DESCoder.initKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("initKey error " + e);
        }
        return key;
    }


    private static String encoding(byte[] bytes) throws Exception {
        return Base32.encode(bytes);
    }

    private static byte[] decoding(String value) throws Exception {
        return Base32.decode(value);
    }

    private static byte[] encrypt(String key, byte[] data) throws Exception {
        return DESCoder.encrypt(data, key);
    }

    public static String encrypt(String value, String key) {
        try {
            byte[] data;
            if (!StringUtils.isEmpty(CHARSET_NAME)) {
                try {
                    data = value.getBytes(CHARSET_NAME);
                } catch (Exception e1) {
                    log.error("charset " + CHARSET_NAME + " Unsupported!", e1);
                    data = value.getBytes();
                }
            } else {
                data = value.getBytes();
            }
            byte[] bytes = encrypt(key, data);
            return encoding(bytes);
        } catch (Exception e) {
            log.error("encrypt error", e);
            return null;
        }
    }

    private static byte[] decrypt(String key, byte[] data) throws Exception {
        return DESCoder.decrypt(data, key);
    }

    public static String decrypt(String value, String key) {
        try {
            byte[] data = decoding(value);
            byte[] bytes = decrypt(key, data);
            if (!StringUtils.isEmpty(CHARSET_NAME)) {
                try {
                    return new String(bytes, CHARSET_NAME);
                } catch (UnsupportedEncodingException e1) {
                    log.error("charset " + CHARSET_NAME + " Unsupported!", e1);
                    return new String(bytes);
                }
            } else {
                return new String(bytes);
            }
        } catch (Exception e) {
            log.error("decrypt error", e);
            return null;
        }
    }

}
