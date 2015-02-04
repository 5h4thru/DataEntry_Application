package edu.utdallas.sai.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import edu.utdallas.sai.MainApp;
import edu.utdallas.sai.model.Person;

public class DataOverviewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;

	@FXML
	private Label firstNameLabel;
	@FXML
	private Label lastNameLabel;
	@FXML
	private Label middleInitialLabel;
	@FXML
	private Label addressLineOneLabel;
	@FXML
	private Label addressLineTwoLabel;
	@FXML
	private Label cityLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Label zipCodeLabel;
	@FXML
	private Label phoneLabel;
	@FXML
	private Label genderLabel;

	// Reference to the main application.
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public DataOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.
		firstNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().firstNameProperty());
		lastNameColumn.setCellValueFactory(
				cellData -> cellData.getValue().lastNameProperty());

		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));
	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

		// Add observable list data to the table
		personTable.setItems(mainApp.getPersonData());
	}

	/**
	 * Fills all text fields to show details about the person.
	 * If the specified person is null, all text fields are cleared.
	 * 
	 * @param person the person or null
	 */
	private void showPersonDetails(Person person) {
		if (person != null) {
			// Fill the labels with info from the person object.
			firstNameLabel.setText(person.getFirstName());
			lastNameLabel.setText(person.getLastName());
			middleInitialLabel.setText(person.getMiddleInitial());
			addressLineOneLabel.setText(person.getAddressLineOne());
			addressLineTwoLabel.setText(person.getAddressLineTwo());
			cityLabel.setText(person.getCity());
			stateLabel.setText(person.getState());
			zipCodeLabel.setText(Integer.toString(person.getZipCode()));
			phoneLabel.setText(Integer.toString(person.getPhoneNumber()));
			genderLabel.setText(person.getGender());

		} else {
			// Person is null, remove all the text.
			firstNameLabel.setText("");
			lastNameLabel.setText("");
			middleInitialLabel.setText("");
			addressLineOneLabel.setText("");
			addressLineTwoLabel.setText("");
			cityLabel.setText("");
			stateLabel.setText("");
			zipCodeLabel.setText("");
			phoneLabel.setText("");
			genderLabel.setText("");
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		if(selectedIndex != -1) {
			personTable.getItems().remove(selectedIndex);
		}
	}
}
