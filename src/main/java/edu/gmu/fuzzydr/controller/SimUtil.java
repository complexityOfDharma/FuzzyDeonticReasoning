package edu.gmu.fuzzydr.controller;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class SimUtil {
	
	/** Null constructor. */
	public SimUtil() {};
	
	public static Point convertToXY(double lat, double lon)
	{
		/*
		APPROXIMATIONS FOR SMALL AREAS (https://stackoverflow.com/questions/16266809/convert-from-latitude-longitude-to-x-y)
		
		If the part of the surface of the earth which you want to draw is relatively small, then you can use a very simple approximation. 
		You can simply use the horizontal axis x to denote longitude (lambda), the vertical axis y to denote latitude, lat. The ratio between these 
		should not be 1:1, though. Instead you should use cos(center_lat) as the aspect ratio, where center_lat denotes a latitude close to the center of 
		your map. Furthermore, to convert from angles (measured in radians) to lengths, you multiply by the radius of the earth (which in 
		this model is assumed to be a sphere).

		x = r * (lon) * cos(exemplar_lat_near center)
		y = r * lat
		
		This is simple equirectangular projection. In most cases, you'll be able to compute cos(center_lat) only once, which makes subsequent 
		computations of large numbers of points really cheap.
		
		For between two lat/lon points:  http://www.movable-type.co.uk/scripts/latlong.html
		*/
		
		GeometryFactory gf = new GeometryFactory();
		
		double LAT_rad = Math.toRadians(lat);
		double LON_rad = Math.toRadians(lon);
		
		double newX = Config.EQ_RADIUS_EARTH_km * LON_rad * Config.FFX_ASPECT_RATIO;
		double newY = Config.EQ_RADIUS_EARTH_km * LAT_rad;
		
		Point p = gf.createPoint(new Coordinate(Math.toDegrees(newX), Math.toDegrees(newY)));
		
		//Point p = gf.createPoint(new Coordinate(lon, lat));
		//Point nextWaypoint = gf.createPoint(new Coordinate(Math.toDegrees(nextLON), Math.toDegrees(nextLAT)));
		
		return p;
	}

}
