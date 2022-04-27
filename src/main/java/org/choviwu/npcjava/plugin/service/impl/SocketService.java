package org.choviwu.npcjava.plugin.service.impl;

import com.alibaba.fastjson.JSON;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class SocketService {

    @Autowired
    private NpcConfig npcConfig;

    @Autowired
    private String operateBean;


    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(20, 20, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    // 1. ping/pong
    // 2. 连接
    public void recvHandle(String payload, WebSocketSession webSocketSession) {

        if (StringUtils.isEmpty(payload)) {
            return;
        }

        if (Objects.equals("cancel", payload)) {
            ExecuteService executeService = new ExecuteService(npcConfig, operateBean);
            EXECUTOR.execute(() -> executeService.execCancelCMD(webSocketSession, npcConfig.getConfPath()));
        }else if (Objects.equals("ping", payload) || Objects.equals("pong", payload)) {


        } else {
            //
            TransferDTO transferDTO = JSON.parseObject(payload, TransferDTO.class);
            System.out.println("连接");
            transferDTO.setNpcUid((String)webSocketSession.getAttributes().get("npc_uid"));
            ExecuteService executeService = new ExecuteService(npcConfig, operateBean);
            EXECUTOR.execute(() -> executeService.execCMD(transferDTO, webSocketSession, npcConfig.getConfPath()));
        }
    }
}
