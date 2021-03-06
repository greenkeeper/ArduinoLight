package arduinoLight.channel;

import arduinoLight.util.Color;


/**
 * This is one of the core parts of this application.
 * A Channel holds a Color and has a Name.
 * It is possible to listen to changes to these properties.
 * Currently, a Channel also has an ID which makes the channel unique. <br>
 * Every Implementation should be thread-safe.
 */
public interface Channel
{
	public int getId();
	
	public Color getColor();
	public void setColor(Color color);
	
	public String getName();
	public void setName(String name);
	
	public void addColorListener(ColorListener listener);
	public void removeColorListener(ColorListener listener);
	
	public void addNameListener(NameListener listener);
	public void removeNameListener(NameListener listener);
}
