package com.can.store.androidbcc.util;


/**
 * IntegerのUtil
 *
 * @author hokuto
 */
public class IntegerUtil {

    /**
     *
     * @return
     */
    public static boolean compare(Integer x, Integer y) {

        if(x == null && y == null){
            return true;
        }
        if(x == null && y != null){
            return false;
        }
        if(x != null && y == null){
            return false;
        }
        if(x.equals(y)){
            return true;
        }else{

        }
        return false;
    }

    /**
     *
     * @param value
     * @return
     */
    public static int parseInt(String value) {
        if(value == null){
            return 0;
        }
        value = hankakuToZenkaku(value);
        if(StringUtil.isNotEmpty(value)) {
            try {
                value = value.replaceAll(",", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
                return (int) Float.parseFloat(value);
            } catch (NumberFormatException e) {
                throw e;
            }
        } else {
            return 0;
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    public static int parseInt(Object obj) {
        if(obj == null){
            return 0;
        }
        String value = String.valueOf(obj);
        value = hankakuToZenkaku(value);
        if(StringUtil.isNotEmpty(value)) {
            try {
                value = value.replaceAll(",", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "");
                return (int) Float.parseFloat(value);
            } catch (NumberFormatException e) {
                throw e;
            }
        } else {
            return 0;
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    public static Integer parseInteger(Object obj) {
        if(obj == null) {
            return null;
        }
        String value = String.valueOf(obj);

        if(value.equals("null")) {
            return null;
        }

        value = hankakuToZenkaku(value);
        if(StringUtil.isNotEmpty(value)) {
            try {
                value = value.replaceAll(",", "").replaceAll("\n", "").replaceAll("\r", "").replaceAll("\t", "").replace("\\", "");
                return (int) Float.parseFloat(value);
            } catch (NumberFormatException e) {
                throw e;
            }
        } else {
            return 0;
        }
    }

    /**
     * 全角を半角に変換します。
     *
     * @param s
     * @return
     */
    private static String hankakuToZenkaku(String s) {
        if(StringUtil.isNotEmpty(s)) {
            StringBuffer sb = new StringBuffer(s);
            for(int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if(c >= '０' && c <= '９') {
                    sb.setCharAt(i, (char) (c - '０' + '0'));
                }
            }
            return sb.toString();
        } else {
            return s;
        }

    }
}
