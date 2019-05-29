

package simp;

import java.awt.Color;
import java.util.ArrayList;

public class Room {
	private String room_number, teacher, subject;
	private Color color;
	private ArrayList<String> clubs;
	
	
	public Room(String room_number, String teacher, String subject, String RGB, ArrayList<String> clubs) {
		this.room_number = room_number;
		this.teacher = teacher;
		this.subject = subject;
		RGB = RGB.trim();
		int red = Integer.parseInt(RGB.substring(0, 3));
		int green = Integer.parseInt(RGB.substring(3, 6));
		int blue = Integer.parseInt(RGB.substring(6, 9));
		color = new Color(red, green, blue);
		System.out.println(red);
		System.out.println(green);
		System.out.println(blue);
		this.clubs = clubs;
	}

	public String toString() {
		String info = room_number + " - " + teacher + " - " + subject + " - ";
		for(String club : clubs) {
			info += club + " - ";
		}
		return info;
		
	}
	
	//GETTERS AND SETTERS GENERATED BELOW

	/**
	 * @return the room_number
	 */
	public String getRoom_number() {
		return room_number;
	}


	/**
	 * @param room_number the room_number to set
	 */
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}


	/**
	 * @return the teacher
	 */
	public String getTeacher() {
		return teacher;
	}


	/**
	 * @param teacher the teacher to set
	 */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}


	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}


	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}


	/**
	 * @return the clubs
	 */
	public ArrayList<String> getClubs() {
		return clubs;
	}


	/**
	 * @param clubs the clubs to set
	 */
	public void setClubs(ArrayList<String> clubs) {
		this.clubs = clubs;
	}
}
