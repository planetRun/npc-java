package org.choviwu.npcjava.plugin.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.choviwu.npcjava.plugin.constant.Constant;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.choviwu.npcjava.plugin.exception.NpcException;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
public class TemplateUtils {

    private static final String NPC_TEMPLATE;

    private static final String BODY_TEMPLATE;

    private static StringBuffer bufferTemplate = new StringBuffer();

    static PropertyPlaceholderHelper propertyPlaceholderHelper = new PropertyPlaceholderHelper("${", "}", ":", true);

    static {
        try {
            NPC_TEMPLATE = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/npc.conf.template"));
            BODY_TEMPLATE = IOUtils.toString(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf/body.conf.template"));

        } catch (IOException e) {
            throw new RuntimeException("load template error", e);
        }

    }

    public static void init(String config) {

    }

    public static boolean fill(TransferDTO transferDTO, String confPath) {
        try {
            boolean present = Constant.TRANSFER_ONLINE_LIST.stream().anyMatch(c ->
                    (c.getRemoteAddress().equals(transferDTO.getRemoteAddress()) && c.getTargetUrl().equals(transferDTO.getTargetUrl())));
            if (present) {
                throw new NpcException(20001, "不能重复连接改域名");
            }
            Map<String, String> describe = BeanUtils.describe(transferDTO);
            Properties properties = new Properties();
            properties.putAll(describe);
            if (bufferTemplate.length() <= 0) {
                String commonText = propertyPlaceholderHelper.replacePlaceholders(NPC_TEMPLATE, properties);
                bufferTemplate.append(commonText);
            }
            // 追加body 模板数据
            String replacePlaceholders = propertyPlaceholderHelper.replacePlaceholders(BODY_TEMPLATE, properties);
            bufferTemplate.append(replacePlaceholders);

            IOUtils.write(bufferTemplate.toString().getBytes(StandardCharsets.UTF_8), new FileOutputStream(confPath));
            Constant.TRANSFER_ONLINE_LIST.add(transferDTO);
            return true;
        } catch (NpcException e) {
            log.error("", e);
            throw e;
        } catch (Exception e) {
            log.error("", e);
            throw new NpcException(500, "未知异常");
        }
    }

    public static boolean cancelNpc(String webUid, String confPath) {
        try {
            List<TransferDTO> transferOnlineList = Constant.TRANSFER_ONLINE_LIST;
            Optional<TransferDTO> transferOpt = transferOnlineList.stream().filter(c -> Objects.equals(c.getNpcUid(), webUid)).findAny();
            if (!transferOpt.isPresent()) {
                return false;
            }
            TransferDTO transferDTO = transferOpt.get();
            transferOnlineList.remove(transferDTO);
            Map<String, String> describe = BeanUtils.describe(transferDTO);
            Properties properties = new Properties();
            properties.putAll(describe);
            // 先删后插
            for (TransferDTO dto : transferOnlineList) {
                fill(dto, confPath);
            }
            return !transferOnlineList.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
