package simpOld;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenSaver extends JPanel {
	private int current = 0;
	private double x = 700;
	private double y = 500;
	private double afk = 0;
	private double t = 300;
	private double dx = .5, dy = .5;
	private int scale = 500;
	private final int numberOfImages = 26;
	private BufferedImage picture, img;
	private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
	
	public ScreenSaver() {
//		addKeyListener(new TAdapter());
		setBackground(Color.BLACK);
		setFocusable(true);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		loadImages();
		JLabel photo = new JLabel();
		photo.setIcon(new ImageIcon(picture));
		this.add(photo);
	}
	
	private void loadImages() {
		try {
			picture = ImageIO.read(new File("map.png"));
		} catch (IOException e1) {
			System.out.println("Invalid File Name");
		}
		
//		Image pic = picture.getScaledInstance(500, 800, Image.SCALE_SMOOTH);
//		
//		JLabel thing = new JLabel();
//		thing.setIcon(new ImageIcon(pic));
//		
//		add(thing);
//		for (int i = 1; i <= numberOfImages; i++) {
//			try {
//				img = ImageIO.read(new File("image" + i + ".JPG"));
//				images.add(img);
//			} catch (IOException e) {
//				System.out.println("Invalid File Name");
//			}
//		}

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
//		b = MouseInfo.getPointerInfo().getLocation();
		g.drawImage(picture, 0, 0, WIDTH, HEIGHT, this);
		// put your usless shit here
		if (afk > t) {
			Toolkit.getDefaultToolkit().sync();
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.drawImage(images.get(current % images.size()), (int) x, (int) y, scale, scale, this);
			checkCollision();
			x += dx;
			y += dy;
		}
//		if (lx != b.getX() || ly != b.getY()) {
//			afk = 0;
//		} else {
//			afk++;
//		}
//		lx = b.getX();
//		ly = b.getY();
	}
	
	private void checkCollision() {
		if (x < 0 || x + scale > WIDTH) {
			dx *= -1;
			current++;
		}
		if (y < 0 || y + scale > HEIGHT) {
			dy *= -1;
			current++;
		}
	}
}
