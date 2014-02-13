package arduinoLight.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import arduinoLight.channel.Channel;
import arduinoLight.channelprovider.generator.ambientlight.Ambientlight;
import arduinoLight.channelwriter.Channelholder;

public class Model
{
	public static Model _instance = null;
	private ChannelFactory _channelFactory = new ChannelFactory();
	private Ambientlight _ambientlight = new Ambientlight();
	private UnusedChannels _unusedChannels = new UnusedChannels();
	private List<Channelholder> _channelwriters = new ArrayList<>();
	
	private Model()
	{
		_channelwriters.add(_ambientlight);
	}
	
	public static Model getInstance()
	{
		if (_instance == null)
		{
			_instance = new Model();
		}
		return _instance;
	}
	
	public ChannelFactory getChannelFactory()
	{
		return _channelFactory;
	}
	
	public UnusedChannels getUnusedChannels()
	{
		return _unusedChannels;
	}
	
	public Ambientlight getAmbientlight()
	{
		return _ambientlight;
	}
	
	public List<Channelholder> getChannelholders()
	{
		List<Channelholder> channelHolders = new ArrayList<Channelholder>(_channelwriters);
		channelHolders.add(_channelFactory);
		channelHolders.add(_unusedChannels);
		return channelHolders;
	}
	
	/**
	 * Searches for a Channelholder that uses the specified channel.
	 * If the channel is not being written to currently, an instance of UnusedChannels is returned. 
	 */
	public Channelholder getChannelholder(Channel channel)
	{
		Set<Channel> writerChannels = null;
		//Search all the writers
		for (Channelholder cwriter : _channelwriters)
		{
			writerChannels = cwriter.getChannels();
			//Search Channels for each writer
			for (Channel ch : writerChannels)
			{
				if (ch.equals(channel))
				{
					return cwriter;
				}
			}
		}
		
		return _unusedChannels;
	}
	
	private class UnusedChannels implements Channelholder
	{
		@Override
		public Set<Channel> getChannels()
		{
			Set<Channel> unusedChannels = new HashSet<Channel>(getChannelFactory().getChannels());
			
			for (Channelholder channelwriter : _channelwriters)
			{
				unusedChannels.removeAll(channelwriter.getChannels());
			}
			
			return unusedChannels;
		}

		@Override
		public String getChannelsDescription()
		{
			return "Unused Channels";
		}
	}
}