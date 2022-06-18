/*
 * FuzzyDR: An agent-based model of institutions and individual deontic reasoning related to compliance
 * with institutions via a fuzzy logic approach.
 * 
 * @author Brant Horio (2022). George Mason University
 * 
 */

package edu.gmu.fuzzydr.controller;

import java.util.ArrayList;
import java.util.HashMap;

import edu.gmu.fuzzydr.model.agents.Household;
import sim.engine.SimState;
import sim.field.geo.GeomVectorField;

@SuppressWarnings("serial")
public class FuzzyDRController extends SimState {

	public GeomVectorField zipCodeSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	public GeomVectorField householdSpace = new GeomVectorField(Config.WIDTH, Config.HEIGHT);
	
    public static ArrayList<Household> masterList_Households = new ArrayList<Household>();
    public static HashMap<Integer, Household> masterMap_Households = new HashMap<Integer, Household>();
	
    /**
     * Default constructor.
     */
    public FuzzyDRController() { super(0); };
	
	/**
	 * Simulation main.
	 * @param args
	 */
	public static void main(String[] args) {
		
		
	}
	
}
