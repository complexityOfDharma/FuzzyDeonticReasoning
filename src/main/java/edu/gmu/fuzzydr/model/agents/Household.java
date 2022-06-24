package edu.gmu.fuzzydr.model.agents;

import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;
import edu.gmu.fuzzydr.controller.SimUtil;

import java.awt.Color;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;


public class Household implements Steppable {

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
	}
	

	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		this.myColor = new Color(95, 158, 160, 150);
		
	};
	
	
	
	public void setLocation(Point p) 
	{
		location = p;
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public Geometry getGeometry()
	{
		return location;
	}
	
	
}
