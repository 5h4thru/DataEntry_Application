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
	public void loadPersonDataFromFile(File file) {


		try {		
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			String line = null;

			ObservableList<Person> personData = null;
			personData = mainApp.getPersonData();
			line = reader.readLine();

			while(line!=null) {
				Person personObject = new Person();
				/*To skip carriage returns in the text file*/ while(line.length()==0) line=reader.readLine();
				String[] oneLineSplit = line.split(":");
				personObject.setFirstName(oneLineSplit[0]);
				personObject.setLastName(oneLineSplit[1]);
				personObject.setMiddleInitial(oneLineSplit[2]);
				personObject.setAddressLineOne(oneLineSplit[3]);
				personObject.setAddressLineTwo(oneLineSplit[4]);
				personObject.setCity(oneLineSplit[5]);
				personObject.setState(oneLineSplit[6]);
				personObject.setZipCode(oneLineSplit[7]);
				personObject.setPhoneNumber(oneLineSplit[8]);
				personObject.setGender(oneLineSplit[9]);
				personData.add(personObject);
				line = reader.readLine();
			}
			if(reader!=null)
				reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the current person data to the specified file.
	 * 
	 * @param file
	 */
	public void savePersonDataToFile(File file, Person person) {
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));

			writer.write("\n");
			writer.write(person.getFirstName()+":");
			writer.write(person.getLastName()+":");
			writer.write(person.getMiddleInitial()+":");
			writer.write(person.getAddressLineOne()+":");
			writer.write(person.getAddressLineTwo()+":");
			writer.write(person.getCity()+":");
			writer.write(person.getState()+":");
			writer.write(person.getZipCode()+":");
			writer.write(person.getPhoneNumber()+":");
			writer.write(person.getGender());

			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePersonFromFile(String fileName, String firstNameToBeDeteled) {
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
				String[] oneLineString = line.split(":");
				if (!oneLineString[0].equalsIgnoreCase(firstNameToBeDeteled)) {

					pw.println(line);
					pw.flush();
				}
			}
			pw.close();
			br.close();

			//Delete the original file
			if (!inFile.delete()) {
				System.out.println("Could not delete file");
				return;
			} 

			//Rename the new file to the filename the original file had.
			if (!tempFile.renameTo(inFile))
				System.out.println("Could not rename file");

		}
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}

	}


}
