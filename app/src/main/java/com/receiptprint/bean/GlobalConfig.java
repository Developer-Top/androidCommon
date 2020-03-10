package com.receiptprint.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * 全局配置
 * Created by linlingrong on 2015-09-16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalConfig implements Serializable {
    private String id;

    private String prisonId;

    private String nginxAddress;

    private String nginxLocalAddress;

    private String nginxPubAddress;

    private int nginxPort;

    private String ftpAddress;

    private String ftpLocalAddress;

    private String ftpPubAddress;

    private String ftpUserName;

    private String ftpPasswd;

    private String ftpPort;

    private String mqAddress;

    private String mqLocalAddress;

    private String mqPubAddress;

    private String mqUserName;

    private String mqPasswd;

    private String mqVirtualHost;

    private int mqPort;

    private int offScreenTime;

    private int shiftIntervalTime;// 值班换班刷卡间隔

    private int keepAliveTime;// 保活时间

    private int keepAlivePort = 8580;// 保活socket发送端口

    private double shoppingPaulAtTheEndCredits;// 用户购物保底额度，本月可用额度低于这个值就不能买东西。

    private String limitedUseStartTime;

    private String limitedUseEndTime;

    private String dormCode;

    private int rollcallLimitTime;

    private String talkServerAddress;

    private String talkServerPubAddress;

    private String talkServerLocalAddress;

    private int talkServerPort;

    private boolean hasSubControl;

    private String subControlOnWorkTime;

    private String subControlOffWorkTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrisonId() {
        return prisonId;
    }

    public void setPrisonId(String prisonId) {
        this.prisonId = prisonId;
    }

    public String getNginxAddress() {
        return nginxAddress;
    }

    public void setNginxAddress(String nginxAddress) {
        this.nginxAddress = nginxAddress;
    }

    public String getNginxLocalAddress() {
        return nginxLocalAddress;
    }

    public void setNginxLocalAddress(String nginxLocalAddress) {
        this.nginxLocalAddress = nginxLocalAddress;
    }

    public String getNginxPubAddress() {
        return nginxPubAddress;
    }

    public void setNginxPubAddress(String nginxPubAddress) {
        this.nginxPubAddress = nginxPubAddress;
    }

    public int getNginxPort() {
        return nginxPort;
    }

    public void setNginxPort(int nginxPort) {
        this.nginxPort = nginxPort;
    }

    public String getFtpAddress() {
        return ftpAddress;
    }

    public void setFtpAddress(String ftpAddress) {
        this.ftpAddress = ftpAddress;
    }

    public String getFtpLocalAddress() {
        return ftpLocalAddress;
    }

    public void setFtpLocalAddress(String ftpLocalAddress) {
        this.ftpLocalAddress = ftpLocalAddress;
    }

    public String getFtpPubAddress() {
        return ftpPubAddress;
    }

    public void setFtpPubAddress(String ftpPubAddress) {
        this.ftpPubAddress = ftpPubAddress;
    }

    public String getFtpUserName() {
        return ftpUserName;
    }

    public void setFtpUserName(String ftpUserName) {
        this.ftpUserName = ftpUserName;
    }

    public String getFtpPasswd() {
        return ftpPasswd;
    }

    public void setFtpPasswd(String ftpPasswd) {
        this.ftpPasswd = ftpPasswd;
    }

    public String getFtpPort() {
        return ftpPort;
    }

    public void setFtpPort(String ftpPort) {
        this.ftpPort = ftpPort;
    }

    public String getMqAddress() {
        return mqAddress;
    }

    public void setMqAddress(String mqAddress) {
        this.mqAddress = mqAddress;
    }

    public String getMqLocalAddress() {
        return mqLocalAddress;
    }

    public void setMqLocalAddress(String mqLocalAddress) {
        this.mqLocalAddress = mqLocalAddress;
    }

    public String getMqPubAddress() {
        return mqPubAddress;
    }

    public void setMqPubAddress(String mqPubAddress) {
        this.mqPubAddress = mqPubAddress;
    }

    public String getMqUserName() {
        return mqUserName;
    }

    public void setMqUserName(String mqUserName) {
        this.mqUserName = mqUserName;
    }

    public String getMqPasswd() {
        return mqPasswd;
    }

    public void setMqPasswd(String mqPasswd) {
        this.mqPasswd = mqPasswd;
    }

    public int getMqPort() {
        return mqPort;
    }

    public void setMqPort(int mqPort) {
        this.mqPort = mqPort;
    }

    public int getOffScreenTime() {
        return offScreenTime;
    }

    public void setOffScreenTime(int offScreenTime) {
        this.offScreenTime = offScreenTime;
    }

    public int getShiftIntervalTime() {
        return shiftIntervalTime;
    }

    public void setShiftIntervalTime(int shiftIntervalTime) {
        this.shiftIntervalTime = shiftIntervalTime;
    }

    public int getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(int keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getKeepAlivePort() {
        return keepAlivePort;
    }

    public void setKeepAlivePort(int keepAlivePort) {
        this.keepAlivePort = keepAlivePort;
    }

    public double getShoppingPaulAtTheEndCredits() {
        return shoppingPaulAtTheEndCredits;
    }

    public void setShoppingPaulAtTheEndCredits(double shoppingPaulAtTheEndCredits) {
        this.shoppingPaulAtTheEndCredits = shoppingPaulAtTheEndCredits;
    }

    public String getLimitedUseStartTime() {
        return limitedUseStartTime;
    }

    public void setLimitedUseStartTime(String limitedUseStartTime) {
        this.limitedUseStartTime = limitedUseStartTime;
    }

    public String getLimitedUseEndTime() {
        return limitedUseEndTime;
    }

    public void setLimitedUseEndTime(String limitedUseEndTime) {
        this.limitedUseEndTime = limitedUseEndTime;
    }

    public String getDormCode() {
        return dormCode;
    }

    public void setDormCode(String dormCode) {
        this.dormCode = dormCode;
    }

    public int getRollcallLimitTime() {
        return rollcallLimitTime;
    }

    public void setRollcallLimitTime(int rollcallLimitTime) {
        this.rollcallLimitTime = rollcallLimitTime;
    }

    public String getTalkServerAddress() {
        return talkServerAddress;
    }

    public void setTalkServerAddress(String talkServerAddress) {
        this.talkServerAddress = talkServerAddress;
    }

    public String getTalkServerPubAddress() {
        return talkServerPubAddress;
    }

    public void setTalkServerPubAddress(String talkServerPubAddress) {
        this.talkServerPubAddress = talkServerPubAddress;
    }

    public String getTalkServerLocalAddress() {
        return talkServerLocalAddress;
    }

    public void setTalkServerLocalAddress(String talkServerLocalAddress) {
        this.talkServerLocalAddress = talkServerLocalAddress;
    }

    public int getTalkServerPort() {
        return talkServerPort;
    }

    public void setTalkServerPort(int talkServerPort) {
        this.talkServerPort = talkServerPort;
    }

    public boolean isHasSubControl() {
        return hasSubControl;
    }

    public void setHasSubControl(boolean hasSubControl) {
        this.hasSubControl = hasSubControl;
    }

    public String getSubControlOnWorkTime() {
        return subControlOnWorkTime;
    }

    public void setSubControlOnWorkTime(String subControlOnWorkTime) {
        this.subControlOnWorkTime = subControlOnWorkTime;
    }

    public String getSubControlOffWorkTime() {
        return subControlOffWorkTime;
    }

    public void setSubControlOffWorkTime(String subControlOffWorkTime) {
        this.subControlOffWorkTime = subControlOffWorkTime;
    }

    public String getMqVirtualHost() {
        return mqVirtualHost;
    }

    public void setMqVirtualHost(String mqVirtualHost) {
        this.mqVirtualHost = mqVirtualHost;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "id='" + id + '\'' +
                ", prisonId='" + prisonId + '\'' +
                ", nginxAddress='" + nginxAddress + '\'' +
                ", nginxLocalAddress='" + nginxLocalAddress + '\'' +
                ", nginxPubAddress='" + nginxPubAddress + '\'' +
                ", nginxPort=" + nginxPort +
                ", ftpAddress='" + ftpAddress + '\'' +
                ", ftpLocalAddress='" + ftpLocalAddress + '\'' +
                ", ftpPubAddress='" + ftpPubAddress + '\'' +
                ", ftpUserName='" + ftpUserName + '\'' +
                ", ftpPasswd='" + ftpPasswd + '\'' +
                ", ftpPort='" + ftpPort + '\'' +
                ", mqAddress='" + mqAddress + '\'' +
                ", mqLocalAddress='" + mqLocalAddress + '\'' +
                ", mqPubAddress='" + mqPubAddress + '\'' +
                ", mqUserName='" + mqUserName + '\'' +
                ", mqPasswd='" + mqPasswd + '\'' +
                ", mqVirtualHost='" + mqVirtualHost + '\'' +
                ", mqPort=" + mqPort +
                ", offScreenTime=" + offScreenTime +
                ", shiftIntervalTime=" + shiftIntervalTime +
                ", keepAliveTime=" + keepAliveTime +
                ", keepAlivePort=" + keepAlivePort +
                ", shoppingPaulAtTheEndCredits=" + shoppingPaulAtTheEndCredits +
                ", limitedUseStartTime='" + limitedUseStartTime + '\'' +
                ", limitedUseEndTime='" + limitedUseEndTime + '\'' +
                ", dormCode='" + dormCode + '\'' +
                ", rollcallLimitTime=" + rollcallLimitTime +
                ", talkServerAddress='" + talkServerAddress + '\'' +
                ", talkServerPubAddress='" + talkServerPubAddress + '\'' +
                ", talkServerLocalAddress='" + talkServerLocalAddress + '\'' +
                ", talkServerPort=" + talkServerPort +
                ", hasSubControl=" + hasSubControl +
                ", subControlOnWorkTime='" + subControlOnWorkTime + '\'' +
                ", subControlOffWorkTime='" + subControlOffWorkTime + '\'' +
                '}';
    }
}
