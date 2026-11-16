package com.orbit.orbit_banking.modules.banking.service.implementation;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.authentication.model.UserAccount;
import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.exceptions.BankAccountNotFoundException;
import com.orbit.orbit_banking.modules.banking.mapper.DepositMapper;
import com.orbit.orbit_banking.modules.banking.model.Deposit;
import com.orbit.orbit_banking.modules.banking.model.DepositStatus;
import com.orbit.orbit_banking.modules.banking.repository.DepositRepository;
import com.orbit.orbit_banking.modules.banking.service.DepositService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class DepositServiceImpl implements DepositService {

    private final BankAccountRepository bankAccountRepository;
    private final DepositRepository depositRepository;
    private final DepositMapper depositMapper;

    public DepositServiceImpl(BankAccountRepository bankAccountRepository, DepositRepository depositRepository, DepositMapper depositMapper) {
        this.bankAccountRepository = bankAccountRepository;
        this.depositRepository = depositRepository;
        this.depositMapper = depositMapper;
    }

    @Transactional
    @Override
    public Deposit deposit(DepositDto depositDto, UserAccount issuer) {
        // Fetch the beneficiary bank account
        BankAccount bankAccount = bankAccountRepository
                .findByAccountNumber(depositDto.accountNumber()).orElseThrow(()-> new BankAccountNotFoundException(depositDto.accountNumber()));
        // Check if the amount is non-negative and greater than 0
        if(depositDto.amount().equals(BigDecimal.ZERO)){
            throw new IllegalArgumentException("The amount must be positive and greater than 0");
        }

        // Create new deposit record
        Deposit deposit = depositMapper.mapToDto(depositDto);
        deposit.setIssuedBy(issuer);

        // Update the account balance
        bankAccount.setBalance(bankAccount.getBalance().add(depositDto.amount()));
        bankAccountRepository.save(bankAccount);

        // Update the deposit status
        deposit.setStatus(DepositStatus.SUCCESS);

        return depositRepository.save(deposit);

    }
}
