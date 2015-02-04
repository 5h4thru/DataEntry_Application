package edu.utdallas.sai.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Model Class for a Person
 * @author Sai
 *
 */
public class Person {
private final StringProperty firstName;
private final StringProperty lastName;
private final StringProperty middleInitial;
private final StringProperty addressLineOne;
private final StringProperty addressLineTwo;
private final StringProperty city;
private final StringProperty state;
private final IntegerProperty zipCode;
private final IntegerProperty phoneNumber;
private final StringProperty gender;

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
    this.firstName = new SimpleStringProperty(firstName);
    this.lastName = new SimpleStringProperty(lastName);

    // Some initial dummy data, just for convenient testing.
    this.middleInitial = new SimpleStringProperty("some street");
    this.addressLineOne = new SimpleStringProperty("some address");
    this.addressLineTwo = new SimpleStringProperty("some city");
    this.city = new SimpleStringProperty("some city");
    this.state = new SimpleStringProperty("some city");
    this.zipCode = new SimpleIntegerProperty(1234);
    this.phoneNumber = new SimpleIntegerProperty(1234);
    this.gender = new SimpleStringProperty("some city");
}


public String getFirstName() {
    return firstName.get();
}
public void setFirstName(String firstName) {
    this.firstName.set(firstName);
}
public String getLastName() {
	return lastName.get();
}
public void setLastName(String lastName) {
    this.lastName.set(lastName);
}
public String getMiddleInitial() {
	return middleInitial.get();
}
public void setMiddleInitial(String middleInitial) {
    this.middleInitial.set(middleInitial);
}
public String getAddressLineOne() {
	return addressLineOne.get();
}
public void setAddressLineOne(String alo) {
	this.addressLineOne.set(alo);
}
public String getAddressLineTwo() {
	return addressLineTwo.get();
}
public void setAddressLineTwo(String alt) {
	this.addressLineTwo.set(alt);
}
public String getCity() {
	return city.get();
}
public void setCity(String city) {
	this.city.set(city);
}
public String getState() {
	return state.get();
}
public void setState(String state) {
	this.state.set(state);
}
public int getZipCode() {
	return zipCode.get();
}
public void setZipCode(int zip) {
	this.zipCode.set(zip);
}
public int getPhoneNumber() {
	return phoneNumber.get();
}
public void setPhoneNumber(int phone) {
	this.phoneNumber.set(phone);
}
public String getGender() {
	return gender.get();
}
public void setGender(String gender) {
	this.gender.set(gender);
}

//String Properties
public StringProperty lastNameProperty() {
    return lastName;
}
public StringProperty firstNameProperty() {
    return lastName;
}
public StringProperty getMiddleInitialProperty() {
	return middleInitial;
}
public StringProperty getAddressLineOneProperty() {
	return addressLineOne;
}
public StringProperty getAddressLineTwoProperty() {
	return addressLineTwo;
}
public StringProperty getCityProperty() {
	return city;
}
public StringProperty getStateProperty() {
	return state;
}
public IntegerProperty getZipCodeProperty() {
	return zipCode;
}
public IntegerProperty getPhoneNumberProperty() {
	return phoneNumber;
}
public StringProperty getGenderProperty() {
	return gender;
}
}
