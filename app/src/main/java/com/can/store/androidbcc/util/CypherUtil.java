
package com.can.store.androidbcc.util;

import com.can.store.androidbcc.Const;

import org.apache.commons.codec.binary.Hex;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CypherUtil {
    private final static Logger log = Logger.getLogger(CypherUtil.class.getName());

    /**
     * 暗号化後にBase64変換して返す
     * 
     * @param key
     * @param text
     * @return
     */
    public static String encStr(String key, String text) {

        Cipher cipher;
        try {
            SecretKeySpec sksSpec = new SecretKeySpec(key.getBytes(Const.DEFAULT_CONTENT_TYPE), "DES");
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(Const.DEFAULT_CONTENT_TYPE));

            // log.info("暗号化しました。");
            return encodeBase64Str(encrypted);
        } catch (Exception e) {
            log.warning(StackTraceUtil.toString(e));
        }
        return null;
    }

    /**
     * デコード後にBase64変換して返す
     * 
     * @param strkey
     * @param bytes
     * @return
     */
    public static String dec(String strkey, String bytes) {
        try {
            byte[] decodeBase64 = decodeBase64(bytes.getBytes(Const.DEFAULT_CONTENT_TYPE));
            SecretKeySpec key = new SecretKeySpec(strkey.getBytes(Const.DEFAULT_CONTENT_TYPE), "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(decodeBase64);

            String str = new String(decrypted, Const.DEFAULT_CONTENT_TYPE);
            // log.info("デコード後→" + str);
            return str;
        } catch (Exception e) {
            log.warning(StackTraceUtil.toString(e));
            return null;
        }
    }

    // public static byte[] encodeBase64(byte[] data) {
    // log.info("Base64エンコードします。");
    // return Base64.encodeBase64(data);
    // }

    public static byte[] decodeBase64(byte[] data) {
        // log.info("Base64デコードします。");
        try {
            return Base64Util.decode(new String(data, Const.DEFAULT_CONTENT_TYPE));
            // return com.google.appengine.repackaged.com.google.common.util.Base64.decode(data);
        } catch (Exception e) {
            log.warning(StackTraceUtil.toString(e));
            return null;
        }
    }

    public static String encodeBase64Str(byte[] data) {
        // log.info("Base64デコードします。");
        return Base64Util.encode(data);
        // return new String(encodeBase64);
        // return com.google.appengine.repackaged.com.google.common.util.Base64.encode(data);
    }

    public static final String BLOWFISH_KEY = "fd7jf.ak";;
    public static final String ALGORITHM = "Blowfish";

    public static String encodeBrowfish(String data) {
        try {
            // log.info(String.format("Blowfishでエンコードします。 [value=%s]",data));

            SecretKeySpec sksSpec = new SecretKeySpec(BLOWFISH_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, sksSpec);
            byte[] encrypted = cipher.doFinal(data.getBytes());

            String result = new String(Hex.encodeHex(encrypted));
            ;
            // log.info(String.format("エンコードしました。 [value=%s]",result));
            return result;

        } catch (Exception e) {
            log.warning(StackTraceUtil.toString(e));
        }
        return null;
    }

    public static String decodeBrowfish(String data) {
        try {
            // log.info(String.format("Blowfishでデコードします。[value=%s]",data));

            byte[] encrypted = Hex.decodeHex(data.toCharArray());
            SecretKeySpec sksSpec = new SecretKeySpec(BLOWFISH_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, sksSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            String result = new String(decrypted);
            // log.info(String.format("デコードしました。[value=%s]",result));

            return result;
        } catch (Exception e) {
            log.warning(StackTraceUtil.toString(e));
        }
        return null;
    }

}