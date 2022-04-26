package org.choviwu.npcjava.plugin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xiaoleilu.hutool.http.HttpResponse;
import com.xiaoleilu.hutool.http.HttpUtil;
import com.xiaoleilu.hutool.http.Method;
import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
* @author wucw
* @description 针对表【t_client】的数据库操作Service实现
* @createDate 2022-04-26 16:19:08
*/
@Service
@Slf4j
public class ClientService {

    @Value("${npc.authKey}")
    private String authKey;


    //    添加客户端
    public String addClient(String serverAddress, String vkey)  {
        try {
            String timestamp = getTimeStamp(serverAddress);
            String auth_key = MD5Utils.md5(authKey + timestamp);

            String format = String.format("%s/client/add?vkey=%s&auth_key=%s&timestamp=%s&config_conn_allow=%s", serverAddress, vkey, auth_key, timestamp, 1);
            log.info("请求的url：{}", format);
            HttpResponse execute = HttpUtil.createRequest(Method.POST, format).execute();
            String body = execute.body();
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //    根据ID获取客户端
    public String getClientById(String serverAddress, String id) throws IOException {

        String timestamp = getTimeStamp(serverAddress);
        String auth_key = MD5Utils.md5(authKey + timestamp);
        String uri = String.format("%s/client/getclient?id=%s&auth_key=%s&timestamp=%s", serverAddress, id, auth_key, timestamp);
        String post = HttpUtil.post(uri, "");
        return post;
    }



    //    请求获得时间戳
    private String getTimeStamp(String serverAddress) throws IOException {
        String post = HttpUtil.post(String.format("%s/auth/gettime", serverAddress), "");
        JSONObject jsonObject = JSON.parseObject(post);
        String time = jsonObject.getString("time");
        return time;
    }
}
