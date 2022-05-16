package ru.ash.greenhouse.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.ash.greenhouse.model.BankAccountDto;
import ru.ash.greenhouse.repo.BankAccountRepository;


import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    public static final String YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS = "Your account has insufficient funds";
    private final BankAccountRepository repository;
    private final BankAccountMapper bankAccountMapper;

    @Transactional
    @Override
    public BankAccountDto create(BankAccountDto bankAccountDto) {
        return bankAccountMapper.toDto(repository.saveAndFlush(bankAccountMapper.toEntity(bankAccountDto)));
    }

    @Transactional
    @Override
    public void remove (String numberAccount) {
        repository.deleteById(numberAccount);
    }

    @Override
    public List<BankAccountDto> getAll(Pageable pageable) {
        return bankAccountMapper.toDtos(repository.findAll(pageable).toList());
    }

    private BankAccountEntity getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @Transactional
    @Override
    public void dispatchMoney(DispatchMoneyDto dispatchMoneyDto) {
        BankAccountEntity sourceBankAccount = getById(dispatchMoneyDto.getSourceId());
        BigDecimal money = dispatchMoneyDto.getMoney();
        BigDecimal sourceBalance = sourceBankAccount.getBalance();
        if (sourceBalance.compareTo(money) < 0) {
            log.error(YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS);
            throw new BankAccountException(YOUR_ACCOUNT_HAS_INSUFFICIENT_FUNDS);
        }
        BigDecimal newBalanceSource = sourceBalance.subtract(money);
        sourceBankAccount.setBalance(newBalanceSource);
        BankAccountEntity targetBankAccount = getById(dispatchMoneyDto.getTargetId());
        BigDecimal newBalanceTarget = targetBankAccount.getBalance().add(money);
        targetBankAccount.setBalance(newBalanceTarget);
    }
}