package com.can.store.androidbcc.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * {@link Throwable} のスタックトレースに関するユーティリティメソッドを提供します。
 *
 */
public class StackTraceUtil {

    /**
     * ユーティリティクラスのため、コンストラクタを公開しません。
     */
    private StackTraceUtil() {
    }

    /**
     * 指定された例外のスタックトレースを文字列に変換します。
     */
    public static String toString(Throwable e) {
        if(null == e) {
            return "null";
        }
        StringWriter sw = new StringWriter(1000);
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
