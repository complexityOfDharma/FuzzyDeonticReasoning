package edu.gmu.fuzzydr.controller;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Config {

	public static final int RANDOM_SEED = 12345;
    public static ec.util.MersenneTwisterFast RANDOM_GENERATOR = new ec.util.MersenneTwisterFast(RANDOM_SEED);
	
    public final static String SIM_NAME = "Fuzzy Deontic Reasoning";
    
	public final static int WIDTH = 800;
	public final static int HEIGHT = 800;

	public static double EQ_RADIUS_EARTH_km = 6371f;
	public static double FFX_ASPECT_RATIO = Math.cos(Math.toRadians(38.831227));		// 38.831227, -77.274628
	
	// NOTE: text file path names DO NOT require preceding slash.
	private static final String householdResourcePath = "edu/gmu/fuzzydr/resources/synthpop/households.txt";
	private static final String personResourcePath = "edu/gmu/fuzzydr/resources/synthpop/people.txt";
	private static final String workplaceResourcePath = "edu/gmu/fuzzydr/resources/synthpop/workplaces.txt";
	private static final String schoolResourcePath = "edu/gmu/fuzzydr/resources/synthpop/schools.txt";
		
	// NOTE: shape file path names require preceding slash.
	private static final String shapefileResourcePath = "/edu/gmu/fuzzydr/resources/locations/ZipCodes_WGS84.shp";
	private static final String shapefileDbResourcePath = "/edu/gmu/fuzzydr/resources/locations/ZipCodes_WGS84.dbf";
	
	private static final String timestamp = getDateTime();
	
	private static final Color colorSusceptible = new Color(23, 184, 232, 50);   // teal (#17B8E8).
	private static final Color colorExposed = new Color(79, 232, 23, 300);        // lime (#4FE817).
	private static final Color colorInfected = new Color(232, 71, 23, 200);       // orange (#E84717).
	private static final Color colorRecovered = new Color(176, 23, 232, 200);     // purple (#B017E8).
	
	/**
	 * @return timestamp for unique output file names.
	 */
	private final static String getDateTime() {  
	    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    df.setTimeZone(TimeZone.getTimeZone("EST"));  
	    return df.format(new Date());  
	}

	public static String getHouseholdPath() {
		return householdResourcePath;
	}
	
	public static String getWorkplacePath() {
		return workplaceResourcePath;
	}
	
	public static String getSchoolsPath() {
		return schoolResourcePath;
	}
	
	public static String getPersonPath() {
		return personResourcePath;
	}
	
	public static String getShapefileResourcePath() {
		return shapefileResourcePath;
	}
	
	public static String getShapefileDbResourcePath() {
		return shapefileDbResourcePath;
	}
	
	public static Color getColorSusceptible() {
		return colorSusceptible;
	}

	public static Color getColorExposed() {
		return colorExposed;
	}

	public static Color getColorInfected() {
		return colorInfected;
	}

	public static Color getColorRecovered() {
		return colorRecovered;
	}

	/**
	 * Test.
	 * @param args
	 * @throws IOException
	 */
	public static void main(final String[] args) throws IOException 
    {
		System.out.println(getShapefileResourcePath());
    }
	
}
