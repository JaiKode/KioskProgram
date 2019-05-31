package simp;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class test {
	public static void main(String[] args) throws IOException, InterruptedException, AWTException {
		Runtime.getRuntime().exec(new String[]{"cmd", "/c","start chrome http://bhs.cloud"});
		Thread.sleep(2000);
		Robot bot = new Robot();
		bot.keyPress(KeyEvent.VK_F11);
	}
}