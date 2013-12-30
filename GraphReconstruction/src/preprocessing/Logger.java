package preprocessing;

public class Logger {
	private Level logLevel;

	public enum Level  {
		NONE, // no logging output
		DEBUG // output everything labelled as "debug"
	}

	public Logger(Level logLevel) {
		this.logLevel = logLevel;
	}

	public void debug(String message) {
		log(Level.DEBUG, message);
	}

	public void log(Level msgLevel, String message) {
		if (msgLevel.compareTo(logLevel) <= 0)
			System.out.println(message);
	}
}
