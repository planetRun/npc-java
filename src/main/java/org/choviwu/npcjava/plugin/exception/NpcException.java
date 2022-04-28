package org.choviwu.npcjava.plugin.exception;

public class NpcException extends RuntimeException{

    private String message;

    private Integer code;

    public NpcException(Integer code, String message) {
        super(message);
        this.message = message;
        this.code = code;

    }

    public NpcException(){}

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
