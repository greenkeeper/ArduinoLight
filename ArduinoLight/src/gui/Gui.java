/**
 * The Guided User Interface for the ArduinoLight
 * @author Tom Hohendorf
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.SpinnerListModel;
import javax.swing.JComboBox;

public class Gui{

	JFrame _frame = new JFrame("Arduino Light");
	
	JTabbedPane _menuTabs = new JTabbedPane(JTabbedPane.TOP);
	
	AmbientlightPanel _ambientlightPanel = new AmbientlightPanel();
	
	JPanel _soundToLightPanel = new JPanel();
	
	JPanel _mainPanel = new JPanel();
	JLabel _connectionSpeedLabel = new JLabel("Connection Speed: 900000");
	JLabel _channelLabel = new JLabel("Channels: ");
	JSpinner _channelSpinner = new JSpinner();
	JLabel _lblNewLabel = new JLabel("New label");
	JComboBox _comboBox = new JComboBox();
	JButton _connectButton = new JButton("Connect");
		
	public Gui(){
		initComponents();
	}
	
	public static void main(String[] args){
		initLookAndFeel();
		new Gui();
	}
		
	/**
	 * Initialize the Look and Feel
	 * First try is "Nimbus"
	 * Second try is the OS Standard Look and Feel
	 */
	private static void initLookAndFeel(){
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			try{
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e1){
				System.out.println("Could not set a valid Look and Feel!");
			}
		}
	}

	private void initComponents() {
			
		_menuTabs.addTab("AmbiLight", _ambientlightPanel);
		_menuTabs.addTab("SoundToLight", _soundToLightPanel);
		
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.LINE_AXIS));
		_mainPanel.setBorder(new TitledBorder(null, "Connection Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));				
		_mainPanel.add(_connectionSpeedLabel);
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_channelLabel);
		_mainPanel.add(_channelSpinner);
			_channelSpinner.setModel(new SpinnerListModel(new String[] {"1", "2","3", "4"}));
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_lblNewLabel);
		_mainPanel.add(_comboBox);
		_mainPanel.add(Box.createHorizontalGlue());
		_mainPanel.add(_connectButton);
		
		_frame.add(_menuTabs, BorderLayout.CENTER);
		_frame.add(_mainPanel, BorderLayout.SOUTH);
		_frame.setMinimumSize(new Dimension(600, 450));
		_frame.setSize(new Dimension(800, 550));
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		_frame.setVisible(true);
		_frame.pack();
	}
}

