import java.util.Arrays;
import java.util.Objects;

public class Utils {
    public static final String ROOT = "./";

    public static void checkEquals(Object expected, Object actual, String message) {
        if (!Objects.equals(expected, actual))
            throw new AssertionError(String.format("%s: expected %s, returned %s", message, expected, actual));
    }

    public static void checkAlmostEquals(Double expected, Double actual, String message) {
        if (Math.abs(actual - expected) > 1e-3)
            throw new AssertionError(String.format("%s: expected %s, returned %s", message, expected, actual));
            //throw new AssertionError(String.format("    ", message));
    }

    public static void checkArrayEquals(double[] expected, double[] actual, String message) {
        if (expected.length == actual.length) {
            for (int i = 0; i < expected.length; i++) {
                if (Math.abs(actual[i] - expected[i]) > 1e-3) {
                    throw new AssertionError(String.format("%s!\n\t - Expected: %s\n\t - Returned: %s",
                            message, Arrays.toString(expected), Arrays.toString(actual)));
                }
            }
        } else {
            throw new AssertionError("Array length mismatch: expected length " + expected.length + ", returned length " + actual.length);
        }
    }

    public static void checkArrayEquals(int[] expected, int[] actual, String message) {
        if (!Arrays.equals(expected, actual)) {
            throw new AssertionError(String.format("%s!\n\t - Expected: %s\n\t - Returned: %s",
                    message, Arrays.toString(expected), Arrays.toString(actual)));
        }
    }

    public static void checkColorWalkEquals(String[] tokens, ColorWalk.WalkPair[] colorWalk, String message) {
        if (colorWalk == null)
            throw new AssertionError(message + ": colorWalk is null!");

        // Get a string representation of the color walk to compare to the expected
        StringBuilder builder = new StringBuilder();
        for (ColorWalk.WalkPair wp : colorWalk) {
            if (wp == null)
                builder.append("null ");
            else
                builder.append(wp.startColor).append(" ").append(wp.walkWeight).append(" ");
        }
        String colorWalkString = builder.toString();

        // Check length
        int expectedLength = (tokens.length - 2) / 2;
        if (expectedLength != colorWalk.length)
            throw new AssertionError("Array length mismatch: expected length " + expectedLength + ", returned length " + colorWalk.length);

        // Check each walk pair
        for (int v = 0; v < colorWalk.length; v++) {
            char startColor = tokens[v * 2 + 2].charAt(0);
            int walkWeight = Integer.parseInt(tokens[v * 2 + 3]);
            if (colorWalk[v] == null || startColor != colorWalk[v].startColor || walkWeight != colorWalk[v].walkWeight)
                throw new AssertionError(String.format("%s!\n\t - Expected: %s\n\t - Returned: %s",
                        message, String.join(" ", Arrays.asList(tokens).subList(2,tokens.length)), colorWalkString));
        }
    }
}
