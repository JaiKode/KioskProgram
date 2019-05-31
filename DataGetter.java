package simp;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Nathan Ahmann
 * Search team never gave me an alternate searching method
 *
 */
public class DataGetter {
	private ArrayList<Room> rooms;
	private Map<Color,Room> roomMap;
	
	/**
     * This method takes in the file and generates all the room data.
     * @param filename
     * @return VOID but makes an ArrayList of Rooms and a Map of rooms to colors generated from the file
     * @throws FileNotFoundException
     */
    public void createRoomData(String filename) throws FileNotFoundException {
    	rooms = new ArrayList<Room>();
    	try (Scanner in = new Scanner(new File(filename))) {
    	    String line;
    	    String[] info = new String[11];
    	    while(in.hasNextLine()) {
    	    	
    	    	for(int lineNum = 0; lineNum < 11; lineNum++) {
    	    			line = in.nextLine();
    		    		info[lineNum] = line;
    		    		if(lineNum == 10) {
    		    			rooms.add(new Room(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8], info[9], info[10]));
    		    		}
    	    	}
    	    }
    	    roomMap = new HashMap<Color, Room>();
    	    for(Room room : rooms) {
    	    	roomMap.put(room.getColor(), room);
    	    }
    	    
    	   
    	} catch (IOException e) {
    		System.err.println("Error in filename for Room Data");
			e.printStackTrace();
		}
	}
    
    
    
    /**
	 * @return the rooms
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}


	/**
	 * @return the roomMap
	 */
	public Map<Color, Room> getRoomMap() {
		return roomMap;
	}
}
