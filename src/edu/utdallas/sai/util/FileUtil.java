package edu.utdallas.sai.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javafx.collections.ObservableList;
import edu.utdallas.sai.MainApp;
import edu.utdallas.sai.model.Person;

public class FileUtil {

	private MainApp mainApp;

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	/**
	 * Loads person data from the specified file. The current person data will
	 * be replaced.
	 * 
	 * @param file
	 */
	public void loadPersonDataFromFile() {
		File file = new File("./db_file.txt");

		try {		
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;

			ObservableList<Person> personData = null;

			personData = mainApp.getPersonData();
			personData.clear();
			personData = mainApp.getPersonData();
			line = reader.readLine();

			while(line!=null) {
				Person personObject = new Person();
				String[] oneLineSplit = line.split("\t");
				if(oneLineSplit.length>=1) personObject.setFirstName(oneLineSplit[0]); else personObject.setFirstName(null);
				if(oneLineSplit.length>=2) personObject.setLastName(oneLineSplit[1]); else personObject.setLastName(null);
				if(oneLineSplit.length>=3) personObject.setMiddleInitial(oneLineSplit[2]); else personObject.setMiddleInitial(null);
				if(oneLineSplit.length>=4) personObject.setAddressLineOne(oneLineSplit[3]); else personObject.setAddressLineOne(null);
				if(oneLineSplit.length>=5) personObject.setAddressLineTwo(oneLineSplit[4]); else personObject.setAddressLineTwo(null);
				if(oneLineSplit.length>=6) personObject.setCity(oneLineSplit[5]); else personObject.setCity(null);
				if(oneLineSplit.length>=7) personObject.setState(oneLineSplit[6]); else personObject.setState(null);
				if(oneLineSplit.length>=8) personObject.setZipCode(oneLineSplit[7]); else personObject.setZipCode(null);
				if(oneLineSplit.length>=9) personObject.setPhoneNumber(oneLineSplit[8]); else personObject.setPhoneNumber(null);
				if(oneLineSplit.length>=10) personObject.setGender(oneLineSplit[9]); else personObject.setGender(null);
				if((personObject.getFirstName()!=null) && (personObject.getLastName()!=null))
					personData.add(personObject);
				line = reader.readLine();
			}
			if(reader!=null)
				reader.close();
		} catch (Exception e) {
			MyLogger.writeToLog(this.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(Person person) {
		File file = new File("./db_file.txt");
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			writer.write(person.getFirstName()+"\t");
			writer.write(person.getLastName()+"\t");
			writer.write(person.getMiddleInitial()+"\t");
			writer.write(person.getAddressLineOne()+"\t");
			writer.write(person.getAddressLineTwo()+"\t");
			writer.write(person.getCity()+"\t");
			writer.write(person.getState()+"\t");
			writer.write(person.getZipCode()+"\t");
			writer.write(person.getPhoneNumber()+"\t");
			writer.write(person.getGender());
			writer.write("\n");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			MyLogger.writeToLog(this.getClass().getName(), e.getMessage());
		}
	}

	/**
	 * Delete the selected user from the file
	 * @param firstNameToBeDeteled
	 * @param lastName
	 * @param middleInitial
	 */
	public void deletePersonFromFile(String firstNameToBeDeteled, String lastName, String middleInitial) {
		String fileName = "./db_file.txt";
		try{
			File inFile = new File(fileName);

			//Construct the new file that will later be renamed to the original filename. 
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			//Read from the original file and write to the new 
			//unless content matches data to be removed.
			while ((line = br.readLine()) != null) {
				String[] oneLineString = line.split("\t");
				if (!
						(checkString(oneLineString[0], firstNameToBeDeteled)
								&& checkString(oneLineString[1], lastName)
								&& checkString(oneLineString[2], middleInitial))
						) {

					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				MyLogger.writeToLog(this.getClass().getName(), "Cannot delete the original file. Occured while deleting the original file to replace with the tmp file.");
				return;
			} 

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				MyLogger.writeToLog(this.getClass().getName(), "Cannot rename the original file. Occured while renaming the original file to replace with the tmp file.");

		}
		catch (FileNotFoundException ex) {
			MyLogger.writeToLog(this.getClass().getName(), ex.getMessage());
		}
		catch (IOException ex) {
			MyLogger.writeToLog(this.getClass().getName(), ex.getMessage());
		}

	}


	/**
	 * Update the selected user from the file
	 * @param firstNameToBeDeteled
	 * @param lastName
	 * @param middleInitial
	 */
	public void updatePersonInFile(String firstNameToBeUpdated, String lastName, String middleInitial, Person person) {
		String fileName = "./db_file.txt";
		try{
			File inFile = new File(fileName);


			//Construct the new file that will later be renamed to the original filename. 
			File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

			BufferedReader br = new BufferedReader(new FileReader(fileName));
			PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

			String line = null;

			//Read from the original file and write to the new 
			//unless content matches data to be updated.
			while ((line = br.readLine()) != null) {
				String[] oneLineString = line.split("\t");

				if (!
						(checkString(oneLineString[0], firstNameToBeUpdated)
								&& checkString(oneLineString[1], lastName)
								&& checkString(oneLineString[2], middleInitial))
						) {

					pw.println(line);
					pw.flush();

				}
				else {

					pw.print(person.getFirstName()+"\t");
					pw.print(person.getLastName()+"\t");
					pw.print(person.getMiddleInitial()+"\t");
					pw.print(person.getAddressLineOne()+"\t");
					pw.print(person.getAddressLineTwo()+"\t");
					pw.print(person.getCity()+"\t");
					pw.print(person.getState()+"\t");
					pw.print(person.getZipCode()+"\t");
					pw.print(person.getPhoneNumber()+"\t");
					pw.print(person.getGender());
					pw.print("\n");
					pw.flush();

				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				MyLogger.writeToLog(this.getClass().getName(), "Cannot delete the original file. Occured while deleting the original file to replace with the tmp file.");
				return;
			} 

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				MyLogger.writeToLog(this.getClass().getName(), "Cannot rename the original file. Occured while renaming the original file to replace with the tmp file.");

		}
		catch (FileNotFoundException ex) {
			MyLogger.writeToLog(this.getClass().getName(), ex.getMessage());
		}
		catch (IOException ex) {
			MyLogger.writeToLog(this.getClass().getName(), ex.getMessage());
		}
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
