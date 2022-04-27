package org.choviwu.npcjava.plugin.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.domain.TClient;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.mapper.TClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Slf4j
@Service
public class SocketService {

    @Autowired
    private NpcConfig npcConfig;

    @Autowired
    private String operateBean;

    @Autowired
    private TClientMapper tClientMapper;


    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(20, 20, 0L, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    // 1. ping/pong
    // 2. 连接
    public void recvHandle(String payload, WebSocketSession webSocketSession) {

        if (StringUtils.isEmpty(payload)) {
            return;
        }

        String npc_uid = (String) webSocketSession.getAttributes().get("npc_uid");
        log.info(">>>>>>>>>>>npcUid {}, data: {}", npc_uid, payload);
        if (Objects.equals("cancel", payload)) {
            ExecuteService executeService = new ExecuteService(npcConfig, operateBean);
            EXECUTOR.execute(() -> executeService.execCancelCMD(webSocketSession, npcConfig.getConfPath()));
            try {
                webSocketSession.close();
            } catch (IOException e) {
                log.error("io exception!", e);
            }
        }else if (Objects.equals("ping", payload) || Objects.equals("pong", payload)) {
            try {
                webSocketSession.sendMessage(new TextMessage("pong"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //
            TransferDTO transferDTO = JSON.parseObject(payload, TransferDTO.class);
            System.out.println("连接");
            transferDTO.setNpcUid((String)webSocketSession.getAttributes().get("npc_uid"));
            ExecuteService executeService = new ExecuteService(npcConfig, operateBean);
            EXECUTOR.execute(() -> executeService.execCMD(transferDTO, webSocketSession, npcConfig.getConfPath()));
            TClient tClient = new TClient();
            tClient.setClientVkey("1234");
            tClient.setClientBasicPassword("");
            tClient.setClientBasicName("");
            tClient.setNpcUid(npc_uid);
            tClient.setLocalUrl(transferDTO.getLocalAddress());
            tClient.setServerAddr(transferDTO.getRemoteAddress());
            tClient.setTargetUrl(transferDTO.getTargetUrl());
            tClient.setStatus(1);
            tClient.setConnType("tcp");
            tClient.setTransType(1);
            tClient.setCreateTime(new Date());
            tClientMapper.insert(tClient);
        }
    }
}
