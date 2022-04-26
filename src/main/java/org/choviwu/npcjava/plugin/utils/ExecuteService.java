package org.choviwu.npcjava.plugin.utils;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Component
public class ExecuteService {


    @Autowired
    private NpcConfig npcConfig;

    private Process process;


    //    执行CMD
//执行cmd命令，获取返回结果
    public void execCMD(TransferDTO transferDTO, WebSocketSession webSocketSession, String confPath) {
        StringBuilder sb = new StringBuilder();
        try {
            boolean fill = TemplateUtils.fill(transferDTO, confPath);
            if (!fill) {
                log.error("模板填写异常");
                return;
            }
            StopWatch stopWatch = new StopWatch();
            // -config=${npc.confPath}
            log.info("准备执行stop...");
            Runtime.getRuntime().exec(npcConfig.getExePath() + " stop");
            log.info("stop执行成功...{}", stopWatch.toString());
            log.info("准备执行启动...");
            process = Runtime.getRuntime().exec(npcConfig.getExePath() + " -config=" + confPath);
            log.info("启动执行成功...{}", stopWatch.toString());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line + "\n");
                log.info(sb.toString());
            }
            bufferedReader.close();
            webSocketSession.sendMessage(new TextMessage(sb));
        } catch (IOException e) {
            log.info("执行cmd命令失败", e);
        }
    }

}
