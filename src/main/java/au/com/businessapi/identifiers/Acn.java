package au.com.businessapi.identifiers;

import java.util.Optional;

public final class Acn {
    private static final int[] WEIGHTS = {8, 7, 6, 5, 4, 3, 2, 1};

    private Acn() {}

    public static boolean isValid(String value) {
        return normalise(value).isPresent();
    }

    public static Optional<String> normalise(String value) {
        String acn = Digits.of(value, 9);
        if (acn == null) return Optional.empty();
        int check = (10 - Digits.sum(acn, WEIGHTS, 0) % 10) % 10;
        return acn.charAt(8) - '0' == check ? Optional.of(acn) : Optional.empty();
    }

    public static Optional<String> format(String value) {
        return normalise(value).map(a -> a.substring(0, 3) + " " + a.substring(3, 6) + " " + a.substring(6));
    }
}
