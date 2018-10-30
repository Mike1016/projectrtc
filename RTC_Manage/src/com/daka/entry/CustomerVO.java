package com.daka.entry;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.math.BigDecimal;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class CustomerVO {

    private Integer id;
    private Integer agentId;
    private String opendId;
    private String nickName;
    private String level;
    private String headImg;
    private String alipay;
    private String city;
    private String birthday;
    private String identityCard;
    private String phone;
    private String extensionCode;
    private String password;
    private String securityPassword;
    private String regesterTime;
    private java.math.BigDecimal teamAchievement;
    private java.math.BigDecimal originalWallet;
    private java.math.BigDecimal waitingDividendsWallet;
    private java.math.BigDecimal dividendsWallet;
    private java.math.BigDecimal rebateWallet;
    private java.math.BigDecimal tastesMoney;
    private java.math.BigDecimal shoppingCoin;
    private Integer state;
    private String code;
    private Integer status;
    private String activationTime;
    private java.math.BigDecimal recommendBonus;

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public BigDecimal getRecommendBonus() {
        return recommendBonus;
    }

    public void setRecommendBonus(BigDecimal recommendBonus) {
        this.recommendBonus = recommendBonus;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public java.math.BigDecimal getOriginalWallet() {
		return originalWallet;
	}

	public void setOriginalWallet(java.math.BigDecimal originalWallet) {
		this.originalWallet = originalWallet;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getOpendId() {
        return opendId;
    }

    public void setOpendId(String opendId) {
        this.opendId = opendId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtensionCode() {
        return extensionCode;
    }

    public void setExtensionCode(String extensionCode) {
        this.extensionCode = extensionCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public String getRegesterTime() {
        return regesterTime;
    }

    public void setRegesterTime(String regesterTime) {
        this.regesterTime = regesterTime;
    }

    public java.math.BigDecimal getTeamAchievement() {
        return teamAchievement;
    }

    public void setTeamAchievement(java.math.BigDecimal teamAchievement) {
        this.teamAchievement = teamAchievement;
    }

    public java.math.BigDecimal getWaitingDividendsWallet() {
        return waitingDividendsWallet;
    }

    public void setWaitingDividendsWallet(java.math.BigDecimal waitingDividendsWallet) {
        this.waitingDividendsWallet = waitingDividendsWallet;
    }

    public java.math.BigDecimal getDividendsWallet() {
        return dividendsWallet;
    }

    public void setDividendsWallet(java.math.BigDecimal dividendsWallet) {
        this.dividendsWallet = dividendsWallet;
    }

    public java.math.BigDecimal getRebateWallet() {
        return rebateWallet;
    }

    public void setRebateWallet(java.math.BigDecimal rebateWallet) {
        this.rebateWallet = rebateWallet;
    }

    public java.math.BigDecimal getTastesMoney() {
        return tastesMoney;
    }

    public void setTastesMoney(java.math.BigDecimal tastesMoney) {
        this.tastesMoney = tastesMoney;
    }

    public java.math.BigDecimal getShoppingCoin() {
        return shoppingCoin;
    }

    public void setShoppingCoin(java.math.BigDecimal shoppingCoin) {
        this.shoppingCoin = shoppingCoin;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
