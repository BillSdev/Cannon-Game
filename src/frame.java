import javax.swing.JFrame;


public class frame {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		frame.setContentPane(new panel());
		frame.pack();
		frame.setTitle("test");
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
}
