package arduinoLight.util;

public class Channel implements IChannel
{
	private RGBColor _color;
	private String _name;
	
	public Channel(String name)
	{
		this(name, new Color());
	}
	
	public Channel(String name, Color color)
	{
		_name = name;
		_color = color;
	}
	
	@Override
	public RGBColor getRGBColor() {
		return _color;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		final int prime = 37;
		int result = 1;
		result = prime * result + ((_color == null) ? 0 : _color.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Channel)) {
			return false;
		}
		Channel other = (Channel) obj;
		if (_color == null) {
			if (other._color != null) {
				return false;
			}
		} else if (!_color.equals(other._color)) {
			return false;
		}
		if (_name == null) {
			if (other._name != null) {
				return false;
			}
		} else if (!_name.equals(other._name)) {
			return false;
		}
		return true;
	}
}