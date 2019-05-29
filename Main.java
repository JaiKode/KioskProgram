

package simp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

/**
 * @author Nathan Ahmann
 * 
 * Feel free to ask me any questions about this code. 
 * 
 * This is the Final UI Code for SIMP
 * Search Team needs to implement their search functions
 * Map Team needs to implement the map
 * Touchscreen Team needs to implement the touchscreen
 * Those spots have been left with a to-do
 */
public class Main {

	public static void main(String[] args) {
		Main ui = new Main();
		ui.display();
	}
	//TODO test on a screen the actual size and make sure dimensions are working correctly
	//HRES AND WRES ARE FLIPPED SINCE THE OTHER MONITORS WILL BE SIDEWAYS ESSENTIALLY!!!
	private final int HRES = 1920 / 2; // divided by 2 because its too big for the screen I was testing on
    private final int WRES = 1080 / 2; // divided by 2 because its too big for the screen I was testing on
    
	//hgap and vgap for borders
	private int hgap = 5, vgap = 5;
	
	//colors used throughout
	private final Color DBLUE = new Color(0, 55, 197);
	private final Color LBLUE = new Color(0, 55, 255);
	
	//this was my implemented way of searching to be changed by Search Team
	private static ArrayList<Room> rooms;
    static Map<Color,Room> roomMap;
	
	//Any UI related components
    private JFrame frame, infoFrame;
	private JPanel contentPane, infoPane, topPanel, searchPanel;
	private JLabel dateText, infoText, mapPanel;
	private JTextField searchBar;
	public BufferedImage colorMap;
	
	//this gets the text in the searchBar when enter is pressed, prob need to change to a button
	private ActionListener searcher = new ActionListener() { 
//		TODO Make this functional with however Search Team handles their searching
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean search_found = false;
			String results = "<html>";
			for(Room room : rooms) {
				String info = room.toString();
				if(info.contains(searchBar.getText())) {
					results += info + "<br>";
					search_found = true;
				}
			}
			results += "</html>";
			
			if(search_found) {			
				infoText.setText(results);
			} else {
				infoText.setText("Invalid Search");
			}
			searchBar.setText("");
			infoFrame.setContentPane(infoPane);
		    infoFrame.pack();
		    infoFrame.setLocationByPlatform(true);
		    infoFrame.setVisible(true);
		}
	};
	
	//this is the mouse listener that allows the map to be clicked on
	private MouseListener mapListener = new MouseListener() { 
		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			 // get the cursor x and y
		    int cursorX = e.getX();
		    int cursorY = e.getY();
		    
		    // get the x and y in the original (not scaled image)
		    int pictureX = (int) (cursorX - hgap);
		    int pictureY = (int) (cursorY - vgap);
		    
		    // display the information for this x and y
		    //System.out.println(cursorX + " " + cursorY);
		    try {
		    	Color color = new Color(colorMap.getRGB(pictureX, pictureY));
        		infoText.setText(roomMap.get(color).getRoom_number());
			} catch (ArrayIndexOutOfBoundsException lol) {
				System.out.println("bad");
			}
		   
			infoFrame.setContentPane(infoPane);
			infoFrame.pack();
			infoFrame.setLocationByPlatform(true);
			infoFrame.setVisible(true);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
	};
	
	
	public Main() {
		//honestly display is really the main method but I didn't want everything in display
    }
	
	/**
	 * Basically the main method. Does all UI display work and sets up necessary elements
	 */
	private void display() {
		frame = new JFrame("SIMP Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //sets up contentPane the main JPanel for everything
        contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(DBLUE);
        contentPane.setBorder(BorderFactory.createEmptyBorder(hgap, hgap, hgap, hgap));
        contentPane.setLayout(new BorderLayout(hgap, vgap));
        
        //sets up topPanel the panel for the top bar containing text1 which is the date/time
        topPanel = new JPanel();
        topPanel.setOpaque(true);
        topPanel.setBackground(LBLUE);
        dateText = new JLabel("");
        dateText.setOpaque(true);
        dateText.setBackground(Color.WHITE);
        dateText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Date/Time"));
        topPanel.add(dateText);
        contentPane.add(topPanel, BorderLayout.PAGE_START);
        
        
        
        //TODO Implement map teams map and have touchscreen help verify its touchscreen functionality
        //sets up mapPanel the panel that contains the map
        mapPanel = new JLabel();
        mapPanel.setOpaque(true);
        mapPanel.setBackground(Color.WHITE);
        mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
        contentPane.add(mapPanel, BorderLayout.CENTER);
        
        BufferedImage image = null; //this grabs the map from the file
		try {
			image = ImageIO.read(new File("map.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace(); //always squelch
		}
		//this resizes it to fit to our size
		int mapWRes = WRES;
		int mapHRes = HRES; //HRES / 3 for testing purposes, a magic number that made it look better
		mapPanel.setSize(mapWRes, mapHRes / 3); 
		Image dimage = image.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
        mapPanel.setIcon(new ImageIcon(dimage));
        
        //sets up infoFrame the JFrame that pops up when a room is searched or clicked on
        JFrame infoFrame = new JFrame("Information");
        
        //sets up infoPane the panel for infoFrame
        infoPane = new JPanel();
        infoPane.setOpaque(true);
        infoPane.setBackground(DBLUE);
        infoPane.setBorder(BorderFactory.createEmptyBorder(hgap, hgap, hgap, hgap));
        infoPane.setLayout(new BorderLayout(hgap, vgap));
        
        BufferedImage colorImage = null; //this grabs the colorMap from the file
		try {
			colorImage = ImageIO.read(new File("mapColor.png"));
		} catch (IOException e1) {
			e1.printStackTrace(); //always squelch
		}
		Image colorImage2 = colorImage.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
		colorMap = new BufferedImage(mapPanel.getWidth(), mapPanel.getHeight(), 1);
		colorMap.getGraphics().drawImage(colorImage2, 0, 0 , null);
		
		mapPanel.addMouseListener(mapListener);
		
		infoText = new JLabel("Info Stuff goes here");
	    infoText.setOpaque(true);
	    infoText.setBackground(Color.WHITE);
	    infoText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Information"));
	    infoPane.add(infoText);
	            
	    //sets up searchPanel, which contains the searchBar
	    searchPanel = new JPanel();
	    searchPanel.setOpaque(true);
	    searchPanel.setBackground(LBLUE);
	    searchBar = new JTextField(10); 
	    searchBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Search"));
	    searchPanel.add(searchBar);
	    searchBar.addActionListener(searcher);
	    

        contentPane.add(searchPanel, BorderLayout.SOUTH);
        //this puts contentPane in frame and displays the whole thing
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        
        
        //this is the timer that controls the date and updates the date/time text
        Timer timer = new Timer();
        class UpdateTask extends TimerTask {
			@Override
			public void run() { 
				Date date = new Date(System.currentTimeMillis());
				String dateString = new SimpleDateFormat("EEEE MMMM dd, yyyy KK:MM:ss a", Locale.ENGLISH).format(date);
				dateText.setText(dateString);
			}
        }
        timer.scheduleAtFixedRate(new UpdateTask(), 1, 1); //currently at a millisecond delay, could change
	}
	
	/**
	 * 
	 * TODO: Everything below this point.
	 * These are the methods I have for a pseudo search system.
	 * It currently reads a formatted file and creates the data to use.
	 * Search Team either needs to make this file, or implement their method of searching
	 * which was talked about involving the school website. I can help implement that into
	 * this code if needed.
	 * 
	 * 
     * This method takes in the file and generates all the room data. Search team is possibly creating a better one
     * I think data team wanted to do something searching the school website... good luck with that
     * @param filename
     * @return an ArrayList of Rooms generated from the file
     * @throws FileNotFoundException
     */
    public static void createRoomData(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename));
		rooms = new ArrayList<Room>();
		while(in.hasNextLine()) {
			String[] current = in.nextLine().split("-");
			String roomNum = current[0];
			String teacher = current[1];
			String subject = current[2];
			String color = current[3];
			ArrayList<String> clubs = new ArrayList<String>();
			if(current.length > 3) {
				for(int i = 3; i < current.length; i++) {
					clubs.add(current[i]);
				}
			}
			rooms.add(new Room(roomNum, teacher, subject, color, clubs));
		}
	}
    
    
    public static void createRoomDataMap(String filename) throws FileNotFoundException {
		Scanner in = new Scanner(new File(filename));
		roomMap = new HashMap<Color, Room>();
		while(in.hasNextLine()) {
			String[] current = in.nextLine().split("-");
			String roomNum = current[0];
			String teacher = current[1];
			String subject = current[2];
			String RGB = current[3].trim();
			ArrayList<String> clubs = new ArrayList<String>();
			if(current.length > 3) {
				for(int i = 3; i < current.length; i++) {
					clubs.add(current[i]);
				}
			}
			int red = Integer.parseInt(RGB.substring(0, 3));
			int green = Integer.parseInt(RGB.substring(3, 6));
			int blue = Integer.parseInt(RGB.substring(6, 9));
			Color color = new Color(red, green, blue);
			roomMap.put(color, new Room(roomNum, teacher, subject, RGB, clubs));
		}
	}
    
    //CURRENTLY UNIMPLEMENTED CORRECTLY
//    public void searchRooms(String search) {
//    	boolean search_found = false;
//		String results = "<html>";
//		for(RoomOld room : rooms) {
//			String info = room.toString();
//			if(info.contains(searchBar.getText())) {
//				if(search_found = false) {
//					results += "Room Data Found:" + "<br>";
//				}
//				results += info + "<br>";
//				search_found = true;
//			}
//		}
//		results += "</html>"; //turns out JLabel operates in html
//		
//		if(search_found) {			
//			text2.setText(results);
//		} else {
//			text2.setText("Invalid Search");
//		}
//		searchBar.setText("");
//    }
//    
//    
//    public ArrayList<Club> createClubData(String filename) throws FileNotFoundException {
//		Scanner in = new Scanner(new File(filename));
//		ArrayList<Club> clubs = new ArrayList<Club>();
//		while(in.hasNextLine()) {
//			String name = in.nextLine();
//			String advisor = in.nextLine();
//			String room = in.nextLine();
//			clubs.add(new Club(name, advisor, room));
//		}
//		return clubs;
//	}
//    public String searchClubs(String search) {
//    	// TODO search through club database and return club info formatted correctly for info screen
//		return search;
//    	
//    }
       
}

