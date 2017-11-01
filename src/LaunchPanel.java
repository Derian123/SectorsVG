import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("serial")
public class LaunchPanel extends JPanel {
	public BufferedImage backDrop;
	public JFrame frame;
	public final int WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public final int HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static void main(String[] args){

		new LaunchPanel();
	}
	public LaunchPanel(){
		makePanel();
		putActions();
		repaint();
	}
	public void putActions(){
		
		URL imageUrl = getClass().getResource("/imgs/launch.jpg");
		try {
			backDrop = ImageIO.read(imageUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}


		JButton start = new JButton("Start Game");
		start.setBounds((int)(WIDTH*.7), (int)(HEIGHT*.6), 200, 100);
		start.addActionListener(e->{
			frame.dispose();
			new Game();
		});
		add(start);

		JButton quit = new JButton("Quit");
		quit.setBounds((int)(WIDTH*.7), (int)(HEIGHT*.73), 200, 100);
		quit.addActionListener(e->{
			frame.dispose();
		});
		add(quit);

		
		
	}

//    public void keyPressed(KeyEvent e){
//	    int key = e.getKeyCode();
//
//        if(key == KeyEvent.VK_ESCAPE){
//	        System.exit(1);
//        }
//    }
	public void makePanel(){
		frame = new JFrame("Sectors!");
		frame.add(this);
		this.setLayout(null);
		frame.setPreferredSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize()));
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);

	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		drawBackGround(g);
	}
	public void drawBackGround(Graphics g){
		g.drawImage(backDrop, 0, 0,WIDTH,HEIGHT, null);
	}
}
