package edu.gmu.fuzzydr.controller;

import java.util.ArrayList;
import java.util.HashMap;

import edu.gmu.fuzzydr.model.agents.Household;
//import agents.Household;
//import model.GeomVectorField;
import sim.engine.SimState;
//import util.ModelConfigUtil;
import sim.field.geo.GeomVectorField;

@SuppressWarnings("serial")
public class FuzzyDRController extends SimState {

	public GeomVectorField zipCodeSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	public GeomVectorField householdSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	
    public static ArrayList<Household> masterList_Households = new ArrayList<Household>();
    public static HashMap<Integer, Household> masterMap_Households = new HashMap<Integer, Household>();
	public FuzzyDRController() { super(0); };
	
	/**
	 * Simulation main.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}
	
}
