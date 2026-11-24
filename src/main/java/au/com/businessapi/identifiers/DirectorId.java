package au.com.businessapi.identifiers;

import java.util.Optional;

// Format only. The check digit method is only given to ATO-registered software providers.
public final class DirectorId {
    private DirectorId() {}

    public static boolean isValid(String value) {
        return normalise(value).isPresent();
    }

    public static Optional<String> normalise(String value) {
        String id = Digits.of(value, 15);
        return id != null && id.startsWith("036") ? Optional.of(id) : Optional.empty();
    }
}
