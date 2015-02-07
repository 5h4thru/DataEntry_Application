package edu.utdallas.sai.model;



/**
 * Model Class for a Person
 * @author Durga Sai Preetham Palagummi
 *
 */
public class Person {
private String firstName;
private String lastName;
private String middleInitial;
private String addressLineOne;
private String addressLineTwo;
private String city;
private String state;
private String zipCode;
private String phoneNumber;
private String gender;

public String getFirstName() {
	return firstName;
}

public void setFirstName(String firstName) {
	this.firstName = firstName;
}

public String getLastName() {
	return lastName;
}

public void setLastName(String lastName) {
	this.lastName = lastName;
}

public String getMiddleInitial() {
	return middleInitial;
}

public void setMiddleInitial(String middleInitial) {
	this.middleInitial = middleInitial;
}

public String getAddressLineOne() {
	return addressLineOne;
}

public void setAddressLineOne(String addressLineOne) {
	this.addressLineOne = addressLineOne;
}

public String getAddressLineTwo() {
	return addressLineTwo;
}

public void setAddressLineTwo(String addressLineTwo) {
	this.addressLineTwo = addressLineTwo;
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

public String getZipCode() {
	return zipCode;
}

public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
}

public String getPhoneNumber() {
	return phoneNumber;
}

public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
}

public String getGender() {
	return gender;
}

public void setGender(String gender) {
	this.gender = gender;
}

/**
 * Default constructor.
 */
public Person() {
    this(null, null);
}

/**
 * Constructor with some initial data.
 * 
 * @param firstName
 * @param lastName
 */
public Person(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;

    // Some initial dummy data, just for convenient testing.
    this.middleInitial = "MiddleName";
    this.addressLineOne = "Address One";
    this.addressLineTwo = "Address Two";
    this.city = "City";
    this.state = "State";
    this.zipCode = "ZipCode";
    this.phoneNumber = "Phone";
    this.gender = "Gender";
}



}
