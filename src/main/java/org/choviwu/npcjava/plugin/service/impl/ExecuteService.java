package org.choviwu.npcjava.plugin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.App;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.constant.ConnectInfo;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.utils.TemplateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

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
            boolean fill = TemplateUtils.fill(transferDTO, confPath);
            if (!fill) {
                log.error("模板填写异常");
                webSocketSession.sendMessage(new TextMessage("fail"));
                return;
            }
            restart(webSocketSession);
        } catch (IOException e) {
            log.info("执行cmd命令失败", e);
        }
    }

    public void execCancelCMD(WebSocketSession webSocketSession, String confPath) {
        try {
            String npc_uid = (String) webSocketSession.getAttributes().get("npc_uid");
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

    private void stop(String uid) throws IOException {
        ExecuteService executeService = ConnectInfo.CONNECT_MAP.get(uid);

        if (executeService!=null) {
            executeService.process.destroy();
            synchronized (this) {
                ConnectInfo.CONNECT_MAP.remove(uid);
            }
        }
    }

    private void restart(WebSocketSession webSocketSession) throws IOException {
        String npc_uid = (String) webSocketSession.getAttributes().get("npc_uid");
        StopWatch stopWatch = new StopWatch();
        log.info("准备执行stop...");
        stop(npc_uid);
        log.info("stop执行成功...{}", stopWatch.toString());
        log.info("准备执行启动...");
        process = Runtime.getRuntime().exec(operateBean + " -config=" + npcConfig.getConfPath());
        log.info("启动执行成功...{}", stopWatch.toString());
        ConnectInfo.CONNECT_MAP.put(npc_uid, this);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));){

            char[] arr = new char[2048];
            while (true) {
                int index = bufferedReader.read(arr);
                String readLine = new String(arr);
                log.info("print out: {}",readLine);
                if (index == -1) {
                    break;
                }
                webSocketSession.sendMessage(new TextMessage(readLine));
            }
        }

    }
}
