package com.account.bank.repo;

import com.account.bank.exception.UserNotFoundException;
import com.account.bank.model.Account;
import com.account.bank.model.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepoTest {

    @Autowired
    private AccountRepo accountRepo;

    @AfterEach
    void tearDown() {
        accountRepo.deleteAll();
    }

    @Test
    void getOneAccountByIdTest() {
        //given
        Account account = new Account(12332l);
        accountRepo.save(account);
        //when
        Optional<Account> expected = accountRepo.getOneAccountById(account.getAccountId());
        //then
        assertSame(expected.get(),account);
    }

}