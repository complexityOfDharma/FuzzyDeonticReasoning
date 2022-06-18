package edu.gmu.fuzzydr.model.agents;


/**
 * An abstract superclass defining an interface for all Persona agents that make 
 * fuzzy deontic reasoning-based decisions.
 */
public abstract class Person {
	
	private int uniqueId;
	private static int nextId = 0;
	public final String name;

	/**
	 * Creates a new Actor.
	 * @param name the non-unique String name of this Actor.
	 */
	public Person(String name) {
	    if (name == null) {
	        name = this.getClass().getName() + "Default";
	    }
	    
	    this.name = name;
	    this.uniqueId = nextId++;
	}

	/** Return this Actor's unique integer id. */
	public int getId() { return uniqueId; }
	
	/**
	 * 
	 * @throws Exception
	 */
	public abstract void makeDecision() throws Exception;
	
	
	
}
