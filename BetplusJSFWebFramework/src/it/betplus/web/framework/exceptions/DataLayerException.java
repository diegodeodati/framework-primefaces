package it.betplus.web.framework.exceptions;

public class DataLayerException extends Exception {

	private static final long serialVersionUID = 3616937170934140071L;

	public DataLayerException() {
		
	}
	
	public DataLayerException(String message)
	{
		super(message);
	}

	public DataLayerException(Throwable cause)
	{
		super(cause);
	}

	public DataLayerException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
