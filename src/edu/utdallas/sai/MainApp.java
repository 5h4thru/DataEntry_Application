package edu.utdallas.sai;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import edu.utdallas.sai.controller.DataOverviewController;
import edu.utdallas.sai.model.Person;
import edu.utdallas.sai.util.FileUtil;
import edu.utdallas.sai.util.MyLogger;

/***
 * This is the main application class.
 * Please note that the instance of this class has to be set only once and then pass the instance to 
 * every other class when required
 * @author Durga Sai Preetham Palagummi
 *
 */
public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	/**
	 * The data as an observable list of Persons.
	 */
	private ObservableList<Person> personData = FXCollections.observableArrayList();


	/**
	 * Returns the data as an observable list of Persons. 
	 * @return
	 */
	public ObservableList<Person> getPersonData() {
		return personData;
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Contact Manager");
		initRootLayout();
		showPersonOverview();
		//Set minimum size for the application
		//User will not be able to reduce resolution beyond this point
		this.primaryStage.setMinHeight(820.0);
		this.primaryStage.setMinWidth(1050.0);
		//Application icon
		this.primaryStage.getIcons().add(new Image("file:resources/images/address.png"));
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {

		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			MyLogger.writeToLog(this.getClass().getName(), e.getMessage());
		}
		//Load the person details from the text file
		FileUtil psvObject = new FileUtil();
		psvObject.setMainApp(this);
		psvObject.loadPersonDataFromFile();

	}

	/**
	 * Shows the person overview inside the root layout.
	 */
	public void showPersonOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/DataOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(personOverview);

			// Give the controller access to the main app.
			DataOverviewController controller = loader.getController();
			controller.setMainApp(this);

		} catch (Exception e) {
			MyLogger.writeToLog(this.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}