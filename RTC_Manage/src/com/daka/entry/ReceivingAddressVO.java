package com.daka.entry;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ReceivingAddressVO {
    
    private Integer id;
    private Integer customerId;
    private String receivingAddress;
    private String addressee;
    private String contactNo;
    private String whichLocated;
    private Integer state;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public String getReceivingAddress() {
        return receivingAddress;
    }
    public void setReceivingAddress(String receivingAddress) {
        this.receivingAddress = receivingAddress;
    }
    public String getAddressee() {
        return addressee;
    }
    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getWhichLocated() {
        return whichLocated;
    }
    public void setWhichLocated(String whichLocated) {
        this.whichLocated = whichLocated;
    }
    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public ReceivingAddressVO(Integer id, Integer customerId,
	    String receivingAddress, String addressee, String contactNo,
	    String whichLocated, Integer state) {
	super();
	this.id = id;
	this.customerId = customerId;
	this.receivingAddress = receivingAddress;
	this.addressee = addressee;
	this.contactNo = contactNo;
	this.whichLocated = whichLocated;
	this.state = state;
    }
    public ReceivingAddressVO() {
	super();
    }
    
    

}
