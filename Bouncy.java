
import java.awt.EventQueue;

import javax.swing.JFrame;

public class Bouncy extends JFrame {
	public Bouncy() {
		initUI();
	}

	private void initUI() {
		add(new Board2());
		setResizable(false);
		pack();
		setTitle("Bouncy");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			JFrame ex = new Bouncy();
			ex.setVisible(true);
		});
	}
}

