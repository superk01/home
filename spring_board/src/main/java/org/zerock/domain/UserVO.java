package org.zerock.domain;

public class UserVO {

  private String urd;
  private String upw;
  private String uname;
  private int upoint;

  public String getUrd() {
    return urd;
  }

  public void setUrd(String urd) {
    this.urd = urd;
  }

  public String getUpw() {
    return upw;
  }

  public void setUpw(String upw) {
    this.upw = upw;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public int getUpoint() {
    return upoint;
  }

  public void setUpoint(int upoint) {
    this.upoint = upoint;
  }

  @Override
  public String toString() {
    return "UserVO [urd=" + urd + ", upw=" + upw + ", uname=" + uname + ", upoint=" + upoint + "]";
  }
}
