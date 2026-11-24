package au.com.businessapi.identifiers;

import java.util.Optional;

public final class Arbn {
    private Arbn() {}

    public static boolean isValid(String value) {
        return Acn.isValid(value);
    }

    public static Optional<String> normalise(String value) {
        return Acn.normalise(value);
    }

    public static Optional<String> format(String value) {
        return Acn.format(value);
    }
}
