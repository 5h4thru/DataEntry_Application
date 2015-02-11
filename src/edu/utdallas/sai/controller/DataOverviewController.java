package edu.utdallas.sai.controller;

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
					cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()+", "+
							cellData.getValue().getLastName()));
			phoneNumberColumn.setCellValueFactory(
					cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
		}

		// Clear person details.
		showPersonDetails(null);

		//Verify all the text fields before adding
		verifyTextFields();

		//Initially disable delete button
		deleteButton.setDisable(true);
	}

	/**
	 * Method to listen to even changes in the table
	 */
	public void onClickOfTable() {
		personTable.setItems(mainApp.getPersonData());
		//Re-enable delete button
		deleteButton.setDisable(false);
		//Clear the status message
		statusLabel.setText("");

		//This following code will solve the problem that
		//when there is only one person left in the list,
		//user was not able to select the person and populate the text fields
		showPersonDetails(mainApp.getPersonData().get(personTable.getSelectionModel().selectedIndexProperty().get()));
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
		if(firstNameText.getText()==null || firstNameText.getText().length()==0){
			fnStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(lastNameText.getText()==null || lastNameText.getText().length()==0){
			lnStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(addressLineOneText.getText()==null || addressLineOneText.getText().length()==0){
			adoStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(cityText.getText()==null || cityText.getText().length()==0){
			ctStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(stateText.getText()==null || stateText.getText().length()==0){
			stStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(zipCodeText.getText()==null || zipCodeText.getText().length()==0){
			zpStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(phoneText.getText()==null || phoneText.getText().length()==0){
			phStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else if(genderText.getText()==null || genderText.getText().length()==0){
			gnStatusLabel.setText("Cannot be blank");
			addButton.setDisable(true);
		}
		else {
			//Check if the person is already existing
			PersonValidation personCheck = new PersonValidation();
			personCheck.setMainApp(mainApp);
			//Set the first, last and middle name for validation purposes
			person.setFirstName(firstNameText.getText());
			person.setLastName(lastNameText.getText());
			if (middleInitialText.getText()!=null) person.setMiddleInitial(middleInitialText.getText().toUpperCase()); else person.setMiddleInitial("");
			if(personCheck.isExisting(person)){
				statusLabel.setWrapText(true);
				statusLabel.setText("This person "
						+person.getFirstName()+" "
						+person.getMiddleInitial()+" "
						+person.getLastName()+
						" is already available.");
			}
			else {
				statusLabel.setText("Added "
						+firstNameText.getText()+" "
						+middleInitialText.getText()+" "
						+lastNameText.getText()+" "+
						"in the table.");
				addButton.setDisable(false);
				person.setAddressLineOne(addressLineOneText.getText());
				if (addressLineTwoText.getText()!=null) person.setAddressLineTwo(addressLineTwoText.getText()); else person.setAddressLineTwo("");
				person.setCity(cityText.getText());
				person.setState(stateText.getText().toUpperCase());
				person.setZipCode(zipCodeText.getText());
				person.setPhoneNumber(phoneText.getText());
				person.setGender(genderText.getText().toUpperCase());
				//Adding to personData will help to display the new entry in the app
				mainApp.getPersonData().add(person);
				//Assigning mainApp reference to PersonValidation class
				FileUtil psvObject = new FileUtil();
				psvObject.setMainApp(mainApp);
				psvObject.savePersonDataToFile(person);
				/*Disable the delete button once a single transaction is complete*/ deleteButton.setDisable(true);
				// Clear the fields once added. Can do this by setting the textfields to setText("")
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
	}


	/**
	 * Called when the user clicks on update button
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
			if(firstNameText.getText()==null || firstNameText.getText().length()==0){
				fnStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(lastNameText.getText()==null || lastNameText.getText().length()==0){
				lnStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(addressLineOneText.getText()==null || addressLineOneText.getText().length()==0){
				adoStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(cityText.getText()==null || cityText.getText().length()==0){
				ctStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(stateText.getText()==null || stateText.getText().length()==0){
				stStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(zipCodeText.getText()==null || zipCodeText.getText().length()==0){
				zpStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(phoneText.getText()==null || phoneText.getText().length()==0){
				phStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else if(genderText.getText()==null || genderText.getText().length()==0){
				gnStatusLabel.setText("Cannot be blank");
				updateButton.setDisable(true);
			}
			else {
				//Check if the person is already existing
				PersonValidation personCheck = new PersonValidation();
				personCheck.setMainApp(mainApp);

				person.setFirstName(firstNameText.getText());
				person.setLastName(lastNameText.getText());
				if (middleInitialText.getText()!=null) person.setMiddleInitial(middleInitialText.getText().toUpperCase()); else person.setMiddleInitial("");
				person.setAddressLineOne(addressLineOneText.getText());
				if (addressLineTwoText.getText()!=null) person.setAddressLineTwo(addressLineTwoText.getText()); else person.setAddressLineTwo("");
				person.setCity(cityText.getText());
				person.setState(stateText.getText().toUpperCase());
				person.setZipCode(zipCodeText.getText());
				person.setPhoneNumber(phoneText.getText());
				person.setGender(genderText.getText().toUpperCase());
				if(personCheck.isUpToDate(person)){
					statusLabel.setWrapText(true);
					statusLabel.setText("This person "
							+person.getFirstName()+" "
							+person.getMiddleInitial()+" "
							+person.getLastName()+
							" is already up-to-date.");

				}
				else {
					statusLabel.setText("");
					updateButton.setDisable(false);
					//Adding to personData will help to display the new entry in the app
					mainApp.getPersonData().add(person);
					//Creating the file path and assigning mainApp reference to PersonValidation class
					psvObject.setMainApp(mainApp);
					psvObject.updatePersonInFile(firstNameToBeUpdated, lastName, middleInitial, person);
					statusLabel.setWrapText(true);
					statusLabel.setText("Updated "
							+personTable.getItems().get(selectedIndex).getFirstName()+" "
							+personTable.getItems().get(selectedIndex).getMiddleInitial()+" "
							+personTable.getItems().get(selectedIndex).getLastName()+" "
							+" in the table.");
					personTable.getItems().remove(selectedIndex);
					/*Disable the delete button once a single transaction is complete*/ deleteButton.setDisable(true);
					// Clear the fields once added. Can do this by setting the textfields to setText("")
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
		}
		else{
			updateButton.setDisable(false);
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
			//Assigning mainApp reference to PersonValidation class
			FileUtil psvObject = new FileUtil();
			psvObject.setMainApp(mainApp);
			psvObject.deletePersonFromFile(firstNameToBeDeteled, lastName, middleInitial);
			statusLabel.setWrapText(true);
			statusLabel.setText("Deleted "
					+personTable.getItems().get(selectedIndex).getFirstName()+" "
					+personTable.getItems().get(selectedIndex).getMiddleInitial()+" "
					+personTable.getItems().get(selectedIndex).getLastName()+" "
					+" from the table.");
			personTable.getItems().remove(selectedIndex);
			/*Disable the delete button once a single transaction is complete*/ deleteButton.setDisable(true);
			// Clear the fields once added. Can do this by setting the textfields to setText("")
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
				if(observedTextField==firstNameText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>20) {
						fnStatusLabel.setText(errorStrings.get("nameError"));
						errorList[0]=1;
					}
					else {
						fnStatusLabel.setText("");
						errorList[0]=0;
					}

				}
				else if (observedTextField==lastNameText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>20) {
						errorList[1]=1;
						lnStatusLabel.setText(errorStrings.get("nameError"));
					}
					else {
						lnStatusLabel.setText("");
						errorList[1]=0;
					}

				}
				else if (observedTextField==middleInitialText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>1) {
						errorList[2]=1;
						mStatusLabel.setText(errorStrings.get("initialError"));
					}
					else {
						mStatusLabel.setText("");
						errorList[2]=0;
					}

				}
				else if (observedTextField==addressLineOneText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>35) {
						errorList[3]=1;
						adoStatusLabel.setText(errorStrings.get("addressError"));
					}
					else {
						adoStatusLabel.setText("");
						errorList[3]=0;
					}

				}
				else if (observedTextField==addressLineTwoText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>35) {
						errorList[4]=1;
						adtStatusLabel.setText(errorStrings.get("addressError"));
					}
					else {
						adtStatusLabel.setText("");
						errorList[4]=0;
					}
				}
				else if (observedTextField==cityText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>25) {
						errorList[5]=1;
						ctStatusLabel.setText(errorStrings.get("cityError"));
					}
					else {
						ctStatusLabel.setText("");
						errorList[5]=0;
					}
				}
				else if (observedTextField==stateText && newValue!=null) {
					if((newValue!=null)&&newValue.length()>2) {
						errorList[6]=1;
						stStatusLabel.setText(errorStrings.get("stateError"));
					}
					else {
						stStatusLabel.setText("");
						errorList[6]=0;
					}
				}
				else if (observedTextField==zipCodeText && newValue!=null) {
					if((newValue.length()!=0)&&!(newValue.matches("[0-9]+"))){
						zpStatusLabel.setText(errorStrings.get("zipError"));
						errorList[7]=1;
					}
					else {
						zpStatusLabel.setText("");
						if((newValue!=null)&&newValue.length()>9) {
							zpStatusLabel.setText(errorStrings.get("zipLength"));
							errorList[7]=1;
						}
						else {
							zpStatusLabel.setText("");
							errorList[7]=0;
						}
					}
				}
				else if (observedTextField==phoneText && newValue!=null) {
					if((newValue.length()!=0)&&!(newValue.matches("[0-9]+"))){
						errorList[8]=1;
						phStatusLabel.setText(errorStrings.get("phoneError"));
					}
					else{
						phStatusLabel.setText("");
						if((newValue!=null)&&newValue.length()>21) {
							phStatusLabel.setText(errorStrings.get("phoneLength"));
							errorList[8]=1;
						}
						else {
							phStatusLabel.setText("");
							errorList[8]=0;
						}
					}
				}
				else if (observedTextField==genderText && newValue!=null) {
					if((newValue.length()!=0)&&!(newValue.equalsIgnoreCase("m") || newValue.equalsIgnoreCase("f"))) {
						errorList[9]=1;
						gnStatusLabel.setText(errorStrings.get("genderError"));
					}
					else {
						gnStatusLabel.setText("");
						errorList[9]=0;
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
