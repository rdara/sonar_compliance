package org.jenkinsci.plugins.sonarcompliance.common;

/**
 * @author Ramesh Dara
 *
 */

public class PropertyException extends Exception {
    private static final long serialVersionUID = -7217415254208180176L;

    public static enum TYPE {
        FORMAT,
        DUPLICATE,
        UNSUPPORTED_VALUE,
        MISSING
    };

    TYPE mExceptionType;
    String mIncorrectvalue;

    public PropertyException(String incorrectValue, TYPE type) {
        setExceptionType(incorrectValue, type);
    }

    public void setExceptionType(String incorrectValue, TYPE type) {
        mExceptionType = type;
        mIncorrectvalue = incorrectValue;
    }

    @Override
    public String toString() {
        String msg = "";
        switch (mExceptionType) {
        case FORMAT:
            msg = "The given argument, " + mIncorrectvalue + ", must be like \\'key::value\\' pair.";
            break;
        case UNSUPPORTED_VALUE:
            msg = "The given argument, " + mIncorrectvalue + " value is not supported.";
            break;
        case MISSING:
            msg = mIncorrectvalue + " is required. must be like \\'key::value\\' pair";
            break;
        default:
            msg = "Exception occurred while processing the arguments";
        }
        return msg;
    }
}
