package edu.gmu.fuzzydr.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.model.agents.School;
import edu.gmu.fuzzydr.model.agents.Workplace;

public class SchoolLoader {
	
	/**
	 * Class Main for testing.
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException 
    {
        // Creating instance to avoid static member methods
		SchoolLoader instance = new SchoolLoader();

        //InputStream is = instance.getFileAsIOStream("edu/gmu/fuzzydr/resources/synthpop/workplaces.txt");
        InputStream is = instance.getFileAsIOStream(Config.getSchoolsPath());
        //DEBUG: instance.printFileContent(is);
        instance.loadSchools(is);
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

    private void loadSchools(InputStream is) throws IOException
    {
        try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
        {
            String line;
            
            System.out.print("Loading schools ...");
            
            br.readLine();  // read and discard header row.
            
            while ((line = br.readLine()) != null) {
            	String[] fields = line.split("\t");
    			
    			int schID = Integer.parseInt(fields[0]);
    			String stco = fields[1].trim();
    			double schLat = Double.parseDouble(fields[2]);
    			double schLon = Double.parseDouble(fields[3]);
    			
    			// instantiate household based on data row.
    			School s = new School(schID, stco, schLat, schLon);
    			FuzzyDRController.masterList_Schools.add(s);
            }
            is.close();
            
            //DEBUG: int count = FuzzyDRController.masterList_Schools.size();
            //DEBUG: System.out.println(" ... complete. " + count + " schools loaded.");
        }
    }
    
    
    /**
     * Test method to print file contents.
     * @param is
     * @throws IOException
     */
    private void printFileContent(InputStream is) throws IOException 
    {
        try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
        {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
            is.close();
        }
    }
	
	

}
