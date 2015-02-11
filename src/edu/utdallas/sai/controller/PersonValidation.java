package edu.utdallas.sai.controller;

import javafx.collections.ObservableList;
import edu.utdallas.sai.MainApp;
import edu.utdallas.sai.model.Person;

/***
 * This class is used to check if the person to be added
 * is already available in the database
 * @author Durga Sai Preetham Palagummi
 *
 */
public class PersonValidation {

	private MainApp mainApp;
	private boolean flag = false;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}
	public boolean isExisting(Person personToBeVerified) {

		ObservableList<Person> personData = mainApp.getPersonData();
		for (Person personInTheList : personData) {
			if((checkString(personToBeVerified.getFirstName(), personInTheList.getFirstName()))
					&& checkString(personToBeVerified.getLastName(),personInTheList.getLastName())
					&& checkString(personToBeVerified.getMiddleInitial(),personInTheList.getMiddleInitial())) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean isUpToDate(Person personToBeVerified) {
		ObservableList<Person> personData = mainApp.getPersonData();
		for (Person personInTheList : personData) {
			if((checkString(personToBeVerified.getFirstName(), personInTheList.getFirstName()))
					&& checkString(personToBeVerified.getLastName(),personInTheList.getLastName())
					&& checkString(personToBeVerified.getMiddleInitial(),personInTheList.getMiddleInitial())
					&& checkString(personToBeVerified.getAddressLineOne(),personInTheList.getAddressLineOne())
					&& checkString(personToBeVerified.getAddressLineTwo(),personInTheList.getAddressLineTwo())
					&& checkString(personToBeVerified.getCity(),personInTheList.getCity())
					&& checkString(personToBeVerified.getState(),personInTheList.getState())
					&& checkString(personToBeVerified.getZipCode(),personInTheList.getZipCode())
					&& checkString(personToBeVerified.getPhoneNumber(),personInTheList.getPhoneNumber())
					&& checkString(personToBeVerified.getGender(),personInTheList.getGender()))
				flag = true;
		}
		return flag;
	}

	/**
	 * Custom function to check two strings
	 * @param prStringToBeVerified
	 * @param prStringInTheList
	 * @return
	 */
	public boolean checkString(String prStringToBeVerified, String prStringInTheList) {
		if(
				(prStringToBeVerified==null || prStringToBeVerified.length()==0)
				&& (prStringInTheList==null || prStringInTheList.length()==0)
				)
			return true;
		else if (prStringToBeVerified.equalsIgnoreCase(prStringInTheList))
			return true;
		else
			return false;

	}
}
