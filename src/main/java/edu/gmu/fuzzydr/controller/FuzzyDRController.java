/*
 * FuzzyDR: An agent-based model of institutions and application of fuzzy logic for modeling
 * individual deontic reasoning. A geospatial contagion model for COVID is presented with 
 * heterogeneous patterns of life between households, schools, workplaces, and gathering locations.
 * The model provides the basis for computationally exploring the modeling of individual compliance
 * with institutions of nonpharmaceutical intervention containment strategies.  
 * 
 * @author Brant Horio (2023). George Mason University
 * 
 */

package edu.gmu.fuzzydr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.vividsolutions.jts.geom.Envelope;

import edu.gmu.fuzzydr.loaders.HouseholdLoader;
import edu.gmu.fuzzydr.model.agents.Household;
import edu.gmu.fuzzydr.model.agents.School;
import edu.gmu.fuzzydr.model.agents.Workplace;
import sim.engine.SimState;
import sim.field.geo.GeomVectorField;
import sim.io.geo.ShapeFileImporter;
import sim.util.geo.MasonGeometry;

@SuppressWarnings("serial")
public class FuzzyDRController extends SimState {	
	
	public static Envelope MBR;
    
    public GeomVectorField zipCodeSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
    public GeomVectorField householdSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
    
    public static ArrayList<Household> masterList_Households = new ArrayList<Household>();
    public static ArrayList<Household> getMasterList_Households() { return masterList_Households; }
    public static HashMap<Integer, Household> masterMap_Households = new HashMap<Integer, Household>();
    public static HashMap<Integer, Household> getMasterMap_Households() { return masterMap_Households; }
    
    public static ArrayList<Workplace> masterList_Workplaces = new ArrayList<Workplace>();
    public static HashMap<Integer, Workplace> masterMap_Workplaces = new HashMap<Integer, Workplace>();
    
    public static ArrayList<School> masterList_Schools = new ArrayList<School>();
    public static HashMap<Integer, School> masterMap_Schools = new HashMap<Integer, School>();
    
    public int count_SUSCEPTIBLE = 0;
    public int count_EXPOSED = 0;
    public int count_INFECTED = 0;
    public int count_RECOVERED = 0;
    
    /**
     * Default constructor.
     */
    public FuzzyDRController() { super(0); };
	
    
    public FuzzyDRController(long seed) throws IOException {
    	super(seed);
    	
    	readShapefileData();
    	
    	System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    	System.out.println("FuzzyDR: AGENT-BASED INSTITUTIONAL MODELING AND FUZZY DEONTIC REASONING");
    	System.out.println("Exploring Institutional Compliance with COVID Containment Strategies through Nonpharmaceutical Interventions");
    	System.out.println("");
    	System.out.println("@author: Brant Horio, George Mason University, 2023");
    	System.out.println("");
    	System.out.println("Starting simulation --- Scenario: ");    // + ModelConfigUtil.scenario + " ___");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        
    	initialize();
    }
    
    private void readShapefileData()
    {
        try
        {
        	//System.out.println("Reading shapefiles... // ");
        	
            // read the data
        	ShapeFileImporter.read(
        			FuzzyDRController.class.getResource(Config.getShapefileResourcePath()), 
        			FuzzyDRController.class.getResource(Config.getShapefileDbResourcePath()), 
        			zipCodeSpace);
        	            
            System.out.println("");
        	System.out.print("Reading shapefiles... // ... shapefiles loaded, setting up MBR... // ");
            // Make all the bounding rectangles match one another
            MBR = zipCodeSpace.getMBR();
            /////MBR.expandToInclude(householdSpace.getMBR());
            
            zipCodeSpace.setMBR(MBR);
            householdSpace.setMBR(MBR);
            
            System.out.println("... MBR set.");
        } 
        catch (Exception ex)
        {
        	System.out.println(ex.getCause());
        	//ex.printStackTrace();
        	
            System.out.println("... ERROR: problem opening shapefile!" + ex);
            System.exit(-1);
        }
    }
    
    private void initialize() throws IOException {
    	// clear GeomVectorFields
    	zipCodeSpace.clear();
    	householdSpace.clear();
    	
    	// clear data collections.
    	masterList_Households.clear();
    	masterMap_Households.clear();
        
        masterList_Schools.clear(); 
        masterMap_Schools.clear();
        
        masterList_Workplaces.clear(); 
        masterMap_Workplaces.clear();
    	
        // clear output collection.
        // CLEAR SEIR WRITER GOES HERE...
    	
    	instantiateHouseholds();
    	instantiateWorkplaces();
    	instantiateSchools();
    	instantiateAgentPopulation();
    	
    }
    
    
    private void instantiateHouseholds() throws IOException {
    	
    	HouseholdLoader hl = new HouseholdLoader();
    	hl.loadHouseholds(Config.getHouseholdPath());
    	
    	for (Household h : masterList_Households) {
    		
    		h.assignModelState(this);
    		MasonGeometry mg = new MasonGeometry(h.getGeometry());
    		mg.addAttribute("householdID", h.getHouseholdID());
    		householdSpace.addGeometry(mg);
    		
    		//householdSpace.addGeometry(new MasonGeometry(h.getGeometry()));
    	}
    	
    	System.out.println("... household instantiation complete: " + masterList_Households.size() + " households.");
    }
    
    private void instantiateWorkplaces() {
    	
    }

    private void instantiateSchools() {
	
	}
	
	private void instantiateAgentPopulation() {
		
	}
    
	
	public void start() {
		super.start();
		
		for (Household h : masterList_Households) {
			schedule.scheduleRepeating(h, 0, 1.0);
		}
		
		//DEBUG: System.out.println("All households added to simulation schedule.");
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
