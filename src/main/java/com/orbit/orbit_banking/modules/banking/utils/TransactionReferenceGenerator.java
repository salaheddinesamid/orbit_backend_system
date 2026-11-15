package com.orbit.orbit_banking.modules.banking.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class TransactionReferenceGenerator {

    public String generateTransactionReference() {
        String date = LocalDate
                .now().format(DateTimeFormatter.BASIC_ISO_DATE);

        String random =
                UUID.randomUUID()
                        .toString()
                        .substring(0,9)
                        .toUpperCase();

        return String.format("TRX-%s-%s",date, random);
    }
}
