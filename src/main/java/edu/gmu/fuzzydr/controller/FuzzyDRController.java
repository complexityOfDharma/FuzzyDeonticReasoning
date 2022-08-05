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
import edu.gmu.fuzzydr.loaders.PersonLoader;
import edu.gmu.fuzzydr.loaders.SchoolLoader;
import edu.gmu.fuzzydr.loaders.WorkplaceLoader;
import edu.gmu.fuzzydr.model.agents.Household;
import edu.gmu.fuzzydr.model.agents.Person;
import edu.gmu.fuzzydr.model.agents.School;
import edu.gmu.fuzzydr.model.agents.Status;
import edu.gmu.fuzzydr.model.agents.Workplace;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.geo.GeomVectorField;
import sim.io.geo.ShapeFileImporter;
import sim.util.geo.MasonGeometry;

@SuppressWarnings("serial")
public class FuzzyDRController extends SimState {	
	
	public static Envelope MBR;
    
    public GeomVectorField zipCodeSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
    public GeomVectorField householdSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
    
    public static ArrayList<Household> masterList_Households = new ArrayList<Household>();
    public static HashMap<Integer, Household> masterMap_Households = new HashMap<Integer, Household>();
    public static HashMap<Integer, ArrayList<Person>> masterMap_HouseholdResidents = new HashMap<Integer, ArrayList<Person>>();
    
    public static ArrayList<Person> masterList_Persons = new ArrayList<Person>();
    public static HashMap<Integer, Person> masterMap_Persons = new HashMap<Integer, Person>();
    
    public static ArrayList<School> masterList_Schools = new ArrayList<School>();
    public static HashMap<Integer, School> masterMap_Schools = new HashMap<Integer, School>();
    public static HashMap<Integer, ArrayList<Person>> masterMap_SchoolStudents = new HashMap<Integer, ArrayList<Person>>(); 
    
    public static ArrayList<Workplace> masterList_Workplaces = new ArrayList<Workplace>();
    public static HashMap<Integer, Workplace> masterMap_Workplaces = new HashMap<Integer, Workplace>();
    public static HashMap<Integer, ArrayList<Person>> masterMap_WorkplaceEmployees = new HashMap<Integer, ArrayList<Person>>();
    
    // outbreak parameters.
    public int initialInfections = 10000;
    public int timeStepToStartInfection = 3;
    
    // SEIR counts.
    public int countSusceptible = 0;
    public int countExposed = 0;
    public int countInfected = 0;
    public int countRecovered = 0;
    
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
        	// read the data.
        	ShapeFileImporter.read(
        			FuzzyDRController.class.getResource(Config.getShapefileResourcePath()), 
        			FuzzyDRController.class.getResource(Config.getShapefileDbResourcePath()), 
        			zipCodeSpace);
        	            
            System.out.println("");
        	System.out.print("Reading shapefiles... // ... shapefiles loaded, setting up MBR... // ");
            
        	// Make all the bounding rectangles match one another.
            MBR = zipCodeSpace.getMBR();
            
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
    	// clear GeomVectorFields.
    	zipCodeSpace.clear();
    	householdSpace.clear();
    	
    	// clear data collections.
    	masterList_Households.clear();
    	masterMap_Households.clear();
        
    	masterList_Persons.clear(); 
        masterMap_Persons.clear();
    	
        masterList_Schools.clear(); 
        masterMap_Schools.clear();
        
        masterList_Workplaces.clear(); 
        masterMap_Workplaces.clear();
    	
        // clear output collection.
        // SEIRWriter.seirOutputs.clear();
    	
        // instantiate model objects.
    	instantiateHouseholds();
    	instantiateAgentPopulation();
    	instantiateSchools();
    	instantiateWorkplaces();
    	
    	mapAllAgents();
    }
    
    
    private void instantiateHouseholds() throws IOException {
    	
    	HouseholdLoader hL = new HouseholdLoader();
    	hL.loadHouseholds(Config.getHouseholdPath());
    	
    	for (Household h : masterList_Households) {
    		
    		h.assignModelState(this);
    		MasonGeometry mg = new MasonGeometry(h.getGeometry());
    		mg.addAttribute("householdID", h.getHouseholdID());
    		householdSpace.addGeometry(mg);
    		
    		//householdSpace.addGeometry(new MasonGeometry(h.getGeometry()));
    	}
    	
    	System.out.println("... households instantiation complete: " + masterList_Households.size() + " households.");
    }
    
	private void instantiateAgentPopulation() throws IOException {
		
		PersonLoader sL = new PersonLoader();
    	sL.loadPeople(Config.getPersonPath());
    	
    	// TODO: for (Person p : masterList_Persons) { p.assignModelState(this); a.assignProbabilities(this); }
    	
    	System.out.println("... people instantiation complete: " + masterList_Persons.size() + " people.");
		
	}
	
    private void instantiateSchools() throws IOException {
    	
    	SchoolLoader sL = new SchoolLoader();
    	sL.loadSchools(Config.getSchoolsPath());
    	
    	System.out.println("... schools instantiation complete: " + masterList_Schools.size() + " schools.");
	}
    
	private void instantiateWorkplaces() throws IOException {
    	
    	WorkplaceLoader hl = new WorkplaceLoader();
    	hl.loadWorkplaces(Config.getWorkplacePath());
    	
    	System.out.println("... workplaces instantiation complete: " + masterList_Workplaces.size() + " workplaces.");
    }

	private void mapAllAgents() {
		System.out.println("");
		System.out.println("Assigning all agents to households, schools, and workplaces...");
		
		// map persons as household residents.
		for (Person p : masterList_Persons) {
			int hhID = p.getHousehold();
			
			if (masterMap_HouseholdResidents.containsKey(hhID)) {
				ArrayList<Person> _residents = masterMap_HouseholdResidents.get(hhID);
				_residents.add(p);
			} else {
				ArrayList<Person> _residents = new ArrayList<Person>();
				_residents.add(p);
				masterMap_HouseholdResidents.put(hhID, _residents);
			}
		}
		System.out.println("   ... all persons mapped to households.");
		
		
		// map persons as school students.
		for (Person p : masterList_Persons) {
			int schID = p.getSchool();
			
			// if the person is a youth and going to school.
			if (schID != 0) {
				if (masterMap_SchoolStudents.containsKey(schID)) {
					ArrayList<Person> _students = masterMap_SchoolStudents.get(schID);
					_students.add(p);
				} else {
					ArrayList<Person> _students = new ArrayList<Person>();
					_students.add(p);
					masterMap_SchoolStudents.put(schID, _students);
				}
			}
		}
		System.out.println("   ... all persons mapped to schools.");
		
		
		// map persons as workplace employees.
		for (Person p : masterList_Persons) {
			int wrkID = p.getWorkplace();
			
			// if the person has a job and goes to a workplace.
			if (wrkID != 0) {
				if (masterMap_WorkplaceEmployees.containsKey(wrkID)) {
					ArrayList<Person> _employees = masterMap_WorkplaceEmployees.get(wrkID);
					_employees.add(p);
				} else {
					ArrayList<Person> _employees = new ArrayList<Person>();
					_employees.add(p);
					masterMap_WorkplaceEmployees.put(wrkID, _employees);
				}
			}
		}
		System.out.println("   ... all persons mapped to workplaces.");
	}
	
	/**
	 * Spike the population with infected individuals to start the outbreak.
	 */
	public void seedInfection(SimState state) {
		System.out.println("\nSeeding infections within the population...\n");
		
		Person p;
		
		// randomly seed infection(s).
		for (int i = 0; i < initialInfections; i++) {
			do {  // do-loop to prevent seeded infection agent from being randomly selected again.
				p = masterList_Persons.get(random.nextInt(masterList_Persons.size()));
			} while (p.getStatus() != Status.SUSCEPTIBLE);
			
			p.setStatus(Status.INFECTED);
		}
		
		// appropriately update status of impacted households.
		for (Household h : masterList_Households) {
			h.updateHouseholdStatus(state);
			h.updateViz();
		}
		
	}
		
	public void start() {
		super.start();
		
		// TODO: seed the infection... consider on a delay versus t=0.
		
		for (Person p : masterList_Persons) {
			schedule.scheduleRepeating(p, 0, 1.0);
		}
		
		for (School s : masterList_Schools) {
			schedule.scheduleRepeating(s, 1, 1.0);
		}
		
		for (Workplace w : masterList_Workplaces) {
			schedule.scheduleRepeating(w, 2, 1.0);
		}
		
		for (Household h : masterList_Households) {
			schedule.scheduleRepeating(h, 3, 1.0);
		}
		
		// start the initial infection at specified time step.
		schedule.scheduleOnce(timeStepToStartInfection, 4, new Steppable() {
			public void step(SimState state) {
				seedInfection(state);
			}
		});
		
		// implement containment strategies as authority-driven institutions.
		schedule.scheduleRepeating(0, 5, new Steppable() {
			public void step(SimState state) {
				
				// TODO: implement some time phased containment institutions, triggered by Step count or something similar.
				
			}
		});
		
		// terminate if end conditions are met.
		schedule.scheduleRepeating(0, 6, new Steppable() {
			public void step(SimState state) {
				countSusceptible = 0;
				countExposed = 0;
				countInfected = 0;
				countRecovered = 0;
				
				for (Person p : masterList_Persons) {
					switch (p.getStatus()) {
						case SUSCEPTIBLE:
							countSusceptible++;
							break;
						case EXPOSED:
							countExposed++;
							break;
						case INFECTED:
							countInfected++;
							break;
						case RECOVERED:
							countRecovered++;
							break;
					}
				}
				
				System.out.println("Day " + state.schedule.getSteps() + " outbreak statistics --- "
						+ " S: " + countSusceptible
						+ ", E: " + countExposed
						+ ", I: " + countInfected
						+ ", R: " + countRecovered
						);
				
				// TODO: append-write the day's statistics to the SEIR output file. 
				
				if ((state.schedule.getSteps() > 20) && (countInfected == 0) && (countRecovered == 0)) {
					System.out.println("");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("Simulation terminated... no new infections.");
                    state.kill();
				}
				
				// TODO: can also update these repeating schedules to account for all of Households status update
				
				
				// DEBUG prints...
				int S_homes = 0;
				int E_homes = 0;
				int I_homes = 0;
				int R_homes = 0;
				for (Household h : masterList_Households) {
					if (h.getSEIRStatus() == Status.SUSCEPTIBLE) { S_homes++; }
					if (h.getSEIRStatus() == Status.EXPOSED) { E_homes++; }
					if (h.getSEIRStatus() == Status.INFECTED) { I_homes++; }
					if (h.getSEIRStatus() == Status.RECOVERED) { R_homes++; }
				}
				System.out.println("DEBUG:  Households S: " + S_homes + ", E: " + E_homes + ", I: "+ I_homes + ", R: " + R_homes);
			}
		});
	}
	
	public void finish() {
		super.finish();
		
		// TODO: generate the final output file of results.
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
