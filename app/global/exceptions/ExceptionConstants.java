package global.exceptions;

public enum ExceptionConstants {
    DEFAULT(100), AUTHENTICATION(101), VALIDATION(102), AUTHORIZATION(103), CONFIGURATION(104);

    private int statusCode;

    ExceptionConstants(int statusCode) {
        this.statusCode = statusCode;
    }

    public int status() {
        return statusCode;
    }
}
