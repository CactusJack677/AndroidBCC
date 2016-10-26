package com.can.store.androidbcc.exception;

import java.util.logging.Logger;

import slim3.jackpot.util.StackTraceUtil;

public class MyException extends RuntimeException {

    // ================================================================
    // Logger
    public final static Logger log = Logger.getLogger(MyException.class.getName());
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     * デフォルト・コンストラクタ
     */
    public MyException() {
        super();
    }

    /** 表示用のメッセージ */
    private String dispMessage;

    /**
     * メッセージ付け異常を構築する
     *
     * @param message
     *            エラーメッセージ
     */
    public MyException(String message) {
        super(message);
        this.setDispMessage(message);
    }

    /**
     * 画面表示用のエラーメッセージを返却します。
     */
    public String getMessage() {
        return this.getDispMessage();
    }

    /**
     * メッセージ付け、異常の発生元（throwable）情報を持つ異常を構築する
     *
     * @param message
     * @param throwable
     */
    public MyException(String message, Throwable throwable) {
        super(message, throwable);
        log.info(StackTraceUtil.toString(throwable));
    }

    /**
     * 異常を発生する元（throwable）情報を持つ異常を構築する
     *
     * @param throwable
     *            Throwable
     */
    public MyException(Throwable throwable) {
        super(throwable);
        if(throwable instanceof MyException){
            this.setDispMessage(((MyException)throwable).dispMessage);
        }
        log.info(StackTraceUtil.toString(throwable));
    }

    public String getDispMessage() {
        return dispMessage;
    }

    public void setDispMessage(String dispMessage) {
        this.dispMessage = dispMessage;
    }

}
