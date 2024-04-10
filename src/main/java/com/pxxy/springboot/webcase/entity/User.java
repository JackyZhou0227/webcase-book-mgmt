package com.pxxy.springboot.webcase.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/******************
 * @Author yejf
 * @Description TODO
 */
public class User {

    private Integer id;

    private String userName;  //登录用户名,唯一且非空

    private String password;  //登录密码

    private String mobilePhone; //手机号，唯一且非空

    private UserStatus status  = UserStatus.正常; //用户状态

    private String realName = "XXXX"; //真实姓名

    private LocalDateTime createDate = LocalDateTime.now();  //创建时间

    private LocalDateTime updateDate; //更新时间

    private LocalDateTime lastLoginDate;  //最后一次登录时间

    private String avatarUrl;  //头像地址

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", status=").append(status);
        sb.append(", realName='").append(realName).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", mobilePhone=").append(mobilePhone);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", lastLoginDate=").append(lastLoginDate);
        sb.append(", avatarUrl='").append(avatarUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

}
