import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JTextField;


public class Monitor extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static long pressTime = 0;
	private static long releaseTime = 0;
	private static char pressedKey;
	Thread th = new Thread();
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	

	private  void initUI(Container pane){
		//Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        
		JTextField textInput = new JTextField();
		textInput.setSize(300, 200);
		KeyListener keyListener = new KeyListener(){

			@Override
			public void keyPressed(KeyEvent e) {
				Monitor.pressTime = System.currentTimeMillis();
				Monitor.pressedKey = e.getKeyChar();				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				Monitor.releaseTime = System.currentTimeMillis();
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
			        public void run() {
			        	Date date = new Date();
			            try {
			            	if(findTime(pressTime, releaseTime) > 0){
							writeToFile(dateFormat.format(date), findTime(pressTime, releaseTime), pressedKey);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
			    });
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub				
			}
			
		};
		textInput.addKeyListener(keyListener);
		
		gl.setAutoCreateContainerGaps(true);
        gl.setHorizontalGroup(gl.createSequentialGroup()
                .addComponent(textInput)
        );
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(textInput)
        );
        pack();
    }
	
	private static void writeToFile(String date, long time, char character) throws IOException{
		
		java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
		String data = date + " " + System.getProperty("user.name") + " at " + localMachine.getHostName() + " pressed /" + character + "/ for " + time + " mlsec";
		
		FileWriter fstream = new FileWriter("log.txt", true);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write(data);
		out.newLine();
		out.close();
	}

	private static long findTime(long start, long finish){
		long time = finish - start;
		return time;
	}
	
	

	public static void createAndShowGUI(){
		Monitor obj = new Monitor();
		JFrame frame = new JFrame("Monitor");
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.initUI(frame.getContentPane());
		frame.pack();
    	frame.setVisible(true);
	}

	public static void main(String[] args){
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}


	
}
