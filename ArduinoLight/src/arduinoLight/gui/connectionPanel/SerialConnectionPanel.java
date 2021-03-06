package arduinoLight.gui.connectionPanel;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import arduinoLight.arduino.PortMap;
import arduinoLight.arduino.SerialConnection;
import arduinoLight.arduino.amblone.AmbloneTransmission;
import arduinoLight.util.DebugConsole;

@SuppressWarnings("serial")
public class SerialConnectionPanel extends JPanel
{
	private SerialConnection _connection;
	private AmbloneTransmission _amblone;
	private PortMap _map;
	
	private AmbloneChannelPanel _amblonePanel;
	private JComboBox<PortItem> _portComboBox;
	private JSpinner _frequencySpinner;
	private JToggleButton _connectButton;
	private JLabel _portLabel = new JLabel("COM-Port: ");
	private JLabel _frequencyLabel = new JLabel("Frequency: ");
	
	
	public SerialConnectionPanel(SerialConnection connection, AmbloneTransmission ambloneTransmission, PortMap map)
	{
		_connection = connection;
		_amblone = ambloneTransmission;
		_map = map;
		
		initComponents();
	}
	
	private void initComponents()
	{
		//amblonePanel
		_amblonePanel = new AmbloneChannelPanel(_map, AmbloneTransmission.SUPPORTED_CHANNELS);
				
		//frequencySpinner
		_frequencySpinner = new JSpinner();
		_frequencySpinner.setModel(new SpinnerNumberModel(100, 1, AmbloneTransmission.MAX_REFRESHRATE, 1));
		
		//portComboBox
		ComboBoxModel<PortItem> cbModel = new DefaultComboBoxModel<PortItem>();
		_portComboBox = new JComboBox<PortItem>(cbModel);
		_portComboBox.addPopupMenuListener(new ComboBoxMenuListener());
		_portComboBox.setPreferredSize(new Dimension(100, 0));
		
		//connectButton
		_connectButton = new JToggleButton("Connect");
		_connectButton.addActionListener(new ConnectButtonHandler());
		
		//Layout
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.weighty = 0.5;
		gbc.gridx = 0;
		gbc.gridy = 0;
		this.setBorder(BorderFactory.createTitledBorder("Connection Settings"));
		this.setLayout(new GridBagLayout());

		this.add(_amblonePanel);
		JPanel lowerLine = new JPanel();
			lowerLine.setLayout(new BoxLayout(lowerLine, BoxLayout.X_AXIS));
			lowerLine.add(_frequencyLabel);
			lowerLine.add(_frequencySpinner);
			lowerLine.add(Box.createHorizontalStrut(20));
			lowerLine.add(_portLabel);
			lowerLine.add(_portComboBox);
		gbc.gridy = 1;
		this.add(lowerLine, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.weightx = 1.0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(Box.createGlue(), gbc);
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 2;
		this.add(_connectButton, gbc);
		
		//preload CommPortIdentifiers
		refreshComboBox();
	}	

	private void refreshComboBox()
	{
		DefaultComboBoxModel<PortItem> model = (DefaultComboBoxModel<PortItem>) _portComboBox.getModel();
		model.removeAllElements();
		Enumeration<CommPortIdentifier> ports = SerialConnection.getAvailablePorts();
		
		while(ports.hasMoreElements()){
			model.addElement(new PortItem(ports.nextElement()));
		}
		
		if(model.getSize() == 0){
			_connectButton.setEnabled(false);
		} else {
			_connectButton.setEnabled(true);
		}
		DebugConsole.print("SerialConnectionPanel", "refreshComboBox", "comboBox refreshed!");
	}
	
	class ConnectButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(_connectButton.isSelected())
			{
				PortItem selectedItem = (PortItem) _portComboBox.getModel().getSelectedItem();
				try
				{
					_connection.open(selectedItem.getPort(), 256000);
				} catch (PortInUseException | IllegalStateException | IllegalArgumentException e1) {
					_connectButton.setSelected(false);
					JOptionPane.showMessageDialog(null,
							"Could not establish a Connection!\nIs the Connection already in use?",
							"Connection failed!",
							JOptionPane.ERROR_MESSAGE);
				}
				if (_connection.isOpen())
				{
					int frequency = ((Integer) _frequencySpinner.getValue());
					_amblone.start(_connection, frequency);
					_connectButton.setText("Disconnect");
				}
			}
			else
			{
				_connection.close();
			}
		}
	}
	
	/**
	 * This listener refreshes the ComboBox if the combobox's popupmenu is opened.
	 */
	private class ComboBoxMenuListener implements PopupMenuListener
	{
		@Override
		public void popupMenuCanceled(PopupMenuEvent e) { /** Do nothing */ }

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) { /** Do nothing */ }

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent e)
		{
			refreshComboBox();
		}
	}
	
	/**
	 * Can be used inside a ComboBox. Holds a reference to a CommPortIdentifier.
	 * Provides a proper toString method.
	 */
	private class PortItem
	{
		CommPortIdentifier _port;
		
		public PortItem(CommPortIdentifier port)
		{
			_port = port;
		}
		
		public CommPortIdentifier getPort()
		{
			return _port;
		}
		
		@Override
		public String toString()
		{
			return _port.getName();
		}
	}
}

