package com.mcp.lottery.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_rule")
public class UserRule {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uid;

    private String game;

    private Integer isOpen;

    private String term;

    private Double initMoney;

    private Date createAt;

    private Date updateAt;

    private Integer isDividing;

    private Double limitLose;

    private Double limitWin;

    private Double dividingTime;

    private Integer isTraceLose;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Double getInitMoney() {
        return initMoney;
    }

    public void setInitMoney(Double initMoney) {
        this.initMoney = initMoney;
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

    public Integer getIsDividing() {
        return isDividing;
    }

    public void setIsDividing(Integer isDividing) {
        this.isDividing = isDividing;
    }

    public Double getLimitLose() {
        return limitLose;
    }

    public void setLimitLose(Double limitLose) {
        this.limitLose = limitLose;
    }

    public Double getLimitWin() {
        return limitWin;
    }

    public void setLimitWin(Double limitWin) {
        this.limitWin = limitWin;
    }

    public Double getDividingTime() {
        return dividingTime;
    }

    public void setDividingTime(Double dividingTime) {
        this.dividingTime = dividingTime;
    }

    public Integer getIsTraceLose() {
        return isTraceLose;
    }

    public void setIsTraceLose(Integer isTraceLose) {
        this.isTraceLose = isTraceLose;
    }
}
