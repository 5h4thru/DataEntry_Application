package edu.utdallas.sai.controller;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import edu.utdallas.sai.MainApp;
import edu.utdallas.sai.model.Person;
import edu.utdallas.sai.util.FileUtil;

public class DataOverviewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> nameColumn;
	@FXML
	private TableColumn<Person, String> phoneNumberColumn;

	@FXML
	private TextField firstNameText;
	@FXML
	private TextField lastNameText;
	@FXML
	private TextField middleInitialText;
	@FXML
	private TextField addressLineOneText;
	@FXML
	private TextField addressLineTwoText;
	@FXML
	private TextField cityText;
	@FXML
	private TextField stateText;
	@FXML
	private TextField zipCodeText;
	@FXML
	private TextField phoneText;
	@FXML
	private TextField genderText;
	@FXML
	private Button deleteButton;
	@FXML
	private Button addButton;
	@FXML
	private Label statusLabel;
	//Reference to mainApp
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
		if(nameColumn!=null && phoneNumberColumn!=null){
			nameColumn.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()+" "+
							cellData.getValue().getMiddleInitial()+", "+
							cellData.getValue().getLastName()));
			phoneNumberColumn.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
		}
		// Clear person details.
		showPersonDetails(null);

		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue));

		//Verify all the text fields before adding
		verifyTextFields();
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
			firstNameText.setText(person.getFirstName());
			lastNameText.setText(person.getLastName());
			middleInitialText.setText(person.getMiddleInitial());
			addressLineOneText.setText(person.getAddressLineOne());
			addressLineTwoText.setText(person.getAddressLineTwo());
			cityText.setText(person.getCity());
			stateText.setText(person.getState());
			zipCodeText.setText(person.getZipCode());
			phoneText.setText(person.getPhoneNumber());
			genderText.setText(person.getGender());

		} else {
			// Person is null, remove all the text.
			firstNameText.setText("");
			lastNameText.setText("");
			middleInitialText.setText("");
			addressLineOneText.setText("");
			addressLineTwoText.setText("");
			cityText.setText("");
			stateText.setText("");
			zipCodeText.setText("");
			phoneText.setText("");
			genderText.setText("");
		}
	}

	/**
	 * Called when the user clicks on the add button.
	 */
	@FXML
	private void handleAddPerson() {
		Person person = new Person();
		if(firstNameText.getText().length()==0){
			statusLabel.setWrapText(true);
			statusLabel.setText("First name cannot be blank");
			addButton.setDisable(true);
		}
		else if(lastNameText.getText().length()==0){
			statusLabel.setWrapText(true);
			statusLabel.setText("Last name cannot be blank");
			addButton.setDisable(true);
		}
		else if(phoneText.getText().length()==0){
			statusLabel.setWrapText(true);
			statusLabel.setText("Phone number cannot be blank");
			addButton.setDisable(true);
		}
		else if(!(zipCodeText.getText().matches("[0-9]+"))) {
			statusLabel.setWrapText(true);
			statusLabel.setText("Zip Code should be digits");
			addButton.setDisable(true);
		}
		else if(!(phoneText.getText().matches("[0-9]+"))) {
			statusLabel.setWrapText(true);
			statusLabel.setText("Phone Number should be digits");
			addButton.setDisable(true);
		}
		else {
			statusLabel.setText("");
			addButton.setDisable(false);
			person.setFirstName(firstNameText.getText());
			person.setLastName(lastNameText.getText());
			person.setMiddleInitial(middleInitialText.getText().toUpperCase());
			person.setAddressLineOne(addressLineOneText.getText());
			person.setAddressLineTwo(addressLineTwoText.getText());
			person.setCity(cityText.getText());
			person.setState(stateText.getText().toUpperCase());
			person.setZipCode(zipCodeText.getText());
			person.setPhoneNumber(phoneText.getText());
			person.setGender(genderText.getText().toUpperCase());

			//Check if the person is already existing
			PersonValidation personCheck = new PersonValidation();
			personCheck.setMainApp(mainApp);

			if(!personCheck.isExisting(person)){
				//Adding to personData will help to display the new entry in the app
				mainApp.getPersonData().add(person);
				//Creating the file path and assigning mainApp reference to PersonValidation class
				File file = new File("./db_file.txt");
				FileUtil psvObject = new FileUtil();
				psvObject.setMainApp(mainApp);
				psvObject.savePersonDataToFile(file, person);
			}
			else {
				statusLabel.setWrapText(true);
				statusLabel.setText("This person "
						+person.getFirstName()+" "
						+person.getMiddleInitial()+","
						+person.getLastName()+
						" is already available in the database");
			}
		}
	}

	/**
	 * Called when the user clicks on the delete button.
	 */
	@FXML
	private void handleDeletePerson() {
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		String firstNameToBeDeteled = null;
		if(selectedIndex >=0 ) {
			firstNameToBeDeteled = personTable.getItems().get(selectedIndex).getFirstName();
			//Creating the file path and assigning mainApp reference to PersonValidation class
			String fileName = "./db_file.txt";
			FileUtil psvObject = new FileUtil();
			psvObject.setMainApp(mainApp);
			psvObject.deletePersonFromFile(fileName, firstNameToBeDeteled);
			statusLabel.setWrapText(true);
			statusLabel.setText("Deleted "
					+personTable.getItems().get(selectedIndex).getFirstName()+
					" from the table");
			personTable.getItems().remove(selectedIndex);
			selectedIndex = personTable.getSelectionModel().getSelectedIndex();
			if(selectedIndex == -1) {
				deleteButton.setDisable(true);
				statusLabel.setText("");
			}
		}
		else{
			deleteButton.setDisable(true);
		}
	}

	/**
	 * 
	 */
	@FXML
	public void verifyTextFields() {
		// Listen for TextField text changes

		//firstName check
		firstNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>20) {
					statusLabel.setWrapText(true);
					statusLabel.setText("First name should not be more than 20 characters.");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//lastName check
		lastNameText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>20) {
					statusLabel.setWrapText(true);
					statusLabel.setText("Last name should not be more than 20 characters.");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//MiddleInitial check
		middleInitialText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>1) {
					statusLabel.setWrapText(true);
					statusLabel.setText("Middle initial should only be a single character");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//AddressLineOne check
		addressLineOneText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>35) {
					statusLabel.setWrapText(true);
					statusLabel.setText("Address fields should not exceed 35 characters each");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});

		//AddressLineTwo check
		addressLineTwoText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>35) {
					statusLabel.setWrapText(true);
					statusLabel.setText("Address fields should not exceed 35 characters each");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//city check
		cityText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>25) {
					statusLabel.setWrapText(true);
					statusLabel.setText("City name should not exceed 25 characters");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//state check
		stateText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()>2) {
					statusLabel.setWrapText(true);
					statusLabel.setText("Please enter the two-lettered-code for your state");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});


		//zip check
		zipCodeText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(newValue.length()==0 || newValue==null) statusLabel.setText("");
				if(!(newValue.matches("[0-9]+"))){
					statusLabel.setWrapText(true);
					statusLabel.setText("Please enter numbers only");
					addButton.setDisable(true);
				}
				else{
					statusLabel.setText("");
					addButton.setDisable(false);
					if(newValue.length()>9) {
						statusLabel.setText("Zip code should not exceed 9 digits");
						addButton.setDisable(true);
					}
					else {
						statusLabel.setText("");
						addButton.setDisable(false);
					}
				}

			}
		});


		//phone check
		phoneText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(!(newValue.matches("[0-9]+"))){
					statusLabel.setWrapText(true);
					statusLabel.setText("Please enter numbers only");
					addButton.setDisable(true);
				}
				else{
					statusLabel.setText("");
					addButton.setDisable(false);
					if(newValue.length()>21) {
						statusLabel.setText("Zip code should not exceed 21 digits");
						addButton.setDisable(true);
					}
					else {
						statusLabel.setText("");
						addButton.setDisable(false);
					}
				}

			}
		});

		//gender check
		genderText.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				if(!(newValue.equalsIgnoreCase("m") || newValue.equalsIgnoreCase("f"))) {
					statusLabel.setText("Enter either M/F");
					addButton.setDisable(true);
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);
				}
			}
		});
	}
}
