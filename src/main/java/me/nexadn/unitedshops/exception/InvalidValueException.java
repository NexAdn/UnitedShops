package me.nexadn.unitedshops.exception;

public class InvalidValueException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -480914077354083867L;

    public InvalidValueException() {
    }

    public InvalidValueException(String arg0) {
        super("Invalid value: " + arg0);
    }

    public InvalidValueException(Throwable arg0) {
        super(arg0);
    }

    public InvalidValueException(String arg0, Throwable arg1) {
        super("Invalid value: " + arg0, arg1);
    }

    public InvalidValueException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super("Invalid value: " + arg0, arg1, arg2, arg3);
    }

}
