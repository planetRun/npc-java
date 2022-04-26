package org.choviwu.npcjava.plugin.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName t_options
 */
public class TOptions implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private String property;

    /**
     * 
     */
    private String options;

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
     * 
     */
    public String getProperty() {
        return property;
    }

    /**
     * 
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 
     */
    public String getOptions() {
        return options;
    }

    /**
     * 
     */
    public void setOptions(String options) {
        this.options = options;
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
        TOptions other = (TOptions) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getProperty() == null ? other.getProperty() == null : this.getProperty().equals(other.getProperty()))
            && (this.getOptions() == null ? other.getOptions() == null : this.getOptions().equals(other.getOptions()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getProperty() == null) ? 0 : getProperty().hashCode());
        result = prime * result + ((getOptions() == null) ? 0 : getOptions().hashCode());
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
        sb.append(", property=").append(property);
        sb.append(", options=").append(options);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}