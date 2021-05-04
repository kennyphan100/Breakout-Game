import javax.swing.*;

public class Main {

	public static void main(String[] args) {

		JFrame frame = new JFrame();
		Game game = new Game();
		frame.setTitle("Breakout");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);

		
	}

}
