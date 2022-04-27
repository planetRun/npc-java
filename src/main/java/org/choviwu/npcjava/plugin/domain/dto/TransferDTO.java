package org.choviwu.npcjava.plugin.domain.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class TransferDTO implements Serializable {

    /**
     * 公网地址 xxx:8024
     */
    private String remoteAddress = "";

    private String npcUid = "";

    /**
     * 公网域名
     */
    private String targetUrl = "";

    /**
     * 公网端口
     */
    private String targetPort = "";

    /**
     * 内网地址
     */
    private String localAddress = "";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferDTO that = (TransferDTO) o;
        return Objects.equals(remoteAddress, that.remoteAddress) && Objects.equals(targetUrl, that.targetUrl) && Objects.equals(localAddress, that.localAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remoteAddress, targetUrl, localAddress);
    }
}
