
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board2 extends JPanel implements ActionListener {
private final int WIDTH = 1920;
private final int HEIGHT = 1080;
private final int DELAY = 0;
private Timer timer;
public Point b = MouseInfo.getPointerInfo().getLocation();
private double lx = b.getX(), ly = b.getY();
private BufferedImage map, img;
private ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
private int current = 0;
private double x = 700;
private double y = 500;
private double afk = 0;
private double t = 300;
private double dx = .5, dy = .5;
private int scale = 500;
private final int numberOfImages = 26;

public Board2() {
initBoard();
}

private void initBoard() {
addKeyListener(new TAdapter());
setBackground(Color.black);
setFocusable(true);
setPreferredSize(new Dimension(WIDTH, HEIGHT));
loadImages();
initGame();
}

private void loadImages() {
	try {
		map = ImageIO.read(new File("map.JPG"));
	} catch (IOException e1) {
		System.out.println("Invalid File Name");
	}
	for(int i = 1; i <= numberOfImages;i++) {
	try {
		img = ImageIO.read(new File("image" + i + ".JPG"));
		images.add(img);
	} catch (IOException e) {
		System.out.println("Invalid File Name");
	}
	}
	
}
private void initGame() {
timer = new Timer(DELAY, this);
timer.start();
}

@Override
public void paintComponent(Graphics g) {
super.paintComponent(g);
doDrawing(g);
}

private void doDrawing(Graphics g) {
b = MouseInfo.getPointerInfo().getLocation();
g.drawImage(map, 0, 0, WIDTH, HEIGHT, this);
//put your usless shit here
if (afk > t) {
Toolkit.getDefaultToolkit().sync();
g.fillRect(0, 0, WIDTH, HEIGHT);
g.drawImage(images.get(current % images.size()), (int) x, (int) y, scale, scale, this);
checkCollision();
x += dx;
y += dy;
}
if(lx != b.getX() || ly != b.getY()) {
afk = 0;
}
else {
afk++;
}
lx = b.getX();
ly = b.getY();
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

@Override
public void actionPerformed(ActionEvent e) {
repaint();
}

private class TAdapter extends KeyAdapter {
@Override
public void keyPressed(KeyEvent e) {
int key = e.getKeyCode();
if (key == KeyEvent.VK_ESCAPE) {
System.exit(0);
}
}
}
}
