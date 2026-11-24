package au.com.businessapi.identifiers;

import java.util.Optional;

// 8-digit TFNs (organisations only) have their own weights. A 9-digit TFN with a leading
// zero passes on either the 9-digit check or the 8-digit check of the rest.
public final class Tfn {
    private static final int[] WEIGHTS_9 = {1, 4, 3, 7, 5, 8, 6, 9, 10};
    private static final int[] WEIGHTS_8 = {1, 4, 3, 7, 5, 8, 6, 10};

    private Tfn() {}

    public static boolean isValid(String value) {
        return isValid(value, false);
    }

    public static boolean isValid(String value, boolean individual) {
        return normalise(value, individual).isPresent();
    }

    public static Optional<String> normalise(String value) {
        return normalise(value, false);
    }

    public static Optional<String> normalise(String value, boolean individual) {
        String tfn = individual ? Digits.of(value, 9) : Digits.of(value, 8, 9);
        if (tfn == null) return Optional.empty();
        String core = Digits.stripLeadingZeros(tfn);
        if (tfn.length() == 9 && core.length() >= 8 && Digits.sum(tfn, WEIGHTS_9, 0) % 11 == 0) return Optional.of(tfn);
        int[] weights = core.length() == 9 ? WEIGHTS_9 : core.length() == 8 ? WEIGHTS_8 : null;
        return weights != null && Digits.sum(core, weights, 0) % 11 == 0 ? Optional.of(tfn) : Optional.empty();
    }
}
