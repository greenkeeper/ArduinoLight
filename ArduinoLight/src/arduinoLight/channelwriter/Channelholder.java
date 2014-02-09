package arduinoLight.channelwriter;

import java.util.*;

import arduinoLight.channel.Channel;

public interface Channelholder
{
	/**
	 * This method returns an unmodifiableList of
	 * the channels that are used for writing. 
	 */
	public Set<Channel> getChannels();
}
