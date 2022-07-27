package edu.gmu.fuzzydr.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.model.agents.Person;

public class PersonLoader {

	/**
	 * Class Main for testing.
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException 
    {
        // Creating instance to avoid static member methods
		PersonLoader instance = new PersonLoader();

        //InputStream is = instance.getFileAsIOStream("edu/gmu/fuzzydr/resources/synthpop/households.txt");
        InputStream is = instance.getFileAsIOStream(Config.getPersonPath());
        //DEBUG: instance.printFileContent(is);
        //instance.loadHouseholds(is);
        instance.loadPeople(Config.getPersonPath());
    }

	
	public void loadPeople(String file) throws IOException {
		doLoad(getFileAsIOStream(file));
	}
	
		
    private InputStream getFileAsIOStream(final String fileName) 
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);
        
        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }

    //private void loadHouseholds(InputStream is) throws IOException
    private void doLoad(InputStream is) throws IOException
    {
        try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
        {
            String line;
            
            System.out.print("Loading people     ...");
            
            br.readLine();  // read and discard header row.
            
            while ((line = br.readLine()) != null) {
            	String[] fields = line.split("\t");
    			
    			int pID = Integer.parseInt(fields[0]);
    			int hhID = Integer.parseInt(fields[1]);
    			int age = Integer.parseInt(fields[2]);
    			String sex = fields[3].trim();
    			int race = Integer.parseInt(fields[4]);
    			int relate = Integer.parseInt(fields[5]);
    			String _schID = fields[6].trim();
    			String _wrkID = fields[7].trim();
    			
    			int schID;
    			int wrkID;
    			
    			if(_schID.equals("X"))
				{
    				schID = 0;
				} 
				else
				{
					schID = Integer.parseInt(fields[6]);
				}
				
				if(_wrkID.equals("X"))
				{
					wrkID = 0;
				} 
				else
				{
					wrkID = Integer.parseInt(fields[7]);
				}
				
				Person p = new Person(pID, hhID, age, sex, schID, wrkID);
    			FuzzyDRController.masterList_Persons.add(p);
    			FuzzyDRController.masterMap_Persons.put(pID, p);
    			
            }
            is.close();
            
        }
    }
	
	
	
	
	// using secondary data, infer the data fields presumed important by published research on social factors for successful NPIs
	// e.g., political party, belief in science, etc,... informed by some predictive model using data science methods
	
	// and also data sources such as SafeGate mobility data to see if NPIs for shutdowns were being followed, and maybe demographics for that
	
	// e.g., CDC Social Vulnerability Index
	
	
	
}
