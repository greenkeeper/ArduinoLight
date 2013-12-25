/**
 * The User Interface for the Custom Color Module.
 * It is a simple Color Picker for Configuration or Testing Purposes
 */

package arduinoLight.gui.customColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arduinoLight.channelprovider.ChannelcolorsListener;
import arduinoLight.channelprovider.generator.customColors.CustomColorsProvider;
import arduinoLight.gui.ActivatePanel;
import arduinoLight.gui.ColorSlider;
import arduinoLight.gui.ChannelPanel;
import arduinoLight.gui.TabPanel;
import arduinoLight.util.IChannel;

@SuppressWarnings("serial")
public class CustomColorPanel extends TabPanel implements ChannelcolorsListener{

	//Model Reference
	private CustomColorsProvider _colorProvider;
	
	//Controller Reference (Inner Class)
	private SliderPanelHandler _panelHandler = new SliderPanelHandler();
	
	private JPanel _mainPanel = new JPanel();
	private ChannelPanel _channelPanel;
	private ActivatePanel _activatePanel;
	private JPanel _statusPanel = new JPanel();
	private JPanel _sliderPanel = new JPanel();
	private JPanel _previewPanel = new JPanel();
	private JPanel _colorPanel = new JPanel();
	
	private ColorSlider _redSlider = new ColorSlider("R", 0, 255, 0);
	private ColorSlider _greenSlider = new ColorSlider("G", 0, 255, 0);
	private ColorSlider _blueSlider = new ColorSlider("B", 0, 255, 0);
	private ColorSlider _brightnessSlider = new ColorSlider("B", 0, 255, 255);
	
	public CustomColorPanel(CustomColorsProvider colorProvider, String title){
		_colorProvider = colorProvider;
		_title = title;
		 _channelPanel = new ChannelPanel(_colorProvider);
		 _activatePanel = new ActivatePanel(_colorProvider);
		initComponents();
	}

	private void initComponents() {
		
		_previewPanel.setLayout(new BorderLayout());
		_sliderPanel.setLayout(new BoxLayout(_sliderPanel, BoxLayout.LINE_AXIS));
		_mainPanel.setLayout(new BorderLayout());
		_statusPanel.setLayout(new BoxLayout(_statusPanel, BoxLayout.LINE_AXIS));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		_redSlider.addChangeListener(_panelHandler);
		_greenSlider.addChangeListener(_panelHandler);
		_blueSlider.addChangeListener(_panelHandler);
		_brightnessSlider.addChangeListener(_panelHandler);
		
		_sliderPanel.add(_redSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_greenSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_blueSlider);
		_sliderPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		_sliderPanel.add(_brightnessSlider);
		
		_previewPanel.add(_colorPanel, BorderLayout.CENTER);
		_colorPanel.setBackground(new Color(0, 0, 0));
		
		_previewPanel.setBorder(new TitledBorder(null, "Preview", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		_colorPanel.setBorder(new LineBorder(Color.black));
		_sliderPanel.setBorder(new TitledBorder(null, "RGB-Color", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.TOP));
		
		_mainPanel.add(_sliderPanel, BorderLayout.EAST);
		_mainPanel.add(_previewPanel, BorderLayout.CENTER);
		
		_statusPanel.add(_activatePanel);
		_statusPanel.add(_channelPanel);
		
		this.add(_mainPanel);
		this.add(_statusPanel);
		
	}

	/**
	 * Inner Class that handles the ChangeEvents thrown by the Sliders
	 */
	class SliderPanelHandler implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			arduinoLight.util.Color color = new arduinoLight.util.Color(_brightnessSlider.getValue(), _redSlider.getValue(), _greenSlider.getValue(), _blueSlider.getValue());
				_colorProvider.setChannelcolor(_channelPanel.getSelectedChannel(), color);

		}
	}

	@Override
	public void channelcolorsUpdated(Object source,
			List<IChannel> refreshedChannellist) {
		arduinoLight.util.Color newColor = _channelPanel.getSelectedChannel().getColor();
		_redSlider.setValueLabel(newColor.getR());
		_greenSlider.setValueLabel(newColor.getG());
		_blueSlider.setValueLabel(newColor.getB());
		_brightnessSlider.setValueLabel(newColor.getA());
		Color color = new Color(newColor.getR(), newColor.getB(), newColor.getB(), newColor.getA());
		_previewPanel.setBackground(color);
	}
}