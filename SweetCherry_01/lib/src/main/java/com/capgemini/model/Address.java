package com.capgemini.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// @Component is used to register the Entity as bean
@Component

@Scope(scopeName = "prototype")
@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;

	@Column(name = "CITY", nullable = false)
	private String city;

	@Column(name = "STATE", nullable = false)
	private String state;

	@Column(name = "PIN_CODE", nullable = false)
	private String pinCode;

	@Column(name = "HOUSE_NO", nullable = false)
	private String houseNo;

	@Column(name = "LANDMARK")
	private String landmark;

	public Address() {

	}

	public Address(String city) {
		super();
		this.city = city;
	}

	public Address(int addressId, String city, String state, String pinCode, String houseNo, String landmark) {
		super();
		this.addressId = addressId;
		this.city = city;
		this.state = state;
		this.pinCode = pinCode;
		this.houseNo = houseNo;
		this.landmark = landmark;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPinCode() {
		return pinCode;
	}

	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	@Override
	public String toString() {
		return "Address [adressId=" + addressId + ", city=" + city + ", state=" + state + ", pinCode=" + pinCode
				+ ", houseNo=" + houseNo + ", landmark=" + landmark + "]";
	}

}