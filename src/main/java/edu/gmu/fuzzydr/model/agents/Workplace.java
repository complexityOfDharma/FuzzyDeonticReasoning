package edu.gmu.fuzzydr.model.agents;

import java.awt.Color;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

import edu.gmu.fuzzydr.controller.SimUtil;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Int2D;

@SuppressWarnings("serial")
public class Workplace implements Steppable {
	
	public int workplaceID;
	public double lat;
	public double lon;
	
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
	public Workplace(int id, double lat, double lon)
	{
		this.workplaceID = id;
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
