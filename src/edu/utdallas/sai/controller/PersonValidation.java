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

		ObservableList<Person>personData = mainApp.getPersonData();
		for (Person personInTheList : personData) {
			if((personToBeVerified.getFirstName().equalsIgnoreCase(personInTheList.getFirstName()))
					&&(personToBeVerified.getMiddleInitial().equalsIgnoreCase(personInTheList.getMiddleInitial()))
					&&(personToBeVerified.getLastName().equalsIgnoreCase(personInTheList.getLastName())))
				flag = true;
		}
		return flag;
	}

}
