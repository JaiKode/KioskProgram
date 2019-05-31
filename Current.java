package simpOld;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import javax.swing.border.*;

import simp.DataGetter;
import simp.Room;

/**
 * @author BRHS-PLTW-ST07 Nathan and Ruben
 * A slightly upgraded version of the original.
 * This one makes use of borders to look slightly better
 *
 */
public class Current {
	//HRES AND WRES ARE FLIPPED SINCE THE OTHER MONITORS WILL BE SIDEWAYS ESSENTIALLY!!!
	private final int HRES = 1920 / 2; // divided by 2 because its too big for the screen I was testing on
    private final int WRES = 1080 / 2; // divided by 2 because its too big for the screen I was testing on
    private final Color DBLUE = new Color(0, 55, 197);
    private final Color LBLUE = new Color(0, 55, 255);
    
    private JFrame frame;
    private JPanel contentPane, infoPane;
	private JPanel topPanel;
    private JLabel mapPanel;
    private JPanel searchPanel;
    private boolean mouseMoved;
    private boolean screenSaved;
    private boolean emergency;
    
    public static BufferedImage colorMap;
    
    private static ArrayList<Room> rooms;
    static Map<Color,Room> roomMap;
    
    private JLabel dateText, infoText;
    private boolean typeOfDay = true;
    static private JTextField searchBar;
    
    int hgap = 5;
    int vgap = 5;
    
    Timer 						screenSaverTimer;
    
    public Current() {
    	hgap = 5;
        vgap = 5;
        emergency = false;
    }
    
    private void displayGUI() {
    	
    	frame = new JFrame();
    	frame.setSize(WRES, HRES);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //sets up contentPane the main JPanel for everything
        contentPane = new JPanel();
        contentPane.setOpaque(true);
        contentPane.setBackground(DBLUE);
        contentPane.setBorder(BorderFactory.createEmptyBorder(hgap, hgap, hgap, hgap));
        contentPane.setLayout(new BorderLayout(hgap, vgap));
        
        //sets up topPanel the panel for the top bar containing dateText which is the date/time
        topPanel = new JPanel();
        topPanel.setOpaque(true);
        topPanel.setBackground(LBLUE);
        dateText = new JLabel("");
        dateText.setOpaque(true);
        dateText.setBackground(Color.WHITE);
        dateText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Date/Time"));
        topPanel.add(dateText);
        contentPane.add(topPanel, BorderLayout.PAGE_START);
        
        
        JButton eButton = new JButton("Emergency mode");
        eButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.err.println("EMERGENCY!!!, EMERGENCY!!!");
				
				if(!emergency) {
					BufferedImage image = null; //this grabs the map from the file
					try {
						image = ImageIO.read(new File("evacMap.jpg"));
					} catch (IOException e1) {
						e1.printStackTrace(); //always squelch
					}
					//this resizes it to fit to our size
					int mapWRes = WRES;
					int mapHRes = HRES; //HRES * 5/8 for testing purposes, a magic number that made it look better
					mapPanel.setSize(mapWRes, mapHRes * 5/8); 
					Image dimage = image.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
			        mapPanel.setIcon(new ImageIcon(dimage));
				} else {
					BufferedImage image = null; //this grabs the map from the file
					try {
						image = ImageIO.read(new File("map.png"));
					} catch (IOException e1) {
						e1.printStackTrace(); //always squelch
					}
					//this resizes it to fit to our size
					int mapWRes = WRES;
					int mapHRes = HRES; //HRES * 5/8 for testing purposes, a magic number that made it look better
					mapPanel.setSize(mapWRes, mapHRes * 5/8); 
					Image dimage = image.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
			        mapPanel.setIcon(new ImageIcon(dimage));
				}
				emergency = !emergency;
			}
        	
        });
        
        topPanel.add(eButton);
        
        //sets up mapPanel the panel that contains the map
        mapPanel = new JLabel();
        mapPanel.setOpaque(true);
        mapPanel.setBackground(Color.WHITE);
       // mapPanel.setBorder(BorderFactory.createTitledBorder("Map"));
        contentPane.add(mapPanel, BorderLayout.CENTER);
        
        BufferedImage image = null; //this grabs the map from the file
		try {
			image = ImageIO.read(new File("map.png"));
		} catch (IOException e1) {
			e1.printStackTrace(); //always squelch
		}
		//this resizes it to fit to our size
		int mapWRes = WRES;
		int mapHRes = HRES; //HRES * 5/8 for testing purposes, a magic number that made it look better
		mapPanel.setSize(mapWRes, mapHRes * 5/8); 
		Image dimage = image.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
        mapPanel.setIcon(new ImageIcon(dimage));
        
        mouseMoved = true;
        
        
        
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
			colorImage = ImageIO.read(new File("mapTouch.png"));
		} catch (IOException e1) {
			e1.printStackTrace(); //always squelch
		}
		Image colorImage2 = colorImage.getScaledInstance(mapPanel.getWidth(), mapPanel.getHeight(), Image.SCALE_SMOOTH);
		colorMap = new BufferedImage(mapPanel.getWidth(), mapPanel.getHeight(), 1);
		colorMap.getGraphics().drawImage(colorImage2, 0, 0 , null);
        
		mapPanel.addMouseListener(new MouseListener() { //this is the mouse listener that allows the map to be clicked on
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
			    System.out.println("Clicked");
				mouseMoved = true;
				screenSaverTimer.cancel();
				
				class ScreenSaverTask extends TimerTask {
		        	public void run() {
		        		blackScreen();
		        	}
		        }
				
				screenSaverTimer = new Timer();
				
				screenSaverTimer.scheduleAtFixedRate(new ScreenSaverTask(), 5000, 5000);
				refresh();
			    // display the information for this x and y
			    try {
			    	Color color = new Color(colorMap.getRGB(cursorX, cursorY));
			    	System.out.println(color.getRed());
			    	System.out.println(color.getGreen());
			    	System.out.println(color.getBlue());
            		infoText.setText(roomMap.get(color).toString());
            		infoFrame.setContentPane(infoPane);
            		infoFrame.pack();
            		infoFrame.setLocationByPlatform(true);
            		infoFrame.setVisible(true);
            		
				} catch (Exception lol) {
					System.err.println("bad");
				}
			    
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

        });
       
        infoText = new JLabel("Info Stuff goes here");
        infoText.setOpaque(true);
        infoText.setBackground(Color.WHITE);
        infoText.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Information"));
        infoPane.add(infoText);
        
        screenSaved = false;
        //sets up searchPanel, which contains the searchBar
        searchPanel = new JPanel();
        searchPanel.setOpaque(true);
        searchPanel.setBackground(LBLUE);
        searchBar = new JTextField(10); 
        searchBar.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED, Color.GRAY, Color.DARK_GRAY), "Search"));
        searchPanel.add(searchBar);
        searchBar.addActionListener(new ActionListener() { //this gets the text in the searchBar when enter is pressed, prob need to change to a button
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean searchFound = false;
				for(Room room : rooms) {
					String info = room.toString();
					
					if(info.contains(searchBar.getText())) {
						infoText.setText(info);
						searchFound = true;
					}
				}
				if(!searchFound) {			
					infoText.setText("Invalid Search");
				}
				searchBar.setText("");
				infoFrame.setContentPane(infoPane);
			    infoFrame.pack();
			    infoFrame.setLocationByPlatform(true);
			    infoFrame.setVisible(true);
			}
		});
        
        contentPane.add(searchPanel, BorderLayout.SOUTH);
        //this puts contentPane in frame and displays the whole thing
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        
        screenSaverTimer = new Timer();
        
        frame.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
				System.out.println("Moved");
				mouseMoved = true;
				screenSaverTimer.cancel();
				
				class ScreenSaverTask extends TimerTask {
		        	public void run() {
		        		blackScreen();
		        	}
		        }
				
				screenSaverTimer = new Timer();
				
				screenSaverTimer.scheduleAtFixedRate(new ScreenSaverTask(), 5000, 5000);
				refresh();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
        
        
        
        //this is the timer that controls the date and updates the date/time text
        Timer timer = new Timer();
        class UpdateTask extends TimerTask {

			@Override
			public void run() { 
				Date date = new Date(System.currentTimeMillis());
				String dateString = new SimpleDateFormat("EEEE MMMM dd, yyyy KK:MM:ss a", Locale.ENGLISH).format(date);
				dateText.setText(dateString + " " + typeOfDay());
			}
        }
        timer.scheduleAtFixedRate(new UpdateTask(), 1, 1); //currently at a millisecond delay, could change
        
        
        
//        class ScreenSaverTask extends TimerTask {
//        	public void run() {
//        		blackScreen();
//        	}
//        }
        
//        screenSaverTimer.scheduleAtFixedRate(new ScreenSaverTask(), 5000, 5000);
    }
    
    public String typeOfDay() {
    	return (typeOfDay) ? "A Day" : "B Day";
    }
    
    public void refresh() {
    	if(screenSaved) {
	    	JPanel pane = new JPanel();
	//    	frame.removeAll();
	//    	contentPane = new JPanel();
	        pane.setOpaque(true);
	        pane.setBackground(DBLUE);
	        pane.setBorder(BorderFactory.createEmptyBorder(hgap, hgap, hgap, hgap));
	        pane.setLayout(new BorderLayout(hgap, vgap));
	        
	        pane.add(topPanel, BorderLayout.PAGE_START);
	        pane.add(mapPanel, BorderLayout.CENTER);
	        pane.add(searchPanel, BorderLayout.SOUTH);
	        
	    	frame.setContentPane(pane);
			frame.repaint();
			screenSaved = false;
    	}
    }
    
    public void blackScreen() {
    	System.out.println("Screen Saver");
    	
    	if(!mouseMoved) {
    		ScreenSaver saver = new ScreenSaver();
    		frame.setContentPane(saver);
    		screenSaved = true;
    		saver.repaint();
    		frame.repaint();
    	}
    	mouseMoved = false;
    }
    
    
    //MAIN METHOD -- this runs displayGUI which contains the bulk of code. Also gets the room data from a file
    public static void main(String[] args) {
    	try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
    	
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	try {
            		Current ui = new Current();
            		DataGetter getter = new DataGetter();
            		getter.createRoomData("test.txt");
            		rooms = getter.getRooms();
            		roomMap = getter.getRoomMap();
                	ui.displayGUI();	
				} catch (FileNotFoundException e) {
					e.printStackTrace(); //squelch it
				}
            }
        });
    }

}
