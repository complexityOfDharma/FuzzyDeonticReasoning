package edu.gmu.fuzzydr.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.model.agents.School;
import edu.gmu.fuzzydr.model.agents.Workplace;
	
public class WorkplaceLoader {

	/**
	 * Class Main for testing.
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException 
    {
        // Creating instance to avoid static member methods
		WorkplaceLoader instance = new WorkplaceLoader();
		// InputStream is = instance.getFileAsIOStream(Config.getWorkplacePath());
        instance.loadWorkplaces(Config.getWorkplacePath());
    }

	public void loadWorkplaces(String file) throws IOException {
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

    private void doLoad(InputStream is) throws IOException
    {
        try (InputStreamReader isr = new InputStreamReader(is); 
                BufferedReader br = new BufferedReader(isr);) 
        {
            String line;
            
            System.out.print("Loading workplaces ...");
            
            br.readLine();  // read and discard header row.
            
            while ((line = br.readLine()) != null) {
            	String[] fields = line.split("\t");
            	
            	int wrkID = Integer.parseInt(fields[0]);
            	double wrkLat = Double.parseDouble(fields[1]);
            	double wrkLon = Double.parseDouble(fields[2]);
            	
            	Workplace w = new Workplace(wrkID, wrkLat, wrkLon);
            	FuzzyDRController.masterList_Workplaces.add(w);
            	FuzzyDRController.masterMap_Workplaces.put(wrkID, w);
    			
            }
            is.close();
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

