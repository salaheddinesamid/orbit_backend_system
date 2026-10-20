package com.orbit.orbit_banking.modules.accounts.utils;

import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import org.springframework.stereotype.Component;

import java.util.random.RandomGenerator;


@Component
public class AccountNumberGenerator {

    private static final String PREFIX = "002516";
    private static final int RANDOM_DIGITS_LENGTH = 10;

    private final BankAccountRepository bankAccountRepository;
    private final RandomGenerator randomGenerator =
            RandomGenerator.getDefault();

    public AccountNumberGenerator(
            BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public String generateAccountNumber() {
        String generatedAccountNumber;

        do {
            StringBuilder randomDigits = new StringBuilder();

            for (int i = 0; i < RANDOM_DIGITS_LENGTH; i++) {
                int digit = randomGenerator.nextInt(10);
                randomDigits.append(digit);
            }

            generatedAccountNumber =
                    PREFIX + randomDigits;

        } while (bankAccountRepository
                .existsByAccountNumber(generatedAccountNumber));

        return generatedAccountNumber;
    }
}

