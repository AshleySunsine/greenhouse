package ru.ash.greenhouse.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ash.greenhouse.model.BankAccountEntity;


@Repository
public interface BankAccountRepository extends JpaRepository<BankAccountEntity, String> {
}