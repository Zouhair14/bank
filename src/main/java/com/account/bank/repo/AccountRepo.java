package com.account.bank.repo;

import com.account.bank.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {
    @Query("SELECT u FROM Account u WHERE u.accountId = ?1")
    Optional<Account> getOneAccountById(Long id);
}
