
package com.orbit.orbit_banking.banking.unit;

import com.orbit.orbit_banking.modules.accounts.model.BankAccount;
import com.orbit.orbit_banking.modules.accounts.repository.BankAccountRepository;
import com.orbit.orbit_banking.modules.banking.dto.TransactionResponse;
import com.orbit.orbit_banking.modules.banking.dto.WithdrawDto;
import com.orbit.orbit_banking.modules.banking.exceptions.InsufficientBalanceException;
import com.orbit.orbit_banking.modules.banking.mapper.TransactionMapper;
import com.orbit.orbit_banking.modules.banking.model.Transaction;
import com.orbit.orbit_banking.modules.banking.model.TransactionType;
import com.orbit.orbit_banking.modules.banking.repository.TransactionRepository;
import com.orbit.orbit_banking.modules.banking.service.implementation.StandardWithdrawService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StandardWithdrawServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private StandardWithdrawService withdrawService;

    private BankAccount bankAccount;
    private WithdrawDto withdrawDto;

    @BeforeEach
    void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setBalance(new BigDecimal("1000.00"));

        withdrawDto = new WithdrawDto(
                "123456789012345678901234",
                new BigDecimal("200.00"),
                null
        );
    }

    @Test
    void withdraw_ShouldCreateTransaction_WhenBalanceIsSufficient()
            throws InsufficientBalanceException {

        Transaction transaction = new Transaction();
        Transaction savedTransaction = new Transaction();
        TransactionResponse expectedResponse = mock(TransactionResponse.class);

        when(bankAccountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(bankAccount));

        when(transactionMapper.mapToTransaction(
                any(BigDecimal.class),
                eq(TransactionType.WITHDRAW),
                any()
        )).thenReturn(transaction);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(savedTransaction);

        when(transactionMapper.mapToDto(savedTransaction))
                .thenReturn(expectedResponse);

        TransactionResponse response = withdrawService.withdraw(withdrawDto);

        assertNotNull(response);
        assertEquals(expectedResponse, response);

        assertEquals(
                new BigDecimal("800.00"),
                bankAccount.getBalance()
        );

        assertEquals(
                new BigDecimal("1000.00"),
                transaction.getBalanceBefore()
        );

        assertEquals(
                new BigDecimal("800.00"),
                transaction.getBalanceAfter()
        );

        verify(bankAccountRepository).findByAccountNumber(anyString());
        verify(transactionRepository).save(transaction);

        verify(transactionMapper).mapToTransaction(
                withdrawDto.amount(),
                TransactionType.WITHDRAW,
                withdrawDto.channel()
        );
    }

    @Test
    void withdraw_ShouldThrowInsufficientBalanceException_WhenBalanceIsInsufficient() {

        withdrawDto = new WithdrawDto(
                "123456789012345678901234",
                new BigDecimal("1500.00"),
                null
        );

        when(bankAccountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(bankAccount));

        assertThrows(
                InsufficientBalanceException.class,
                () -> withdrawService.withdraw(withdrawDto)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw_ShouldRejectNegativeAmount() {

        withdrawDto = new WithdrawDto(
                "123456789012345678901234",
                new BigDecimal("-100.00"),
                null
        );

        when(bankAccountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(bankAccount));

        assertThrows(
                IllegalArgumentException.class,
                () -> withdrawService.withdraw(withdrawDto)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw_ShouldRejectZeroAmount() {

        withdrawDto = new WithdrawDto(
                "123456789012345678901234",
                BigDecimal.ZERO,
                null
        );

        when(bankAccountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(bankAccount));

        assertThrows(
                Exception.class,
                () -> withdrawService.withdraw(withdrawDto)
        );

        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw_ShouldAllowWithdrawalEqualToBalance()
            throws InsufficientBalanceException {

        withdrawDto = new WithdrawDto(
                "123456789012345678901234",
                new BigDecimal("1000.00"),
                null
        );

        when(bankAccountRepository.findByAccountNumber(anyString()))
                .thenReturn(Optional.of(bankAccount));

        Transaction transaction = new Transaction();

        when(transactionMapper.mapToTransaction(
                any(BigDecimal.class),
                eq(TransactionType.WITHDRAW),
                any()
        )).thenReturn(transaction);

        when(transactionRepository.save(any(Transaction.class)))
                .thenReturn(transaction);

        when(transactionMapper.mapToDto(transaction))
                .thenReturn(mock(TransactionResponse.class));

        assertDoesNotThrow(() -> withdrawService.withdraw(withdrawDto));
    }
}
