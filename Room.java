package simp;

import java.awt.Color;
import java.util.ArrayList;

public class Room {
	private String roomNum, teacher, message;
	private String p1, p2, p3, p4, p5, p6;
	private Color color;
	private String clubs;
	
	
	public Room(String roomNum, String color, String teacher, String clubs, String p1, String p2, String p3, String p4, String p5, String p6, String message) {
		this.roomNum = roomNum;
		this.teacher = teacher;
		this.message = message;
		int red = Integer.parseInt(color.trim().substring(0, 3));
		int green = Integer.parseInt(color.substring(3, 6));
		int blue = Integer.parseInt(color.substring(6, 9));
		this.color = new Color(red, green, blue);
		this.clubs = clubs;
		//to function with 0 and 7th periods would need to change to an array and change datagetter accordingly
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.p4 = p4;
		this.p5 = p5;
		this.p6 = p6;
		
	}

	public String toString() {
		String info = "<html>";
		info += "Room Number: " + roomNum + "<br>";
		info += "Teacher: " + teacher + "<br>";
		info += "Clubs " + clubs + "<br>";
		info += "Period 1: " + p1.substring(2) + "<br>";
		info += "Period 2: " + p2.substring(2) + "<br>";
		info += "Period 3: " + p3.substring(2) + "<br>";
		info += "Period 4: " + p4.substring(2) + "<br>";
		info += "Period 5: " + p5.substring(2) + "<br>";
		info += "Period 6: " + p6.substring(2) + "<br>";
		info += "Message: " + message + "<br>";
		info += "<html>";
		return info;
	}
	
	public Color getColor() {
		return color;
	}
}
