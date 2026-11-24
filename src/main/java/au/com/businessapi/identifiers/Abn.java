package au.com.businessapi.identifiers;

import java.util.Optional;

public final class Abn {
    private static final int[] WEIGHTS = {10, 1, 3, 5, 7, 9, 11, 13, 15, 17, 19};

    private Abn() {}

    public static boolean isValid(String value) {
        return normalise(value).isPresent();
    }

    public static Optional<String> normalise(String value) {
        String abn = Digits.of(value, 11);
        if (abn == null) return Optional.empty();
        // Subtract 1 from the first digit, then the weighted sum must be divisible by 89.
        int sum = (abn.charAt(0) - '0' - 1) * WEIGHTS[0] + Digits.sum(abn.substring(1), WEIGHTS, 1);
        return sum % 89 == 0 ? Optional.of(abn) : Optional.empty();
    }

    public static Optional<String> format(String value) {
        return normalise(value).map(a -> a.substring(0, 2) + " " + a.substring(2, 5) + " " + a.substring(5, 8) + " " + a.substring(8));
    }
}
