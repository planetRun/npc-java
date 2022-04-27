package org.choviwu.npcjava.plugin.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_client
 */
@Data
public class TClient implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 公网域名+端口
     */
    private String targetUrl;

    /**
     * 本地域名+端口
     */
    private String localUrl;

    /**
     * 
     */
    private String clientVkey;

    /**
     * 
     */
    private String clientBasicName;

    /**
     * 
     */
    private String clientBasicPassword;

    /**
     * 
     */
    private String serverAddr;

    /**
     * 
     */
    private String connType;

    /**
     *
     */
    private String npcUid;

    /**
     * 
     */
    private Integer transType;

    /**
     * 2
     */
    private Integer status;

    /**
     * 
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 公网域名+端口
     */
    public String getTargetUrl() {
        return targetUrl;
    }

    /**
     * 公网域名+端口
     */
    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    /**
     * 
     */
    public String getClientVkey() {
        return clientVkey;
    }

    /**
     * 
     */
    public void setClientVkey(String clientVkey) {
        this.clientVkey = clientVkey;
    }

    /**
     * 
     */
    public String getClientBasicName() {
        return clientBasicName;
    }

    /**
     * 
     */
    public void setClientBasicName(String clientBasicName) {
        this.clientBasicName = clientBasicName;
    }

    /**
     * 
     */
    public String getClientBasicPassword() {
        return clientBasicPassword;
    }

    /**
     * 
     */
    public void setClientBasicPassword(String clientBasicPassword) {
        this.clientBasicPassword = clientBasicPassword;
    }

    /**
     * 
     */
    public String getServerAddr() {
        return serverAddr;
    }

    /**
     * 
     */
    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    /**
     * 
     */
    public String getConnType() {
        return connType;
    }

    /**
     * 
     */
    public void setConnType(String connType) {
        this.connType = connType;
    }

    /**
     * 
     */
    public Integer getTransType() {
        return transType;
    }

    /**
     * 
     */
    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    /**
     * 2
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 2
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        TClient other = (TClient) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTargetUrl() == null ? other.getTargetUrl() == null : this.getTargetUrl().equals(other.getTargetUrl()))
            && (this.getClientVkey() == null ? other.getClientVkey() == null : this.getClientVkey().equals(other.getClientVkey()))
            && (this.getClientBasicName() == null ? other.getClientBasicName() == null : this.getClientBasicName().equals(other.getClientBasicName()))
            && (this.getClientBasicPassword() == null ? other.getClientBasicPassword() == null : this.getClientBasicPassword().equals(other.getClientBasicPassword()))
            && (this.getServerAddr() == null ? other.getServerAddr() == null : this.getServerAddr().equals(other.getServerAddr()))
            && (this.getConnType() == null ? other.getConnType() == null : this.getConnType().equals(other.getConnType()))
            && (this.getTransType() == null ? other.getTransType() == null : this.getTransType().equals(other.getTransType()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTargetUrl() == null) ? 0 : getTargetUrl().hashCode());
        result = prime * result + ((getClientVkey() == null) ? 0 : getClientVkey().hashCode());
        result = prime * result + ((getClientBasicName() == null) ? 0 : getClientBasicName().hashCode());
        result = prime * result + ((getClientBasicPassword() == null) ? 0 : getClientBasicPassword().hashCode());
        result = prime * result + ((getServerAddr() == null) ? 0 : getServerAddr().hashCode());
        result = prime * result + ((getConnType() == null) ? 0 : getConnType().hashCode());
        result = prime * result + ((getTransType() == null) ? 0 : getTransType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", targetUrl=").append(targetUrl);
        sb.append(", clientVkey=").append(clientVkey);
        sb.append(", clientBasicName=").append(clientBasicName);
        sb.append(", clientBasicPassword=").append(clientBasicPassword);
        sb.append(", serverAddr=").append(serverAddr);
        sb.append(", connType=").append(connType);
        sb.append(", transType=").append(transType);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}