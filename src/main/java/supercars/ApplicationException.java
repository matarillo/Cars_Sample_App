package supercars;

public class ApplicationException extends Exception {
    private final Throwable cause;

    public ApplicationException() {
        super();
        cause = null;
    }

    public ApplicationException(String message) {
        super(message);
        cause = null;
    }

    public ApplicationException(String message, Throwable cause) {
        super(message);
        this.cause = cause;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public void printStackTrace(java.io.PrintStream ps)
    {
        super.printStackTrace(ps);
        if (cause != null) {
            ps.println("Caused by:");
            cause.printStackTrace(ps);
        }
    }

    @Override
    public void printStackTrace(java.io.PrintWriter pw)
    {
        super.printStackTrace(pw);
        if (cause != null) {
            pw.println("Caused by:");
            cause.printStackTrace(pw);
        }
    }
}
