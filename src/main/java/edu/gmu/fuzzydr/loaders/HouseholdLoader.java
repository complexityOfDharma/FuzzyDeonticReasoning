package edu.gmu.fuzzydr.loaders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.model.agents.Household;

/**
 * 
 * Of note, the agent populations are drawn from FRED (A Framework for Reconstructing Epidemic Dynamics),
 * developed by Pitt Public Health Dynamics Laboratory (https://fred.publichealth.pitt.edu/). The synthetic
 * population files are located here: https://fred.publichealth.pitt.edu/syn_pops
 * 
 * FRED citation:
 * Grefenstette JJ, Brown ST, Rosenfeld R, Depasse J, Stone NT, Cooley PC, Wheaton WD, Fyshe A, Galloway DD, 
 * Sriram A, Guclu H, Abraham T, Burke DS. FRED (A Framework for Reconstructing Epidemic Dynamics): An open-
 * source software system for modeling infectious diseases and control strategies using census-based populations. 
 * BMC Public Health, 2013 Oct;13(1), 940. doi: 10.1186/1471-2458-13-940. PubMed PMID: 24103508
 * 
 * @author bhorio
 *
 */
public class HouseholdLoader {

	/**
	 * Class Main for testing.
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException 
    {
        // Creating instance to avoid static member methods
		HouseholdLoader instance = new HouseholdLoader();

        //InputStream is = instance.getFileAsIOStream("edu/gmu/fuzzydr/resources/synthpop/households.txt");
        InputStream is = instance.getFileAsIOStream(Config.getHouseholdPath());
        //DEBUG: instance.printFileContent(is);
        //instance.loadHouseholds(is);
        instance.loadHouseholds(Config.getHouseholdPath());
    }

	
	public void loadHouseholds(String file) throws IOException {
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
            
            System.out.print("Loading households ...");
            
            br.readLine();  // read and discard header row.
            
            while ((line = br.readLine()) != null) {
            	String[] fields = line.split("\t");
    			
    			int hhID = Integer.parseInt(fields[0]);
    			String stcotrbg = fields[1].trim();
    			int hhRace = Integer.parseInt(fields[2]);
    			int hhIncome = Integer.parseInt(fields[3]);
    			double hhLat = Double.parseDouble(fields[4]);
    			double hhLon = Double.parseDouble(fields[5]);
    			
    			Household h = new Household(hhID, stcotrbg, hhRace, hhIncome, hhLat, hhLon);
    			FuzzyDRController.masterList_Households.add(h);
    			FuzzyDRController.masterMap_Households.put(hhID, h);
    			
            }
            is.close();
            
            //DEBUG: int count = FuzzyDRController.masterList_Households.size();
            //DEBUG: System.out.println(" ... complete. " + count + " households loaded.");
            
            //System.out.println(FuzzyDRController.masterMap_Households.size());
            //DEBUG: System.out.println(FuzzyDRController.masterMap_Households.get(43746287).getIncome()); //answer: 62500
            
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
