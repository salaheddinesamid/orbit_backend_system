package com.orbit.orbit_banking.modules.accounts.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class BankAccountDetailsGenerator {

    private static final String COUNTRY_CODE = "MA";
    private static final String COUNTRY_CODE_NUMERIC = "2210";

    private static final int IBAN_MODULUS = 97;
    private static final int RIB_MODULUS = 97;

    private static final int BANK_CODE_LENGTH = 3;
    private static final int BRANCH_CODE_LENGTH = 3;
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private static final int RIB_KEY_LENGTH = 2;
    private static final int RIB_LENGTH = 24;
    private static final int IBAN_LENGTH = 28;

    /**
     * Generates a Moroccan RIB.
     *
     * RIB = Bank Code + Branch Code + Account Number + RIB Key
     */
    public String generateRib(
            String bankCode,
            String branchCode,
            String accountNumber
    ) {

        validateNumeric(bankCode, BANK_CODE_LENGTH, "Bank code");
        validateNumeric(branchCode, BRANCH_CODE_LENGTH, "Branch code");
        validateNumeric(accountNumber, ACCOUNT_NUMBER_LENGTH, "Account number");

        String bban = bankCode + branchCode + accountNumber;

        int remainder = new BigInteger(bban)
                .mod(BigInteger.valueOf(RIB_MODULUS))
                .intValue();

        int ribKey = (RIB_MODULUS - remainder) % RIB_MODULUS;

        return bban + String.format("%02d", ribKey);
    }

    /**
     * Generates a Moroccan IBAN from a 24-digit RIB.
     */
    public String generateIBAN(String rib) {

        validateNumeric(rib, RIB_LENGTH, "RIB");

        // Rearrange BBAN + country code + check digits
        String rearranged = rib + COUNTRY_CODE + "00";

        // Move first four characters to the end
        rearranged = rearranged.substring(4) + rearranged.substring(0, 4);

        // Convert letters to their numeric representation
        String numericValue = rearranged.replace(
                COUNTRY_CODE,
                COUNTRY_CODE_NUMERIC
        );

        BigInteger number = new BigInteger(numericValue);

        int remainder = number
                .mod(BigInteger.valueOf(IBAN_MODULUS))
                .intValue();

        int checkDigits = 98 - remainder;

        return COUNTRY_CODE
                + String.format("%02d", checkDigits)
                + rib;
    }

    /**
     * Generates both RIB and IBAN from account details.
     */
    public BankAccountDetails generateBankAccountDetails(
            String bankCode,
            String branchCode,
            String accountNumber
    ) {

        String rib = generateRib(bankCode, branchCode, accountNumber);
        String iban = generateIBAN(rib);

        return new BankAccountDetails(rib, iban);
    }

    /**
     * Validates a Moroccan RIB checksum.
     */
    public boolean validateRib(String rib) {

        if (!isValidNumeric(rib, RIB_LENGTH)) {
            return false;
        }

        String bban = rib.substring(0, RIB_LENGTH - RIB_KEY_LENGTH);
        String providedKey = rib.substring(RIB_LENGTH - RIB_KEY_LENGTH);

        int remainder = new BigInteger(bban)
                .mod(BigInteger.valueOf(RIB_MODULUS))
                .intValue();

        int expectedKey = (RIB_MODULUS - remainder) % RIB_MODULUS;

        return Integer.parseInt(providedKey) == expectedKey;
    }

    /**
     * Validates a Moroccan IBAN using MOD-97.
     */
    public boolean validateIBAN(String iban) {

        if (iban == null || iban.length() != IBAN_LENGTH) {
            return false;
        }

        iban = iban.replaceAll("\\s+", "").toUpperCase();

        if (!iban.matches("MA\\d{26}")) {
            return false;
        }

        String rearranged = iban.substring(4) + iban.substring(0, 4);

        String numericValue = rearranged.replace(
                COUNTRY_CODE,
                COUNTRY_CODE_NUMERIC
        );

        return new BigInteger(numericValue)
                .mod(BigInteger.valueOf(IBAN_MODULUS))
                .intValue() == 1;
    }

    /**
     * Validates numeric fields and their expected lengths.
     */
    private void validateNumeric(
            String value,
            int expectedLength,
            String fieldName
    ) {

        if (!isValidNumeric(value, expectedLength)) {
            throw new IllegalArgumentException(
                    fieldName + " must contain exactly "
                            + expectedLength + " digits"
            );
        }
    }

    private boolean isValidNumeric(String value, int expectedLength) {

        return value != null
                && value.matches("\\d{" + expectedLength + "}");
    }

    /**
     * Result containing generated RIB and IBAN.
     */
    public record BankAccountDetails(
            String rib,
            String iban
    ) {}
}
