package org.choviwu.npcjava.plugin.data;

import com.alibaba.fastjson.JSON;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import org.choviwu.npcjava.plugin.constant.Constant;
import org.choviwu.npcjava.plugin.exception.NpcException;

import java.io.Serializable;

@Builder
@Data
@Accessors(chain = true)
public class Message implements Serializable {


    private Object data;

    private String message;

    private Integer code;


    public static String getMessage(String msg, int code) {
        return getMessage(msg, code, "");
    }

    public static String getMessage(Object data) {
        return getMessage(Constant.MESSAGE, Constant.SUCCESS_CODE, data);
    }


    public static String getMessage(NpcException npcException) {
        return getMessage(Constant.FAIL, npcException.getCode(), npcException.getMessage());
    }

    public static String getMessage(String msg, int code, Object data) {
        return JSON.toJSONString(Message.builder().message(msg)
                .code(code).data(data).build());
    }
}
