package com.orbit.orbit_banking.banking.unit;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.TransferDto;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.implementation.InternalTransferServiceImpl;
import com.orbit.orbit_banking.modules.banking.utils.AccountNumberExtractor;
import com.orbit.orbit_banking.modules.banking.utils.TransactionReferenceGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;



public class InternalTransferServiceUnitTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private AccountNumberExtractor accountNumberExtractor;

    @Mock
    private TransactionReferenceGenerator transactionReferenceGenerator;

    @InjectMocks
    private InternalTransferServiceImpl internalTransferService;

    private BankAccount sender;
    private BankAccount beneficiary;
    private TransferDto transferDto;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        sender = new BankAccount();
        sender.setId(UUID.randomUUID());
        sender.setBalance(BigDecimal.valueOf(1000.00));
        sender.setAccountNumber("ORB22009");


        beneficiary = new BankAccount();
        beneficiary.setId(UUID.randomUUID());
        beneficiary.setBalance(BigDecimal.valueOf(200.0));
        sender.setAccountNumber("ORBT3092");

        transferDto = new TransferDto();
        transferDto.setAmount(BigDecimal.valueOf(300));
        transferDto.setType("INTERNAL");
        transferDto.setBeneficiaryRIB("000000ORB2200982");
        transferDto.setSenderAccountNumber("ORB22009");

    }

    @Test
    void testInternalTransferSuccess(){

        Transaction expected = new Transaction();
        expected.setTransactionId(UUID.randomUUID());


        String beneficiaryAccountNumber = accountNumberExtractor.extractAccountNumber(transferDto.getBeneficiaryRIB());

        when(bankAccountRepository.findByAccountNumber(transferDto.getSenderAccountNumber()))
                .thenReturn(Optional.of(sender));

        when(bankAccountRepository.findByAccountNumber(beneficiaryAccountNumber))
                .thenReturn(Optional.of(beneficiary));

        when(transactionRepository.save(any()))
                .thenReturn(expected);

        TransactionResponse transactionResponse =
                internalTransferService.transfer(TransactionType.TRANSFER, transferDto);

        assertNotNull(transactionResponse);
        assertEquals(sender.getBalance(), BigDecimal.valueOf(700.0));
        assertEquals(beneficiary.getBalance(), BigDecimal.valueOf(500.0));
    }

    @Test
    void testInternalTransferThrowsInsufficientBalance(){}
}
