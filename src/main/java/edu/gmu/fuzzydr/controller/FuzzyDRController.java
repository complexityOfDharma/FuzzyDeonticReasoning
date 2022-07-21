/*
 * FuzzyDR: An agent-based model of institutions and individual deontic reasoning related to compliance
 * with institutions via a fuzzy logic approach.
 * 
 * @author Brant Horio (2022). George Mason University
 * 
 */

package edu.gmu.fuzzydr.controller;

//import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import com.vividsolutions.jts.geom.Envelope;

import edu.gmu.fuzzydr.model.agents.Household;
import edu.gmu.fuzzydr.model.agents.School;
import edu.gmu.fuzzydr.model.agents.Workplace;
import sim.engine.SimState;
import sim.field.geo.GeomVectorField;
import sim.io.geo.ShapeFileImporter;

@SuppressWarnings("serial")
public class FuzzyDRController extends SimState {

	public GeomVectorField zipCodeSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	public GeomVectorField householdSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	
    public static ArrayList<Household> masterList_Households = new ArrayList<Household>();
    public static HashMap<Integer, Household> masterMap_Households = new HashMap<Integer, Household>();
	
    public static ArrayList<Workplace> masterList_Workplaces = new ArrayList<Workplace>();
    public static HashMap<Integer, Workplace> masterMap_Workplaces = new HashMap<Integer, Workplace>();
    
    public static ArrayList<School> masterList_Schools = new ArrayList<School>();
    public static HashMap<Integer, School> masterMap_Schools = new HashMap<Integer, School>();
    
    /**
     * Default constructor.
     */
    public FuzzyDRController() { super(0); };
	
    
    public FuzzyDRController(long seed) {
    	super(seed);
    	
    	readShapefileData();
    	initialize();
    	
    }
    
    private void readShapefileData()
    {
        try
        {
        	System.out.println("Reading shapefiles... ");
        	
            // read the data
        	ShapeFileImporter.read(
        			FuzzyDRController.class.getResource(Config.getShapefileResourcePath()), 
        			FuzzyDRController.class.getResource(Config.getShapefileDbResourcePath()), 
        			zipCodeSpace);
        	            
            System.out.println("... shapefiles loaded, setting up MBR.");
            // Make all the bounding rectangles match one another
            Envelope MBR = zipCodeSpace.getMBR();
            /////MBR.expandToInclude(householdSpace.getMBR());
            
            zipCodeSpace.setMBR(MBR);
            /////householdSpace.setMBR(MBR);
            
            System.out.println("... MBR set.");
            System.out.println("");
        } 
        catch (Exception ex)
        {
        	System.out.println(ex.getCause());
        	//ex.printStackTrace();
        	
            System.out.println("... ERROR: problem opening shapefile!" + ex);
            System.exit(-1);
        }
    }
    
    private void initialize() {
    	
    	
    	
    }
    
    
    
	/**
	 * Simulation main.
	 * @param args
	 */
	public static void main(String[] args) {
		doLoop(FuzzyDRController.class, args);
		System.exit(0);
		
	}
	
}
