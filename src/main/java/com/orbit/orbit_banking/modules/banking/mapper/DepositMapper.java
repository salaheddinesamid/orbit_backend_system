package com.orbit.orbit_banking.modules.banking.mapper;

import com.orbit.orbit_banking.modules.banking.dto.DepositDto;
import com.orbit.orbit_banking.modules.banking.model.Deposit;
import com.orbit.orbit_banking.modules.banking.model.DepositStatus;
import org.springframework.stereotype.Component;

@Component
public class DepositMapper {

    public Deposit mapToDto(DepositDto dto){
        Deposit deposit = new Deposit();
        deposit.setAmount(dto.amount());
        deposit.setStatus(DepositStatus.EXECUTED);

        return deposit;
    }
}
