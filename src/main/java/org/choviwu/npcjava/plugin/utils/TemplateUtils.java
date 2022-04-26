package org.choviwu.npcjava.plugin.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;
import org.choviwu.npcjava.plugin.domain.dto.TransferDTO;
import org.springframework.util.PropertyPlaceholderHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;

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

    public static void init() {
    }

    public static boolean fill(TransferDTO transferDTO, String confPath) {
        try {
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
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}