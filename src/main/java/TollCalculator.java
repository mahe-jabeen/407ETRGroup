/**
 * 
 */
package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * @author mahejabeen
 *
 */
public class TollCalculator {
    
	public final static double AVERAGE_RADIUS_OF_EARTH = 6371;
	public static Map<String, List<String>> parseJsonFile() {
		
		//JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
       Map<String, List<String>> interchangesList = new HashMap<String, List<String>>();	
        try (FileReader reader = new FileReader("src/interchanges.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject locationList = new JSONObject();
            locationList.putAll((Map) obj); 
             
            JSONObject locations = (JSONObject) locationList.get("locations");
            //Iterate over locations
            for(int i=1 ; i <= 50; i++) 
            {
            	JSONObject location = (JSONObject) locations.get(Integer.valueOf(i).toString());
            	if(location != null) {
	            	List<String> latLngList = new ArrayList<String>();
	            	latLngList.add(location.get("lat").toString());
	            	latLngList.add(location.get("lng").toString());            	
	            	interchangesList.put(location.get("name").toString(), latLngList);	            	
            	}
            } 
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interchangesList;
	}
	
	public static void costofTrip(String location1 , String location2) {
		 Map<String, List<String>> interchangesList = parseJsonFile();
		 List<String> location1List = interchangesList.get(location1);
		 List<String> location2List = interchangesList.get(location2);
		 if(location1List != null && location1List.size() == 2 && location2List != null && location2List.size() == 2) {
			 double lat1 = Double.valueOf(location1List.get(0));
	         double lng1 = Double.valueOf(location1List.get(1));
	         
	         double lat2 = Double.valueOf(location2List.get(0));
	         double lng2 = Double.valueOf(location2List.get(1));
	         int distance = calculateDistance(lat1, lng1, lat2, lng2);
	         System.out.println("costofTrip('"+location1+"', '"+location2+"')");
	         System.out.println("Distance: "+ distance);
	         System.out.println("Cost: "+ distance*0.25);
		 } else {
			 System.out.println("Couldn't calculate the distance and cost for given locations");
		 }
	}
	
	public static int calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

	    double latDistance = Math.toRadians(userLat - venueLat);
	    double lngDistance = Math.toRadians(userLng - venueLng);

	    double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
	                    (Math.cos(Math.toRadians(userLat))) *
	                    (Math.cos(Math.toRadians(venueLat))) *
	                    (Math.sin(lngDistance / 2)) *
	                    (Math.sin(lngDistance / 2));

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

	    return (int) (Math.round(AVERAGE_RADIUS_OF_EARTH * c));

	}

}
