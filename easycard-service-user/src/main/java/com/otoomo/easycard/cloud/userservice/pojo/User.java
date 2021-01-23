package com.otoomo.easycard.cloud.userservice.pojo;

import com.otoomo.commonbase.annotation.XGenField;
import com.otoomo.commonbase.annotation.XGenModel;
import com.otoomo.commonbase.generater.ComponentType;
import com.otoomo.commonbase.generater.Default;
import com.otoomo.commonbase.generater.EnumData;

import java.io.Serializable;
import java.util.Date;

@XGenModel(comment = "用户")
public class User implements Serializable {
    @XGenField(comment = "ID", key = true, uniqueKey = true)
    private Long id;
    @XGenField(comment = "用户编号", notNull = true, query = true)
    private Long userCode;
    @XGenField(comment = "用户名称", length = 40, notNull = true, query = true)
    private String userName;
    @XGenField(comment = "openid", length = 50, notNull = true)
    private String openid;
    @XGenField(comment = "头像", length = 200)
    private String headImgUrl;
    @XGenField(comment = "最后登录IP", length = 30)
    private String lastLoginIp;
    @XGenField(comment = "最后登录时间")
    private Date lastLoginTime;

    @XGenField(comment = "状态", notNull = true, length = 2,
            componentType = ComponentType.SELECT,
            query = true, def = Default.DEFAULT_INT_ONE,
            enumData = {
                    @EnumData(key = "0", value = "失效"),
                    @EnumData(key = "1", value = "可用")
            })
    private Integer status;
    @XGenField(comment = "删除状态", notNull = true, length = 2,
            componentType = ComponentType.SELECT,
            query = true, def = Default.DEFAULT_INT_ZERO,
            enumData = {
                    @EnumData(key = "0", value = "未删除"),
                    @EnumData(key = "1", value = "已删除")
            })
    private Integer delStatus;
    @XGenField(comment = "创建时间")
    private Long createTime;
    @XGenField(comment = "编辑时间")
    private Long editTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCode() {
        return userCode;
    }

    public void setUserCode(Long userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getEditTime() {
        return editTime;
    }

    public void setEditTime(Long editTime) {
        this.editTime = editTime;
    }
}
