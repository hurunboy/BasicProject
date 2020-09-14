package com.pdb.web.entity.bo;

/**
 * @Description ：
 * @Tauthor ZhangZaipeng
 * @Tdata 2020/6/6   11:55
 */
public class UserWeiXinLoginBo {
  private String openId ;
  private String unionId ;
  private String avatar ;
  private int sex ;
  private String nickName ;

  public UserWeiXinLoginBo() {
  }

  public UserWeiXinLoginBo(String openId, String unionId, String avatar, int sex,
      String nickName) {
    this.openId = openId;
    this.unionId = unionId;
    this.avatar = avatar;
    this.sex = sex;
    this.nickName = nickName;
  }

  public String getOpenId() {
    return openId;
  }

  public void setOpenId(String openId) {
    this.openId = openId;
  }

  public String getUnionId() {
    return unionId;
  }

  public void setUnionId(String unionId) {
    this.unionId = unionId;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public int getSex() {
    return sex;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }
}
