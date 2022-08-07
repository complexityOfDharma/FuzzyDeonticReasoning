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
	private int timeSinceInfected;
	
	// state variables go here for vaccination, or other booleans to indicate compliance w/ NPIs
	private boolean isQuarrantined;
	private boolean isMasked;
	private boolean isDistanced;
	private boolean isVaxxed;
		
	
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
		// e.g., if age < XXX, then CDC-specified VE
		
		// TODO: assign this person to some kind of persona/archetype, may require additional data by Census tract (via lat/lon), e.g., politics
		
		// TODO: stuff here to assign fuzzy logic behaviors based on persona
	}
	
	public void assignModelState(FuzzyDRController state) {
		this.fuzzyDRController = (FuzzyDRController) state;
	}
	
	/**
	 * Process by which an individual determines if their exposure results in successful transmission of the infection. Assumes that
	 * Person agents calling this method are all of the 'susceptible' status.
	 */
	public void expose() {
		
		// already sick or assumes self immunity from a prior infection.
		if (seirStatus != Status.SUSCEPTIBLE) { return; }   
		
		// else, Person is possible to infect.
		double transmissionChance = fuzzyDRController.random.nextDouble();
		
		// roll against transmission probability.
		if (transmissionChance <= FuzzyDRController.pTransmission ) {
			
			// successful transmission to the agent.
			this.infect();
			
			// TODO: logic here for introducing a protectionChance and prevent infection.
			
		}
		
	}
	
	/**
	 * Person's state is changed to 'exposed' after being infected.
	 */
	public void infect() {
		
		// TODO: this process of expose() then infect() can be entered if Person is EXPOSED or INFECTED. so if infected, does clock and state reset?
		this.setStatus(Status.EXPOSED);
		this.timeSinceInfected = 0;
		
	}
	
	public void step(SimState state) {
		
		switch(seirStatus) {
		
			case SUSCEPTIBLE:
				// TODO: FuzzyDR for NPI choices.
				
				break;   // do nothing.
				
			case EXPOSED:
				// TODO: FuzzyDR for NPI choices.
				
				this.timeSinceInfected++;
				
				// if incubation period is over, change status to fully infected.
				if (this.timeSinceInfected > FuzzyDRController.incubationPeriod) {
					this.setStatus(Status.INFECTED);
				}
				break;
				
			case INFECTED:
				// TODO: FuzzyDR for NPI choices.
				
				this.timeSinceInfected++;
				
				if (this.timeSinceInfected > FuzzyDRController.infectionDuration) {
					this.setStatus(Status.RECOVERED);
				}
				break;
				
			case RECOVERED:
				// TODO: FuzzyDR for NPI choices.
				
				this.timeSinceInfected++;
				break;
		}
	}
	
	public int getPersonID() {
		return this.personID;
	}
	
	public Status getStatus() {
		return this.seirStatus;
	}
	
	public void setStatus(Status status) {
		this.seirStatus = status;
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
