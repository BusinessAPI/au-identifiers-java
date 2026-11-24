# au-identifiers

Validate and format Australian identifiers in Java: ABN, ACN, ARBN, TFN, Director ID and VIN.

```xml
<dependency>
  <groupId>au.com.businessapi</groupId>
  <artifactId>au-identifiers</artifactId>
  <version>0.1.0</version>
</dependency>
```

```java
import au.com.businessapi.identifiers.*;

Abn.isValid("51 824 753 556");              // true
Abn.format("51824753556");                  // Optional["51 824 753 556"]
Acn.format("004085616");                    // Optional["004 085 616"]
Tfn.isValid("15195606");                    // true (8-digit, organisation)
Tfn.isValid("15195606", true);              // false
DirectorId.isValid("036 123 456 789 012");  // true
Vin.normalise(" 1hgcm82633a004352 ");       // Optional["1HGCM82633A004352"]
```

Each class has `isValid`, `normalise` and, for ABN, ACN and ARBN, `format`. `normalise` and `format` return an empty `Optional` for invalid input. `Arbn` wraps `Acn`. Java 8+.

## Rules

| | Format | Check |
|---|---|---|
| ABN | 11 digits | ATO modulus 89 |
| ACN, ARBN | 9 digits | ASIC check digit |
| TFN | 8 or 9 digits | ATO modulus 11 |
| Director ID | 15 digits, starts with 036 | Format only |
| VIN | 17 characters, no I, O or Q | Format only (ISO 3779) |

Spaces and hyphens in numbers are ignored. VINs are trimmed and upper-cased. Anything else makes the value invalid.

8-digit TFNs (organisations only) use their own weights, and a 9-digit TFN with a leading zero is valid if either the 9-digit check or the 8-digit check on the rest passes. Set the individual option for a person's TFN, which is always 9 digits.

The Director ID check digit method is only given to ATO-registered software providers, so it's a format check here.

## Valid isn't registered

These checks run offline and only tell you a number is well formed. To check an ABN or ACN is registered and active, or to register companies, ABNs and business names, see [Business API](https://businessapi.com.au).

Also available for [Python](https://github.com/BusinessAPI/au-identifiers-python), [JavaScript / TypeScript](https://github.com/BusinessAPI/au-identifiers-js), [PHP](https://github.com/BusinessAPI/au-identifiers-php), [.NET](https://github.com/BusinessAPI/au-identifiers-dotnet).

## Licence

MIT
