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
			personData.clear();
			personData = mainApp.getPersonData();
			line = reader.readLine();

			while(line!=null) {
				Person personObject = new Person();
				/*To skip carriage returns in the text file*/ while(line.length()==0) line=reader.readLine();
				String[] oneLineSplit = line.split(":");
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
				if((personObject.getFirstName()!=null) && (personObject.getLastName()!=null) && (personObject.getZipCode()!=null) && (personObject.getPhoneNumber()!=null))
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
			writer.write("\n");

			writer.flush();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deletePersonFromFile(String fileName, String firstNameToBeDeteled, String lastName, String middleInitial) {
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

	public void updatePersonInFile(String fileName, String firstNameToBeUpdated, String lastName, String middleInitial, Person person) {
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
				String[] oneLineString = line.split(":");
//				System.out.println(oneLineString[0]);
				//		System.out.println(oneLineString[1]);
//				System.out.println(oneLineString[2]);
				if (!(
						(oneLineString[0].equalsIgnoreCase(firstNameToBeUpdated))
						&& (oneLineString[1].equalsIgnoreCase(lastName) && (oneLineString[2].equalsIgnoreCase(middleInitial)))
						)) {

					pw.println(line);
					pw.flush();
				}
				else {
					
//						pw.print("\n");
						pw.print(person.getFirstName()+":");
						pw.print(person.getLastName()+":");
						pw.print(person.getMiddleInitial()+":");
						pw.print(person.getAddressLineOne()+":");
						pw.print(person.getAddressLineTwo()+":");
						pw.print(person.getCity()+":");
						pw.print(person.getState()+":");
						pw.print(person.getZipCode()+":");
						pw.print(person.getPhoneNumber()+":");
						pw.print(person.getGender());
						pw.print("\n");
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
