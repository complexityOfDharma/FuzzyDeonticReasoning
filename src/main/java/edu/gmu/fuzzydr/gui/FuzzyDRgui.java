package edu.gmu.fuzzydr.gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.IOException;

import javax.swing.JFrame;

import edu.gmu.fuzzydr.controller.Config;
import edu.gmu.fuzzydr.controller.FuzzyDRController;
import edu.gmu.fuzzydr.model.agents.Household;
import edu.gmu.fuzzydr.model.agents.Status;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.geo.GeomVectorFieldPortrayal;
import sim.portrayal.simple.RectanglePortrayal2D;
import sim.util.geo.MasonGeometry;

public class FuzzyDRgui extends GUIState {

	public FuzzyDRController fuzzyDRController;
	public Display2D display;
	public JFrame displayFrame;
	
	private GeomVectorFieldPortrayal zipCodesPortrayal = new GeomVectorFieldPortrayal();
	private GeomVectorFieldPortrayal householdsPortrayal = new GeomVectorFieldPortrayal();
	private GeomVectorFieldPortrayal exposedHouseholdsPortrayal = new GeomVectorFieldPortrayal();
	private GeomVectorFieldPortrayal infectedHouseholdsPortrayal = new GeomVectorFieldPortrayal();
	private GeomVectorFieldPortrayal recoveredHouseholdsPortrayal = new GeomVectorFieldPortrayal();
	
	public FuzzyDRgui() throws IOException {
		super(new FuzzyDRController(Config.RANDOM_SEED));
		fuzzyDRController = (FuzzyDRController) state;
	}
	
	public FuzzyDRgui(SimState state) {
		super(state);
		fuzzyDRController = (FuzzyDRController) state;
	}
	
	/*
	public static String getName() {
		return "FuzzyDR";
	}
	
	public Object getSimulationInspectedObject() {
		return state;
	}
	*/
	
	public void start() {
		super.start();
		setupPortrayals();
		
		System.out.println("\nStarting simulation visualization...\n");
		
		//TODO: if XYSeries objects for plots, do clear them here.
	}
	
	public void load(SimState state) {
		super.load(state);
		setupPortrayals();
	}
	
	public void init(final Controller c) {
		super.init(c);
		
		display = new Display2D(Config.WIDTH, Config.HEIGHT, this);
		
		// Shape layers
		display.attach(zipCodesPortrayal, "Fairfax County Zip Codes");
		display.attach(householdsPortrayal, "Households");
		
		// TODO: figure out if we can visualize *ONLY* the exposed/infected households, or is redrawing all households 3 times every step?
		// TODO: also, can set color and scale to be object attribute driven, so maybe only need 1 inner class?
		//display.attach(exposedHouseholdsPortrayal, "Exposed Households");
		//display.attach(infectedHouseholdsPortrayal, "Infected Households");
		//display.attach(recoveredHouseholdsPortrayal, "Recovered Households");
		
		// Point layers
		displayFrame = display.createFrame();
		controller.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		
		//TODO: can make the SEIR chart visible here by default
		//TODO: c.registerFrame(createDiseaseTimeSeriesFrame());
		//TODO: diseaseTimeSeriesFrame.setVisible(true);
		
		//((Console) controller).setSize(480, 540);
	}
	
	/**
	 * Set up visualization portrayals of households and the SEIR status of those agent collectives, and not agents themselves.
	 */
	private void setupPortrayals() {
		
		//TODO: potential portrayals here for the county zip codes overlay to bound the agent households.
		// see GeoMasonCookbook, pg28, section 4.3 "Displaying Boundary Lines over a Grid Field."
		
		householdsPortrayal.setField(fuzzyDRController.householdSpace);
		householdsPortrayal.setPortrayalForAll(new HouseholdPortrayal());
		
		//exposedHouseholdsPortrayal.setField(fuzzyDRController.householdSpace);
		//exposedHouseholdsPortrayal.setPortrayalForAll(new ExposedHouseholdPortrayal());
		
		//infectedHouseholdsPortrayal.setField(fuzzyDRController.householdSpace);
		//infectedHouseholdsPortrayal.setPortrayalForAll(new RecoveredHouseholdPortrayal());
		
		//recoveredHouseholdsPortrayal.setField(fuzzyDRController.householdSpace);
		//recoveredHouseholdsPortrayal.setPortrayalForAll(new RecoveredHouseholdPortrayal());
		
		display.reset();
		display.setBackdrop(Color.WHITE);
		display.repaint();
	}
	
	public void quit() {
		super.quit();
		
		if (displayFrame != null) {
			displayFrame.dispose();
		}
		
		displayFrame = null;
		display = null;
	}
	
	public static void main(String[] args) throws IOException {
		new FuzzyDRgui().createController();
	}
	
	/** Inner class definition for the portrayal of households. */
    @SuppressWarnings("serial")
	class HouseholdPortrayal extends RectanglePortrayal2D {
    	public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
            MasonGeometry mg = (MasonGeometry) object;
    		Integer id = mg.getIntegerAttribute("householdID");

    		//Household h = fuzzyDRController.masterMap_Households.get(id);
    		Household h = FuzzyDRController.masterMap_Households.get(id);
    		
    		paint = h.getMyColor();
    		
    		//scale = 1;
    		scale = h.getMyVizScale();
    		//filled = false;
    		if (h.seirStatus != Status.SUSCEPTIBLE) {
    			filled = true;
    		} else {
    			filled = false;
    		}
    		
            super.draw(object, graphics, info);
        }
    }
	
    /** Inner class definition for the portrayal of exposed households. */
    //@SuppressWarnings("serial")
	/*
    class ExposedHouseholdPortrayal extends RectanglePortrayal2D {
    	public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
            MasonGeometry mg = (MasonGeometry) object;
    		Integer id = mg.getIntegerAttribute("householdID");

    		//Household h = fuzzyDRController.masterMap_Households.get(id);
    		Household h = FuzzyDRController.masterMap_Households.get(id);
    		
    		paint = h.getMyColor();
    		
    		if (h.getSEIRStatus() == Status.EXPOSED) {
    			scale = 5;
        		filled = true;
        		
        		// TODO: does this make this portrayal *ONLY* draw if the conditions are met?
        		super.draw(object, graphics, info);
    		}
    	}
    }
    */
    
    /** Inner class definition for the portrayal of infected households. */
    //@SuppressWarnings("serial")
	/*
    class InfectedHouseholdPortrayal extends RectanglePortrayal2D {
    	public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
            MasonGeometry mg = (MasonGeometry) object;
    		Integer id = mg.getIntegerAttribute("householdID");

    		//Household h = fuzzyDRController.masterMap_Households.get(id);
    		Household h = FuzzyDRController.masterMap_Households.get(id);
    		
    		paint = h.getMyColor();
    		
    		if (h.getSEIRStatus() == Status.INFECTED) {
    			scale = 5;
        		filled = true;
        		
        		// TODO: does this make this portrayal *ONLY* draw if the conditions are met?
                super.draw(object, graphics, info);
    		}
    	}
    }
    */
    
    /** Inner class definition for the portrayal of infected households. */
    //@SuppressWarnings("serial")
	/*
    class RecoveredHouseholdPortrayal extends RectanglePortrayal2D {
    	public final void draw(Object object, Graphics2D graphics, DrawInfo2D info)
        {
            MasonGeometry mg = (MasonGeometry) object;
    		Integer id = mg.getIntegerAttribute("householdID");

    		//Household h = fuzzyDRController.masterMap_Households.get(id);
    		Household h = FuzzyDRController.masterMap_Households.get(id);
    		
    		paint = h.getMyColor();
    		
    		if (h.getSEIRStatus() == Status.RECOVERED) {
    			scale = 5;
        		filled = true;
        		
        		// TODO: does this make this portrayal *ONLY* draw if the conditions are met?
                super.draw(object, graphics, info);
    		}
    	}
    }
    */
	
}
