package com.pdb.web.entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

  private Long userId;

  private String nickName;

  private String gender;

  private String iconImgUrl;

  private String certName;

  private String certCard;

  private String deviceId;

  private Long referrerUserId;

  private String createdSource;

  private String withdrawPassword;

  private Date createdAt;

  private Date updatedAt;

  private static final long serialVersionUID = 1L;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getIconImgUrl() {
    return iconImgUrl;
  }

  public void setIconImgUrl(String iconImgUrl) {
    this.iconImgUrl = iconImgUrl;
  }

  public String getCertName() {
    return certName;
  }

  public void setCertName(String certName) {
    this.certName = certName;
  }

  public String getCertCard() {
    return certCard;
  }

  public void setCertCard(String certCard) {
    this.certCard = certCard;
  }

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Long getReferrerUserId() {
    return referrerUserId;
  }

  public void setReferrerUserId(Long referrerUserId) {
    this.referrerUserId = referrerUserId;
  }

  public String getCreatedSource() {
    return createdSource;
  }

  public void setCreatedSource(String createdSource) {
    this.createdSource = createdSource;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getWithdrawPassword() {
    return withdrawPassword;
  }

  public void setWithdrawPassword(String withdrawPassword) {
    this.withdrawPassword = withdrawPassword;
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
    User other = (User) that;
    return (this.getUserId() == null ? other.getUserId() == null
        : this.getUserId().equals(other.getUserId()))
        && (this.getNickName() == null ? other.getNickName() == null
        : this.getNickName().equals(other.getNickName()))
        && (this.getGender() == null ? other.getGender() == null
        : this.getGender().equals(other.getGender()))
        && (this.getIconImgUrl() == null ? other.getIconImgUrl() == null
        : this.getIconImgUrl().equals(other.getIconImgUrl()))
        && (this.getCertName() == null ? other.getCertName() == null
        : this.getCertName().equals(other.getCertName()))
        && (this.getCertCard() == null ? other.getCertCard() == null
        : this.getCertCard().equals(other.getCertCard()))
        && (this.getDeviceId() == null ? other.getDeviceId() == null
        : this.getDeviceId().equals(other.getDeviceId()))
        && (this.getReferrerUserId() == null ? other.getReferrerUserId() == null
        : this.getReferrerUserId().equals(other.getReferrerUserId()))
        && (this.getCreatedSource() == null ? other.getCreatedSource() == null
        : this.getCreatedSource().equals(other.getCreatedSource()))
        && (this.getWithdrawPassword() == null ? other.getWithdrawPassword() == null
        : this.getWithdrawPassword().equals(other.getWithdrawPassword()))
        && (this.getCreatedAt() == null ? other.getCreatedAt() == null
        : this.getCreatedAt().equals(other.getCreatedAt()))
        && (this.getUpdatedAt() == null ? other.getUpdatedAt() == null
        : this.getUpdatedAt().equals(other.getUpdatedAt()));
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
    result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
    result = prime * result + ((getGender() == null) ? 0 : getGender().hashCode());
    result = prime * result + ((getIconImgUrl() == null) ? 0 : getIconImgUrl().hashCode());
    result = prime * result + ((getCertName() == null) ? 0 : getCertName().hashCode());
    result = prime * result + ((getCertCard() == null) ? 0 : getCertCard().hashCode());
    result = prime * result + ((getDeviceId() == null) ? 0 : getDeviceId().hashCode());
    result = prime * result + ((getReferrerUserId() == null) ? 0 : getReferrerUserId().hashCode());
    result = prime * result + ((getCreatedSource() == null) ? 0 : getCreatedSource().hashCode());
    result =
        prime * result + ((getWithdrawPassword() == null) ? 0 : getWithdrawPassword().hashCode());
    result = prime * result + ((getCreatedAt() == null) ? 0 : getCreatedAt().hashCode());
    result = prime * result + ((getUpdatedAt() == null) ? 0 : getUpdatedAt().hashCode());
    return result;
  }
}
