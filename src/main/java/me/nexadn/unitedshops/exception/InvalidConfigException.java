package me.nexadn.unitedshops.exception;

public class InvalidConfigException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5306010410217446343L;

    public InvalidConfigException(String configKey) {
	super("Invalid configuration near " + configKey);
    }

}
