package edu.gmu.fuzzydr.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

//import util.ec;

public class Config {

	public static final int RANDOM_SEED = 12345;
    //public static ec.util.MersenneTwisterFast RANDOM_GENERATOR = new ec.util.MersenneTwisterFast(RANDOM_SEED);
	
    public final static String SIM_NAME = "Fuzzy Deontic Reasoning";
	public final static int WIDTH = 800;
	public final static int HEIGHT = 800;

	public static double EQ_RADIUS_EARTH_km = 6371f;
	
	public static double FFX_ASPECT_RATIO = Math.cos(Math.toRadians(38.831227));		// 38.831227, -77.274628
	
	private static final String sep = File.separator;
	private static final String timestamp = getDateTime();
	private static final String filepathPrefix = ".." + sep;
	
	/** File path location of the beneficiary population file for reading in. */
	private static final String dataFilepathPrefix = filepathPrefix + "data_HedgeVax" + sep;
	/** File path to save model output data. */
	private static final String outputFilepathPrefix = filepathPrefix + "output_HedgeVax" + sep + timestamp + sep;
	
	private static final String shapefilePath = dataFilepathPrefix + "ZipCodes_WGS84.shp";
	
	/** Filename for household population to load. */
	private static final String householdPath = dataFilepathPrefix + "households.txt";
	
	/**
	 * @return timestamp for unique output file names.
	 */
	private final static String getDateTime()  
	{  
	    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    df.setTimeZone(TimeZone.getTimeZone("EST"));  
	    return df.format(new Date());  
	}

	public static String getShapefilePath() 
	{
		//System.out.println(shapefilePath);
		return shapefilePath;
	}
	
	public static String getHouseholdPath() 
	{
		return householdPath;
	}
	
}
