package arduinoLight;

import arduinoLight.channelprovider.debugprovider.Debugprovider;
import arduinoLight.gui.AmbientlightPanel;
import arduinoLight.gui.CustomColorPanel;
import arduinoLight.gui.Gui;
import arduinoLight.gui.ScreenSelectionPanel;
import arduinoLight.gui.SerialConnectionPanel;

public class ArduinoLight
{
	public static void main(String[] args)
	{
		Gui.initLookAndFeel();
		Debugprovider provider = new Debugprovider();
		SerialConnectionPanel connectionPanel = new SerialConnectionPanel();
		ScreenSelectionPanel selectionPanel = new ScreenSelectionPanel();
		AmbientlightPanel ambiPanel = new AmbientlightPanel(selectionPanel);
		CustomColorPanel colorPanel = new CustomColorPanel(provider);
		new Gui(connectionPanel, ambiPanel, colorPanel);
	}
}
