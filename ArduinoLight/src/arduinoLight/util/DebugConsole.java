package arduinoLight.util;

/**
 * Encapsulates the printing to the console for debugging-purposes.
 */
public class DebugConsole
{
	private static int classLength = 15;
	private static int methodLength = 15;
	
	public static boolean isEnabled()
	{
		return true;
	}
	
	public static void print(String containingClass, String method, String message)
	{
		if (!isEnabled())
			return;
		containingClass = padClass(containingClass);
		method = padMethod(method);
		System.out.println("+++ " + containingClass + ":" + method + ":" + message);
	}
	
	
	public static void printh(String containingClass, String method, String message)
	{
		StringBuilder newMessage = new StringBuilder();
		newMessage.append("-------------- ");
		message = Util.getRightPaddedString(message + " ", '-', 50);
		newMessage.append(message);
		print(containingClass, method, newMessage.toString());
	}
	
	
	private static String padClass(String containingClass)
	{
		if (containingClass.length() > classLength)
			classLength = containingClass.length();
		
		return Util.getRightPaddedString(containingClass, ' ', classLength);
	}
	
	private static String padMethod(String method)
	{
		if (method.length() > methodLength)
			methodLength = method.length();
		
		return Util.getRightPaddedString(method, ' ', methodLength);
	}
}
