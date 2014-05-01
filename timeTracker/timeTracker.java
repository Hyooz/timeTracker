import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.util.*;

class timePunch{
	Calendar punchTime;
	String punchName;
	
	timePunch(Calendar punch, String name){
		punchTime = punch;
		punchName = name;
	}
	
	Calendar getPunchTime(){
		return punchTime;
	}
	
	String getPunchName(){
		return punchName;
	}
	
	void setPunchTime(){
		punchTime = Calendar.getInstance();
	}
}

public class timeTracker{

	JButton StartButton;
	JButton StopButton;
	private JTextField descriptor;
	private ArrayList<timePunch> punchList;
	private JFrame frame;
	
	public static void main(String[] args) {
		timeTracker track = new timeTracker();
		track.go();		
	}	
		
	public void go(){
		//build the GUI
		
		frame = new JFrame("Time Tracker");
		JPanel mainPanel = new JPanel();
		Font bigFont = new Font("sanserif", Font.BOLD, 24);
		
		descriptor = new JTextField(15);
		descriptor.setFont(bigFont);
		
		StartButton = new JButton("New Punch");
		StopButton = new JButton("End Punch");
		punchList = new ArrayList<timePunch>();
		
		JLabel textLabel = new JLabel("Punch Type");
		
		mainPanel.add(textLabel);
		mainPanel.add(StartButton);
		mainPanel.add(StopButton);
		mainPanel.add(descriptor);
		
		StartButton.addActionListener(new StartButtonListener());
		StopButton.addActionListener(new StopButtonListener());
		
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
		JMenuItem saveMenuItem = new JMenuItem("Save");
		newMenuItem.addActionListener(new NewMenuListener());
		saveMenuItem.addActionListener(new saveMenuListener());
		
		fileMenu.add(newMenuItem);
		fileMenu.add(saveMenuItem);
		menuBar.add(fileMenu);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
		frame.setSize(500,600);
		frame.setVisible(true);
		
	}
	

	class StartButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String test = StartButton.getText();
				if(test == "New Punch"){
					StartButton.setText("Timer Running...");
					StopButton.setText("End Punch");
					punchList.add(new timePunch(Calendar.getInstance(), descriptor.getText()));
				}
		}
	}
	
	class StopButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String test = StopButton.getText();
			if(test == "End Punch"){
				StartButton.setText("New Punch");
				StopButton.setText("Stopped");
				punchList.add(new timePunch(Calendar.getInstance(), "End Punch" + " " + descriptor.getText()));
			}
		}
	}
	
	private void saveFile(File file){
		try{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			
			for(timePunch punch:punchList){
				writer.write(punch.punchName + " " + punch.punchTime.getTime()+ "\n");
			}
			writer.close();
		}
		catch(IOException ex){
			System.out.println("Couldn't write punch");
			ex.printStackTrace();
		}
	}
	
	class NewMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			punchList.clear();
			descriptor.setText("");
			descriptor.requestFocus();
		}
	}
	
	class saveMenuListener implements ActionListener{
		public void actionPerformed(ActionEvent event){
			String test = StopButton.getText();
			if(test == "End Punch"){
				StartButton.setText("New Punch");
				StopButton.setText("Stopped");
				punchList.add(new timePunch(Calendar.getInstance(), "End Punch" + " " + descriptor.getText()));
			}
			
			JFileChooser fileSave = new JFileChooser();
			fileSave.showSaveDialog(frame);
			saveFile(fileSave.getSelectedFile());
		}
	}
	
}
