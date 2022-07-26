package edu.gmu.fuzzydr.model.agents;

import sim.engine.SimState;
import sim.engine.Steppable;

/**
 * An abstract superclass defining an interface for all _Archetype agents that make 
 * fuzzy deontic reasoning-based decisions based on delta parameters influenced by their
 * classified social groupings.
 */
@SuppressWarnings("serial")
public abstract class Person implements Steppable {
	
	private int uniqueId;
	private static int nextId = 0;
	public final String name;

	/**
	 * Creates a new Person.
	 * @param name the non-unique String name of this Actor.
	 */
	public Person(String name) {
	    if (name == null) {
	        name = this.getClass().getName() + "Default";
	    }
	    
	    this.name = name;
	    this.uniqueId = nextId++;
	}

	/** Return this Person's unique integer id. */
	public int getId() { return uniqueId; }
	
	
	//public abstract void makeDecision() throws Exception;
	
	
	public void step(SimState state) {
		
	}
	
	
}
