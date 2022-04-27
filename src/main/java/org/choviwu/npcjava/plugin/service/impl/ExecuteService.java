package org.choviwu.npcjava.plugin.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.App;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
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
@Component
public class ExecuteService implements InitializingBean {


    @Autowired
    private NpcConfig npcConfig;

    @Autowired
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
            boolean flag = TemplateUtils.cancelNpc((String) webSocketSession.getAttributes().get("npc_uid"), confPath);
            if (flag) {
                restart(webSocketSession);
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

    private void stop() throws IOException {
        Runtime.getRuntime().exec(operateBean + " stop");
    }

    private void restart(WebSocketSession webSocketSession) throws IOException {
        StopWatch stopWatch = new StopWatch();
        log.info("准备执行stop...");
        stop();
        log.info("stop执行成功...{}", stopWatch.toString());
        log.info("准备执行启动...");
        process = Runtime.getRuntime().exec(operateBean + " -config=" + npcConfig.getConfPath());
        log.info("启动执行成功...{}", stopWatch.toString());

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
