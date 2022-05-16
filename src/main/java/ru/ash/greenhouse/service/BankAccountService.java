package ru.ash.greenhouse.service;

import org.springframework.data.domain.Pageable;
import ru.ash.greenhouse.model.BankAccountDto;

import javax.transaction.Transactional;
import java.util.List;

public interface BankAccountService {
    BankAccountDto create(BankAccountDto bankAccountDto);

    @Transactional
    void remove (String numberAccount);

    List<BankAccountDto> getAll(Pageable pageable);

}