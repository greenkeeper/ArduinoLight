/**
 * This Panel holds the Controls necessary to activate a Module and to switch between Channels.
 * Every Module needs this Panel, thus it is automatically added by the ModulePanel Wrapper Class.
 */

package arduinoLight.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import arduinoLight.channel.Channel;
import arduinoLight.channelholder.ModifiableChannelholder;
import arduinoLight.model.Model;

@SuppressWarnings("serial")
public class ChannelPanel extends JPanel{

	ModifiableChannelholder _provider;
	
	private DefaultComboBoxModel<ComboBoxChannelItem> _channelBoxModel = new DefaultComboBoxModel<ComboBoxChannelItem>();
	private JComboBox<ComboBoxChannelItem> _channelBox = new JComboBox<ComboBoxChannelItem>(_channelBoxModel);
	private JLabel _channelLabel = new JLabel("Channel: ");
	private JButton _removeButton = new JButton("Remove");
	private JButton _addButton = new JButton("Add");
	
	public ChannelPanel(ModifiableChannelholder provider){
		_provider = provider;
		initComponents();
	}
	
	private void initComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(new TitledBorder(null, "Status", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));

		_provider.addChannel(Model.getInstance().getChannelFactory().newChannel());
		updateComboBoxModel();
		
		//_channelBox.setEditable(true);
		
		_removeButton.setEnabled(false);
		_addButton.addActionListener(new AddButtonHandler());
		_removeButton.addActionListener(new RemoveButtonHandler());
		
		this.add(_channelLabel);
		this.add(_channelBox);
		this.add(_addButton);
		this.add(_removeButton);
	}
	
	public void addComboBoxListener(ItemListener listener){
		_channelBox.addItemListener(listener);
	}
	
	private void updateComboBoxModel(){
		_channelBoxModel.removeAllElements();
		Set<Channel> newChannellist = _provider.getChannels();
		for(Channel channel : newChannellist){
			ComboBoxChannelItem channelItem = new ComboBoxChannelItem(channel);
			_channelBoxModel.addElement(channelItem);
		}
	}
	
	public class ComboBoxChannelItem{
		
		Channel _channel;
		
		public ComboBoxChannelItem(Channel channel){
			_channel = channel;
		}
		
		@Override
		public String toString() {
			return "Channel: " + Integer.toString(_channel.getId());
		}
		
		public Channel getChannel(){
			return _channel;
		}
	}
	
	class AddButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_provider.addChannel(Model.getInstance().getChannelFactory().newChannel());
			updateComboBoxModel();
			_channelBox.setSelectedIndex(_channelBoxModel.getSize() - 1);
			_removeButton.setEnabled(true);
		}
	}
	
	class RemoveButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			_provider.removeChannel(getSelectedChannel());
			updateComboBoxModel();
			if(_channelBox.getItemCount() < 2){
				_removeButton.setEnabled(false);
			}
		}
	}
	
	public Channel getSelectedChannel() {
		ComboBoxChannelItem channelItem = (ComboBoxChannelItem)_channelBox.getSelectedItem(); 
		return channelItem.getChannel();
		
	}
}
