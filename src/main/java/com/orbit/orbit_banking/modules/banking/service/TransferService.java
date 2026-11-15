package com.orbit.orbit_banking.modules.banking.service;

import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;

public interface TransferService {


    Transaction transfer(TransactionType type, TransferDto transferDto);
}
