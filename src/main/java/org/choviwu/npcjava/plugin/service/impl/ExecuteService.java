package org.choviwu.npcjava.plugin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.constant.Constant;
import org.choviwu.npcjava.plugin.data.Message;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.exception.NpcException;
import org.choviwu.npcjava.plugin.utils.TemplateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StopWatch;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
public class ExecuteService implements InitializingBean {


    public ExecuteService(NpcConfig npcConfig, String operateBean) {
        this.npcConfig = npcConfig;
        this.operateBean = operateBean;
    }

    private NpcConfig npcConfig;

    private String operateBean;

    private Process process;

    public void execCMD(TransferDTO transferDTO, WebSocketSession webSocketSession, String confPath) {
        try {
            try {
                TemplateUtils.fill(transferDTO, confPath);
            } catch (NpcException e) {
                webSocketSession.sendMessage(new TextMessage(Message.getMessage(e)));
                webSocketSession.close(CloseStatus.BAD_DATA);
                return;
            }

            restart(webSocketSession);
        } catch (IOException e) {
            log.info("执行cmd命令失败", e);
        }
    }

    public void execCancelCMD(WebSocketSession webSocketSession, String confPath) {
        try {
            String npc_uid = (String) webSocketSession.getAttributes().get(Constant.NPC_UID_NAME);
            boolean flag = TemplateUtils.cancelNpc(npc_uid, confPath);
            if (flag) {
                restart(webSocketSession);
            } else {
                stop(npc_uid);
            }
        } catch (IOException e) {
            log.info("执行cmd命令失败", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            TemplateUtils.init(npcConfig.getConfPath());
        }catch (Exception e) {}
    }

    public void stop(String uid) throws IOException {
        ExecuteService executeService = Constant.CONNECT_MAP.get(uid);

        if (executeService!=null) {
            executeService.process.destroy();
            synchronized (this) {
                Constant.CONNECT_MAP.remove(uid);
            }
        }
    }

    private void restart(WebSocketSession webSocketSession) throws IOException {
        String npc_uid = (String) webSocketSession.getAttributes().get(Constant.NPC_UID_NAME);
        StopWatch stopWatch = new StopWatch();
        log.info("准备执行stop...");
        stop(npc_uid);
        log.info("stop执行成功...{}", stopWatch.toString());
        log.info("准备执行启动...");
        process = Runtime.getRuntime().exec(operateBean + " -config=" + npcConfig.getConfPath());
        log.info("启动执行成功...{}", stopWatch.toString());
        Constant.CONNECT_MAP.put(npc_uid, this);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));){

            char[] arr = new char[2048];
            while (true) {
                int index = bufferedReader.read(arr);
                String readLine = new String(arr);
                log.info("print out: {}",readLine);
                if (index == -1) {
                    break;
                }
                webSocketSession.sendMessage(new TextMessage(Message.getMessage(Constant.MESSAGE, Constant.SUCCESS_CODE, readLine)));
            }
        }

    }
}
