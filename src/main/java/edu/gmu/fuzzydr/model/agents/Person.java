package edu.gmu.fuzzydr.model.agents;

import edu.gmu.fuzzydr.controller.FuzzyDRController;
import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * 
 */
@SuppressWarnings("serial")
public class Person implements Steppable {
	
	/*
	 consider making this an abstract superclass defining an interface for all archetypes (e.g., ideology-based)
	 that make fuzzy deontic reasoning-based decisions based on delta parameters influenced by their
	 social grouping
	*/
	
	public FuzzyDRController fuzzyDRController;
	
	private int personID;
	private int householdID;
	private int age;
	private String sex;
	private int race;
	private int relate;
	private int schoolID;
	private int workplaceID;
	
	public Status seirStatus;
	
	private boolean isQuarrantined;
	private boolean isMasked;
	private boolean isDistanced;
	private boolean isVaxxed;
	
	private int timeSinceInfected;
	
	/**
	 * Creates a new Person.
	 * @param name the non-unique String name of this Actor.
	 */
	public Person(int id, int hh_id, int age, String sex, int sch_id, int wrk_id) {
		
		// TODO: consider using other attributes for fuzzy deontic reasoning or group classification (e.g., race, gender)
		
		this.personID = id;
		this.householdID = hh_id;
		this.age = age;
		this.sex = sex;
		this.schoolID = sch_id;
		this.workplaceID = wrk_id;

		this.seirStatus = Status.SUSCEPTIBLE;
		
		this.isQuarrantined = false;
		this.isMasked = false;
		this.isDistanced = false;
		this.isVaxxed = false;
		
		this.timeSinceInfected = 0;
		
		// TODO: assign any p(vax effectiveness, masking, distancing, quarrantining, vaxxing, etc).
		
	}

	
	
	
	public void step(SimState state) {
		
		
		
		
		
		
		
		
		
	}
	
	public Status getStatus() {
		return this.seirStatus;
	}
	
	public int getHousehold() {
		return this.householdID;
	}
	
	public int getSchool() {
		return this.schoolID;
	}
	
	public int getWorkplace() {
		return this.workplaceID;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public String getGender() {
		return this.sex;
	}

	public boolean isQuarrantined() {
		return isQuarrantined;
	}

	public void setQuarrantined(boolean isQuarrantined) {
		this.isQuarrantined = isQuarrantined;
	}

	public boolean isMasked() {
		return isMasked;
	}

	public void setMasked(boolean isMasked) {
		this.isMasked = isMasked;
	}

	public boolean isDistanced() {
		return isDistanced;
	}

	public void setDistanced(boolean isDistanced) {
		this.isDistanced = isDistanced;
	}

	public boolean isVaxxed() {
		return isVaxxed;
	}

	public void setVaxxed(boolean isVaxxed) {
		this.isVaxxed = isVaxxed;
	}
	
}
