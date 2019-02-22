package com.mcp.lottery.model;



import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table
public class Plat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String username;

    private String password;

    private String loginUrl;

    private String balanceUrl;

    private String touzhuUrl;

    private String cookies;

    private String assist;

    private Long categoryId;

    private Date createAt;

    private Date updateAt;

    private Date deleteAt;

    @Transient
    private PlatCategory platCategory;

    @Transient
    private List<PlatGame> platGameList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getBalanceUrl() {
        return balanceUrl;
    }

    public void setBalanceUrl(String balanceUrl) {
        this.balanceUrl = balanceUrl;
    }

    public String getTouzhuUrl() {
        return touzhuUrl;
    }

    public void setTouzhuUrl(String touzhuUrl) {
        this.touzhuUrl = touzhuUrl;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }

    public String getAssist() {
        return assist;
    }

    public void setAssist(String assist) {
        this.assist = assist;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    public PlatCategory getPlatCategory() {
        return platCategory;
    }

    public void setPlatCategory(PlatCategory platCategory) {
        this.platCategory = platCategory;
    }

    public List<PlatGame> getPlatGameList() {
        return platGameList;
    }

    public void setPlatGameList(List<PlatGame> platGameList) {
        this.platGameList = platGameList;
    }
}
