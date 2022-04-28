package org.choviwu.npcjava.plugin.socket;

import lombok.extern.slf4j.Slf4j;
import org.choviwu.npcjava.plugin.conf.NpcConfig;
import org.choviwu.npcjava.plugin.constant.Constant;
import org.choviwu.npcjava.plugin.domain.TClient;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.mapper.TClientMapper;
import org.choviwu.npcjava.plugin.service.impl.SocketService;
import org.choviwu.npcjava.plugin.utils.TemplateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class NpcSocketHandler implements WebSocketHandler {

    private static final Map<String, Object> map = new ConcurrentHashMap<>();

    @Autowired
    private SocketService socketService;

    @Autowired
    private TClientMapper tClientMapper;

    @Autowired
    private NpcConfig npcConfig;

    /**
     * 用户连接上WebSocket的回调
     *
     * @param webSocketSession
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String npcUid = (String) webSocketSession.getAttributes().get(Constant.NPC_UID_NAME);
        if (map.size() > npcConfig.getConnectSize()) {
            log.info("超过最大连接数, 关闭");
            webSocketSession.close();
        }
        map.put(npcUid, "");
    }

    /**
     * 收到消息的回调
     *
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        if (webSocketMessage instanceof TextMessage) {
            log.info("用户:{},发送命令:{}", webSocketSession.getAttributes().get(Constant.NPC_UID_NAME), webSocketMessage.toString());
            // 两种消息一种是ping/pong 一种是连接
            socketService.recvHandle(((TextMessage) webSocketMessage).getPayload(), webSocketSession);
        } else if (webSocketMessage instanceof BinaryMessage) {

        } else if (webSocketMessage instanceof PongMessage) {

        } else {
            System.out.println("Unexpected WebSocket message type: " + webSocketMessage);
        }
    }

    /**
     * 出现错误的回调
     *
     * @param webSocketSession
     * @param throwable
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.warn("发生错误", throwable);
    }

    /**
     * 连接关闭的回调
     *
     * @param webSocketSession
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        close(webSocketSession);
    }

    private void close(WebSocketSession webSocketSession) {
        String npcUid = (String) webSocketSession.getAttributes().get(Constant.NPC_UID_NAME);
        map.remove(npcUid);
        log.info("{}已退出", npcUid);
        TClient tClient = tClientMapper.query(npcUid);
        if (Objects.nonNull(tClient)) {
            //移除
            TransferDTO transferDTO = new TransferDTO();
            transferDTO.setTargetUrl(tClient.getTargetUrl());
            transferDTO.setRemoteAddress(tClient.getServerAddr());
            transferDTO.setNpcUid(tClient.getNpcUid());
            Constant.TRANSFER_ONLINE_LIST.remove(transferDTO);
            tClientMapper.updateById(2, tClient.getId());
        }
        TemplateUtils.cancelNpc(npcUid, npcConfig.getConfPath());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
