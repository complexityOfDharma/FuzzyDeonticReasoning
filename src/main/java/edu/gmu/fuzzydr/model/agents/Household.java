package edu.gmu.fuzzydr.model.agents;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.controller.SimUtil;

import java.awt.Color;

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
		
		// convert lat/lon into x/y for coordinate position in MASON
		location = SimUtil.convertToXY(this.lat, this.lon);
		
		// set position in the world.
		this.setLocation(location);
		
		// set color for visualization
		//this.myColor = new Color(23, 184, 232, 1);
		this.myColor = new Color(0,0,0);
	}
	
	@Override
	public void step(SimState state) {
		this.fuzzyDRController = (FuzzyDRController) state;
		
		//TODO: get the group of assigned residents at this household to initiate and mix, expose, and infect process amongst them.
		
		//TODO: update their status and visualization of infection state.
		
		
	};
	
	public void assignModelState(FuzzyDRController state) {
		this.fuzzyDRController = (FuzzyDRController) state;
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
	
	
}
