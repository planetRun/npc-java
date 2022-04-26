package org.choviwu.npcjava.plugin.domain.dto;

import lombok.Data;

import java.io.Serializable;
@Data
public class TransferDTO implements Serializable {

    /**
     * 公网地址 xxx:8024
     */
    private String remoteAddress;

    private String npcUid;

    /**
     * 公网域名
     */
    private String targetUrl;

    /**
     * 公网端口
     */
    private String targetPort = "";

    /**
     * 内网地址
     */
    private String localAddress;


}
