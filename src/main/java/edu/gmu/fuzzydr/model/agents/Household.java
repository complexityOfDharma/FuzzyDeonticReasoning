package edu.gmu.fuzzydr.model.agents;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;
import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.controller.SimUtil;

import java.awt.Color;
import java.util.ArrayList;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;


@SuppressWarnings("serial")
public class Household implements Steppable {

	public FuzzyDRController fuzzyDRController;
	
	public int householdID;
	public String stcotrbg;
	public int race;
	public int income;
	public double lat;
	public double lon;
	public Color myColor;
	//public boolean isInfected;
	public Status seirStatus;
	
	/** Object that specifies the household XY-coordinate position in the world. */
	public Point location;
	/** X-coordinate location of the household. */
	private double coordX = 0;
	/** Y-coordinate location of the household. */
	private double coordY = 0;
	/** Object that specifies the household XY-coordinate position in the world. */
	private Int2D coordXY;
	
	
	/**
	 * Constructor.
	 * @param id
	 */
	public Household(int id, String stcotrbg, int race, int income, double lat, double lon)
	{
		this.householdID = id;
		this.stcotrbg = stcotrbg;
		this.race = race;
		this.income = income;
		this.lat = lat;
		this.lon = lon;
		
		// convert lat/lon into x/y for coordinate position in MASON.
		location = SimUtil.convertToXY(this.lat, this.lon);
		
		// set position in the world.
		this.setLocation(location);  
		
		// set default color for visualization, assuming a susceptible state for the household.
		this.seirStatus = Status.SUSCEPTIBLE;
		this.myColor = Config.getColorSusceptible();
	}
	
	@Override
	public void step(SimState state) {
		this.fuzzyDRController = (FuzzyDRController) state;
		
		//TODO: get the group of assigned residents at this household to initiate and mix, expose, and infect process amongst them.
		// BUT HOW TO DO THIS WITHOUT CONSIDERING YOURSELF... REMOVE SELF FROM AGENT LIST AND ONLY COMPARE AGAINST OTHERS??? 
		// e.g., AND if the Agent ID is notEquals...
		
		
		// update Household status.
		// commenting out for now...     updateHouseholdStatus(state);
		updateViz();
	};
	
	public void assignModelState(FuzzyDRController state) {
		this.fuzzyDRController = (FuzzyDRController) state;
	}
	
	/**
	 * Based on status of household residents, update the Household status to Susceptible, Exposed, or Infected.
	 * @param state
	 */
	public void updateHouseholdStatus(SimState state) {
		this.fuzzyDRController = (FuzzyDRController) state;
		
		// local list for residents at a given household.
		ArrayList<Person> residents = this.fuzzyDRController.masterMap_HouseholdResidents.get(this.getHouseholdID());
		
		// if anyone is Exposed, then the household is exposed.
		for (Person p : residents) {
			if (p.getStatus().equals(Status.EXPOSED)) {
				this.setSEIRStatus(Status.EXPOSED);
				
				// break out of the loop once an Exposed resident is identified.
				break; 
			}
		}
		
		// if however any resident is infected, override the household status to Infected.
		for (Person p : residents) {
			if (p.getStatus().equals(Status.INFECTED)) {
				this.setSEIRStatus(Status.INFECTED);
				
				// break out of the loop once an Infected resident is identified.
				break;
			}
		}
		
		// TODO: IF the household (or persons?) are Recovered, color them to denote that immune for some duration of time, then reset.
		
		// if no resident is Exposed or Identified, then the Household remains in a Susceptible state.
		this.setSEIRStatus(Status.SUSCEPTIBLE);
		this.setMyColor(Config.getColorSusceptible());
		
	}
	
	public void updateViz() {
		//System.out.println("Status: " + this.seirStatus);
		switch(this.seirStatus) {
			case SUSCEPTIBLE:
				this.setMyColor(Config.getColorSusceptible());
				break;
			case EXPOSED:
				this.setMyColor(Config.getColorExposed());
				break;
			case INFECTED:
				this.setMyColor(Config.getColorInfected());
				break;
			case RECOVERED:
				this.setMyColor(Config.getColorRecovered());
				break;
		}
	}
	
	public Integer getHouseholdID() {
		return householdID;
	}
	
	public void setLocation(Point p) {
		location = p;
	}
	
	public Point getLocation() {
		return location;
	}
	
	public Geometry getGeometry() {
		return location;
	}
	
	public Integer getIncome() {
		return income;
	}
	
	public Color getMyColor() {
		return myColor;
	}
	
	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}
	
	//public boolean isInfected() {
	//	return isInfected;
	//}

	//public void setInfected(boolean isInfected) {
	//	this.isInfected = isInfected;
	//}
	
	public Status getSEIRStatus() {
		return this.seirStatus;
	}
	
	public void setSEIRStatus(Status s) {
		this.seirStatus = s;
	}
	
	
}
