package com.mcp.lottery.model;


import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Table(name = "user_rule_timing")
public class UserRuleTiming {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long uid;

    private String game;

    private Integer begin;

    private Integer end;

    private Integer upPoint;



    private Integer originUpPoint;

    private Integer downPoint;



    private Integer originDownPoint;

    private Date createAt;

    public Integer getBullCountRatio() {
        return bullCountRatio;
    }

    public void setBullCountRatio(Integer bullCountRatio) {
        this.bullCountRatio = bullCountRatio;
    }

    private Integer bullCountRatio;

    private Integer isBullAxisMove;

    private Integer isTradeBeforeAxisMove;

    public Integer getIsBullDirectionOnly() {
        return isBullDirectionOnly;
    }

    public void setIsBullDirectionOnly(Integer isBullDirectionOnly) {
        this.isBullDirectionOnly = isBullDirectionOnly;
    }

    private Integer isBullDirectionOnly;

    @Transient
    private String startTime;

    @Transient
    private String endTime;

    private Integer direction;

    @Override
    public String toString() {
        return "UserRuleTiming{" +
                "id=" + id +
                ", uid=" + uid +
                ", game='" + game + '\'' +
                ", begin=" + begin +
                ", end=" + end +
                ", upPoint=" + upPoint +
                ", downPoint=" + downPoint +
                ", createAt=" + createAt +
                ", isBullAxisMove=" + isBullAxisMove +
                ", isTradeBeforeAxisMove=" + isTradeBeforeAxisMove +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", direction=" + direction +
                '}';
    }

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

    public Integer getBegin() {
        return begin;
    }

    public void setBegin(Integer begin) {
        this.begin = begin;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public Integer getUpPoint() {
        return upPoint;
    }

    public void setUpPoint(Integer upPoint) {
        this.upPoint = upPoint;
    }

    public Integer getOriginUpPoint() {
        return originUpPoint;
    }

    public void setOriginUpPoint(Integer originUpPoint) {
        this.originUpPoint = originUpPoint;
    }

    public Integer getDownPoint() {
        return downPoint;
    }

    public Integer getOriginDownPoint() {
        return originDownPoint;
    }

    public void setOriginDownPoint(Integer originDownPoint) {
        this.originDownPoint = originDownPoint;
    }

    public void setDownPoint(Integer downPoint) {
        this.downPoint = downPoint;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public String getStartTime() {
        if (this.getBegin() == null) {
            return null;
        }
        SimpleDateFormat londonSdf = new SimpleDateFormat("HH:mm");
        londonSdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return londonSdf.format(new Date(this.getBegin() * 1000));
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
        SimpleDateFormat londonSdf = new SimpleDateFormat("HH:mm");
        londonSdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        try {
            Integer time = Integer.parseInt(String.valueOf(londonSdf.parse(startTime).getTime() / 1000));
            this.setBegin(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getEndTime() {
        if (this.getEnd() == null) {
            return null;
        }
        SimpleDateFormat londonSdf = new SimpleDateFormat("HH:mm");
        londonSdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        return londonSdf.format(new Date(this.getEnd() * 1000));
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
        SimpleDateFormat londonSdf = new SimpleDateFormat("HH:mm");
        londonSdf.setTimeZone(TimeZone.getTimeZone("GMT+0:00"));
        try {
            Integer time = Integer.parseInt(String.valueOf(londonSdf.parse(endTime).getTime() / 1000));
            this.setEnd(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Integer getIsBullAxisMove() {
        return isBullAxisMove;
    }

    public void setIsBullAxisMove(Integer isBullAxisMove) {
        this.isBullAxisMove = isBullAxisMove;
    }

    public Integer getIsTradeBeforeAxisMove() {
        return isTradeBeforeAxisMove;
    }

    public void setIsTradeBeforeAxisMove(Integer isTradeBeforeAxisMove) {
        this.isTradeBeforeAxisMove = isTradeBeforeAxisMove;
    }
}
