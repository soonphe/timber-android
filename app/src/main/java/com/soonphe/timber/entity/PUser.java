package com.soonphe.timber.entity;

import java.io.Serializable;

/**
 * @Author anna
 * @Date 2017-12-06 15:09
 * @Description 用户表
 */
public class PUser implements Serializable {


    /**
     * id : 206
     * userCode : 1508302196692000
     * name : 咕噜
     * password : null
     * sex : 0
     * email : null
     * phone : 15927393643
     * tel : null
     * address :
     * token : 1149429327
     * headPic : http://q.qlogo.cn/qqapp/1106114370/0D0523A909EE17B08E023004F636187C/100
     * type : 0
     * sinaId :
     * wxId :
     * qqId : 0D0523A909EE17B08E023004F636187C
     * state : 0
     * createTime : 2017-10-18 12:49:56
     * updateTime : 2017-10-18 12:49:56
     * delFlag : 0
     * qqAccount : null
     * wxAccount : null
     * sinaAccount : null
     * aliPayAccount : null
     * certifyState : 0
     * hasStore : null
     */

    private int id;
    private String userCode;
    private String name;
    private String password;
    private int sex;
    private String email;
    private String phone;
    private String tel;
    private String address;
    private String token;
    private String headPic;
    private int type;
    private String uid;
    private String sinaId;
    private String wxId;
    private String qqId;
    private int state;
    private String createTime;
    private String updateTime;
    private int delFlag;
    private String qqAccount;
    private String wxAccount;
    private String sinaAccount;
    private String aliPayAccount;
    private int certifyState;
    private int hasStore;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSinaId() {
        return sinaId;
    }

    public void setSinaId(String sinaId) {
        this.sinaId = sinaId;
    }

    public String getWxId() {
        return wxId;
    }

    public void setWxId(String wxId) {
        this.wxId = wxId;
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    public String getQqAccount() {
        return qqAccount;
    }

    public void setQqAccount(String qqAccount) {
        this.qqAccount = qqAccount;
    }

    public String getWxAccount() {
        return wxAccount;
    }

    public void setWxAccount(String wxAccount) {
        this.wxAccount = wxAccount;
    }

    public String getSinaAccount() {
        return sinaAccount;
    }

    public void setSinaAccount(String sinaAccount) {
        this.sinaAccount = sinaAccount;
    }

    public String getAliPayAccount() {
        return aliPayAccount;
    }

    public void setAliPayAccount(String aliPayAccount) {
        this.aliPayAccount = aliPayAccount;
    }

    public int getCertifyState() {
        return certifyState;
    }

    public void setCertifyState(int certifyState) {
        this.certifyState = certifyState;
    }

    public int getHasStore() {
        return hasStore;
    }

    public void setHasStore(int hasStore) {
        this.hasStore = hasStore;
    }
}
