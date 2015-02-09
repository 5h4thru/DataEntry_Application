package edu.utdallas.sai.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
	private Button updateButton;
	@FXML
	private Label statusLabel;

	//Checking the errors will require two lists.
	private int[] errorList;
	private HashMap<String, String> errorStrings;
	@FXML
	private Label fnStatusLabel;
	@FXML
	private Label lnStatusLabel;
	@FXML
	private Label mStatusLabel;
	@FXML
	private Label adoStatusLabel;
	@FXML
	private Label adtStatusLabel;
	@FXML
	private Label ctStatusLabel;
	@FXML
	private Label stStatusLabel;
	@FXML
	private Label zpStatusLabel;
	@FXML
	private Label phStatusLabel;
	@FXML
	private Label gnStatusLabel;
	
	//Reference to mainApp
	private MainApp mainApp;



	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public DataOverviewController() {
		//Initialize the error helper variables
		errorList = new int[10]; errorStrings = new HashMap<String, String>();
		Arrays.fill(errorList, 0);
		errorStrings.put("nameError", "Should not exceed 20 characters");
		errorStrings.put("initialError", "Should only be 1 character long");
		errorStrings.put("addressError", "Should not exceed 35 characters");
		errorStrings.put("cityError", "Should not exceed 25 characters");
		errorStrings.put("stateError", "Should not exceed 2 characters");
		errorStrings.put("zipError", "Should only be numbers");
		errorStrings.put("zipLength", "Should not exceed 9 characters");
		errorStrings.put("phoneError", "Should only be numbers");
		errorStrings.put("phoneLength", "Should not exceed 21 characters");
		errorStrings.put("genderError", "Should be M/F");
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

		//Verify all the text fields before adding
		verifyTextFields();
		
	}

	/**
	 * Method to listen to even changes in the table
	 */
	public void onClickOfTable() {
		// Listen for selection changes and show the person details when changed.
		personTable.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showPersonDetails(newValue)
				);
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
//		else if((zipCodeText.getText().length()!=0) && !(zipCodeText.getText().matches("[0-9]+"))) {
//			statusLabel.setWrapText(true);
//			statusLabel.setText("Zip Code should be digits");
//			addButton.setDisable(true);
//		}
//		else if(((phoneText.getText().length()!=0) && !(phoneText.getText().matches("[0-9]+")))) {
//			statusLabel.setWrapText(true);
//			statusLabel.setText("Phone Number should be digits");
//			addButton.setDisable(true);
//		}
//		else if(middleInitialText.getText().length()>1) {
//			statusLabel.setWrapText(true);
//			statusLabel.setText("Middle Initial should be one character");
//			addButton.setDisable(true);
//		}
//		else if(stateText.getText().length()>2) {
//			statusLabel.setWrapText(true);
//			statusLabel.setText("State should represented with two characters");
//			addButton.setDisable(true);
//		}
//		else if(genderText.getText().length()>1) {
//			statusLabel.setWrapText(true);
//			statusLabel.setText("Gender should be either M/F");
//			addButton.setDisable(true);
//		}
		else {
			//Check if the person is already existing
			PersonValidation personCheck = new PersonValidation();
			personCheck.setMainApp(mainApp);
			//Set the first, last and middle name for validation purposes
			person.setFirstName(firstNameText.getText());
			person.setLastName(lastNameText.getText());
			person.setMiddleInitial(middleInitialText.getText().toUpperCase());
			if(personCheck.isExisting(person)){
				statusLabel.setWrapText(true);
				statusLabel.setText("This person "
						+person.getFirstName()+" "
						+person.getMiddleInitial()+","
						+person.getLastName()+
						" is already available in the database");
			}
			else {
				statusLabel.setText("");
				addButton.setDisable(false);
				person.setAddressLineOne(addressLineOneText.getText());
				person.setAddressLineTwo(addressLineTwoText.getText());
				person.setCity(cityText.getText());
				person.setState(stateText.getText().toUpperCase());
				person.setZipCode(zipCodeText.getText());
				person.setPhoneNumber(phoneText.getText());
				person.setGender(genderText.getText().toUpperCase());
				//Adding to personData will help to display the new entry in the app
				mainApp.getPersonData().add(person);
				//Creating the file path and assigning mainApp reference to PersonValidation class
				File file = new File("./db_file.txt");
				FileUtil psvObject = new FileUtil();
				psvObject.setMainApp(mainApp);
				psvObject.savePersonDataToFile(file, person);
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
		String lastName = null;
		String middleInitial = null;
		if(selectedIndex >=0 ) {
			firstNameToBeDeteled = personTable.getItems().get(selectedIndex).getFirstName();
			lastName = personTable.getItems().get(selectedIndex).getLastName();
			middleInitial = personTable.getItems().get(selectedIndex).getMiddleInitial();
			//Creating the file path and assigning mainApp reference to PersonValidation class
			String fileName = "./db_file.txt";
			FileUtil psvObject = new FileUtil();
			psvObject.setMainApp(mainApp);
			psvObject.deletePersonFromFile(fileName, firstNameToBeDeteled, lastName, middleInitial);
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
	public void handleUpdatePerson() {
		//statusLabel.setText("Update button pressed!");
		int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
		String firstNameToBeUpdated = null;
		String lastName = null;
		String middleInitial = null;
		FileUtil psvObject = new FileUtil();
		if(selectedIndex >=0 ) {
			firstNameToBeUpdated = personTable.getItems().get(selectedIndex).getFirstName();
			lastName = personTable.getItems().get(selectedIndex).getLastName();
			middleInitial = personTable.getItems().get(selectedIndex).getMiddleInitial();
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
//			else if(phoneText.getText().length()==0){
//				statusLabel.setWrapText(true);
//				statusLabel.setText("Phone number cannot be blank");
//				addButton.setDisable(true);
//			}
//			else if(!(zipCodeText.getText().matches("[0-9]+"))) {
//				statusLabel.setWrapText(true);
//				statusLabel.setText("Zip Code should be digits");
//				addButton.setDisable(true);
//			}
//			else if(!(phoneText.getText().matches("[0-9]+"))) {
//				statusLabel.setWrapText(true);
//				statusLabel.setText("Phone Number should be digits");
//				addButton.setDisable(true);
//			}
			else {
				//Check if the person is already existing
				PersonValidation personCheck = new PersonValidation();
				personCheck.setMainApp(mainApp);
				
				if(firstNameText.getText()!=null) person.setFirstName(firstNameText.getText()); else person.setFirstName(null);
				if(lastNameText.getText()!=null) person.setLastName(lastNameText.getText()); else person.setLastName(null);
				if(middleInitialText.getText()!=null) person.setMiddleInitial(middleInitialText.getText().toUpperCase()); else person.setMiddleInitial(null);
				if(addressLineOneText.getText()!=null) person.setAddressLineOne(addressLineOneText.getText()); else person.setAddressLineOne(null);
				if(addressLineTwoText.getText()!=null) person.setAddressLineTwo(addressLineTwoText.getText()); else person.setAddressLineTwo(null);
				if(cityText.getText()!=null) person.setCity(cityText.getText()); else person.setCity(null);
				if(stateText.getText()!=null) person.setState(stateText.getText().toUpperCase()); else person.setState(null);
				if(zipCodeText.getText()!=null) person.setZipCode(zipCodeText.getText()); else person.setZipCode(null);
				if(phoneText.getText()!=null) person.setPhoneNumber(phoneText.getText()); else person.setPhoneNumber(null);
				if(genderText.getText()!=null) person.setGender(genderText.getText().toUpperCase()); else person.setGender(null);
				if(personCheck.isUpToDate(person)){
					statusLabel.setWrapText(true);
					statusLabel.setText("This person "
							+person.getFirstName()+" "
							+person.getMiddleInitial()+","
							+person.getLastName()+
							" is already up-to-date in the database");
					
				}
				else {
					statusLabel.setText("");
					addButton.setDisable(false);


					//Adding to personData will help to display the new entry in the app
					mainApp.getPersonData().add(person);
					//Creating the file path and assigning mainApp reference to PersonValidation class
					String fileName = "./db_file.txt";
					psvObject.setMainApp(mainApp);
					psvObject.updatePersonInFile(fileName, firstNameToBeUpdated, lastName, middleInitial, person);
					statusLabel.setWrapText(true);
					statusLabel.setText("Hello World!");
					statusLabel.setText("Updated "
							+personTable.getItems().get(selectedIndex).getFirstName()+
							" in the table");
					System.out.println("Updated "
							+personTable.getItems().get(selectedIndex).getFirstName()+
							" in the table");
					personTable.getItems().remove(selectedIndex);
				}
	
			}
		}
		else{
			updateButton.setDisable(false);
		}

	}

	/**
	 * This method is used to validate all the business logic
	 */
	@FXML
	public void verifyTextFields() {
		
		ChangeListener<String> textListener = new ChangeListener<String>() {
			
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {
				StringProperty obsVal = (StringProperty) observable;
				TextField observedTextField = (TextField) obsVal.getBean();
				if(observedTextField==firstNameText) {
					if((newValue!=null)&&newValue.length()>20) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("First name should not be more than 20 characters.");
//						addButton.setDisable(true);
						fnStatusLabel.setText(errorStrings.get("nameError"));
						errorList[0]=1;
					}
					else {
						fnStatusLabel.setText("");
						errorList[0]=0;
//						addButton.setDisable(false);
					}
					
				}
				else if (observedTextField==lastNameText) {
					if((newValue!=null)&&newValue.length()>20) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Last name should not be more than 20 characters.");
//						addButton.setDisable(true);
						errorList[1]=1;
						lnStatusLabel.setText(errorStrings.get("nameError"));
					}
					else {
						lnStatusLabel.setText("");
						errorList[1]=0;
//						addButton.setDisable(false);
					}
					
				}
				else if (observedTextField==middleInitialText) {
					if((newValue!=null)&&newValue.length()>1) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Middle initial should only be a single character");
//						addButton.setDisable(true);
						errorList[2]=1;
						mStatusLabel.setText(errorStrings.get("initialError"));
					}
					else {
						mStatusLabel.setText("");
						errorList[2]=0;
//						addButton.setDisable(false);
					}
					
				}
				else if (observedTextField==addressLineOneText) {
					if((newValue!=null)&&newValue.length()>35) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Address fields should not exceed 35 characters each");
//						addButton.setDisable(true);
						errorList[3]=1;
						adoStatusLabel.setText(errorStrings.get("addressError"));
					}
					else {
						adoStatusLabel.setText("");
						errorList[3]=0;
//						addButton.setDisable(false);
					}
					
				}
				else if (observedTextField==addressLineTwoText) {
					if((newValue!=null)&&newValue.length()>35) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Address fields should not exceed 35 characters each");
//						addButton.setDisable(true);
						errorList[4]=1;
						adtStatusLabel.setText(errorStrings.get("addressError"));
					}
					else {
						adtStatusLabel.setText("");
						errorList[4]=0;
//						addButton.setDisable(false);
					}
				}
				else if (observedTextField==cityText) {
					if((newValue!=null)&&newValue.length()>25) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("City name should not exceed 25 characters");
//						addButton.setDisable(true);
						errorList[5]=1;
						ctStatusLabel.setText(errorStrings.get("cityError"));
					}
					else {
						ctStatusLabel.setText("");
						errorList[5]=0;
//						addButton.setDisable(false);
					}
				}
				else if (observedTextField==stateText) {
					if((newValue!=null)&&newValue.length()>2) {
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Please enter the two-lettered-code for your state");
//						addButton.setDisable(true);
						errorList[6]=1;
						stStatusLabel.setText(errorStrings.get("stateError"));
					}
					else {
						stStatusLabel.setText("");
						errorList[6]=0;
//						addButton.setDisable(false);
					}
				}
				else if (observedTextField==zipCodeText) {
//					if((newValue!=null)&&newValue.length()==0 || newValue==null) statusLabel.setText("");
					if((newValue!=null)&&!(newValue.matches("[0-9]+"))){
//						statusLabel.setWrapText(true);
						zpStatusLabel.setText(errorStrings.get("zipError"));
//						addButton.setDisable(true);
						errorList[7]=1;
					}
					else{
//						addButton.setDisable(false);
						zpStatusLabel.setText("");
						if((newValue!=null)&&newValue.length()>9) {
							zpStatusLabel.setText(errorStrings.get("zipLength"));
//							addButton.setDisable(true);
							errorList[7]=1;
						}
						else {
							zpStatusLabel.setText("");
							errorList[7]=0;
//							addButton.setDisable(false);
						}
					}
				}
				else if (observedTextField==phoneText) {
					if((newValue!=null)&&!(newValue.matches("[0-9]+"))){
//						statusLabel.setWrapText(true);
//						statusLabel.setText("Please enter numbers only");
//						addButton.setDisable(true);
						errorList[8]=1;
						phStatusLabel.setText(errorStrings.get("phoneError"));
					}
					else{
//						addButton.setDisable(false);
//						statusLabel.setText("");
						phStatusLabel.setText("");
						if((newValue!=null)&&newValue.length()>21) {
							phStatusLabel.setText(errorStrings.get("phoneLength"));
//							addButton.setDisable(true);
							errorList[8]=1;
						}
						else {
							phStatusLabel.setText("");
//							statusLabel.setText("");
							errorList[8]=0;
//							addButton.setDisable(false);
						}
					}
				}
				else if (observedTextField==genderText) {
					if((newValue!=null)&&!(newValue.equalsIgnoreCase("m") || newValue.equalsIgnoreCase("f"))) {
//						statusLabel.setText("Enter either M/F");
//						addButton.setDisable(true);
						errorList[9]=1;
						gnStatusLabel.setText(errorStrings.get("genderError"));
					}
					else {
						gnStatusLabel.setText("");
						errorList[9]=0;
//						addButton.setDisable(false);
					}
				}

				int flag = -1;
				for (int error : errorList) {
					if(error==1) flag = 1;
				}
				if(flag==-1) {
					addButton.setDisable(false);
					updateButton.setDisable(false);
				}
				else {
					addButton.setDisable(true);
					updateButton.setDisable(true);
				}
			}
		}; 

		firstNameText.textProperty().addListener(textListener);
		lastNameText.textProperty().addListener(textListener);
		middleInitialText.textProperty().addListener(textListener);
		addressLineOneText.textProperty().addListener(textListener);
		addressLineTwoText.textProperty().addListener(textListener);
		cityText.textProperty().addListener(textListener);
		stateText.textProperty().addListener(textListener);
		zipCodeText.textProperty().addListener(textListener);
		phoneText.textProperty().addListener(textListener);
		genderText.textProperty().addListener(textListener);

	}
}
