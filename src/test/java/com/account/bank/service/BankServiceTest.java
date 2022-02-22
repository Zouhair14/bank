package com.account.bank.service;

import com.account.bank.exception.UserNotFoundException;
import com.account.bank.model.Account;
import com.account.bank.model.Operation;
import com.account.bank.repo.AccountRepo;
import com.account.bank.repo.OperationRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    private BankService bankService;
    @Mock
    private AccountRepo accountRepo;
    @Mock
    private OperationRepo operationRepo;

    @BeforeEach
    void setUp() {
        bankService = new BankService(accountRepo,operationRepo);
    }

    @Test
    void addAccount() {
        //given
        Account account = new Account(12332l);
        //when
        bankService.addAccount(account);
        //then
        ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
        Mockito.verify(accountRepo).save(accountArgumentCaptor.capture());
        assertThat(accountArgumentCaptor.getValue()).isEqualTo(account);
    }

    @Test
    void allAccount() {
        //when
        bankService.allAccount();
        //then
        Mockito.verify(accountRepo).findAll();
    }

    @Test
    void oneAccount() {
        //given
        //when
        Account account = new Account(10l,12332l);
        BDDMockito.given(accountRepo.getOneAccountById(account.getAccountId())).willReturn(Optional.of(account));
        //then
        bankService.oneAccount(account.getAccountId());
        verify(accountRepo).getOneAccountById(account.getAccountId());

    }

    @Test
    void throwOnAccountTest(){
        //given
        Account account = new Account(10l,12332l);
        //then
        assertThatThrownBy(()-> bankService.oneAccount(account.getAccountId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User "+account.getAccountId()+" was not found");
    }

    @Test
    void deleteAccount() {
        //given
        Account account = new Account(10l,12332l);
        //then
        when(accountRepo.getOneAccountById(account.getAccountId())).thenReturn(Optional.of(account));
        bankService.deleteAccount(account.getAccountId());
        verify(accountRepo).deleteById(account.getAccountId());
    }

    @Test
    void throwDeleteTest(){
        //given
        Account account = new Account(10l,12332l);
        //then
        assertThatThrownBy(()-> bankService.deleteAccount(account.getAccountId()))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("User "+account.getAccountId()+" was not found");
    }

    @Test
    void retrieveAllMoneyOperation() {
        Account account = new Account(10l,300l);
        List<Operation> ops = new ArrayList<>();
        Operation operation = new Operation();
        ops.add(operation);
        operation.setAmount(400l);operation.setDateOfOperation(new Date());
        account.setOperations(ops);
        operation.setAccount(account);
        BDDMockito.given(accountRepo.getOneAccountById(account.getAccountId())).willReturn(Optional.of(account));
        assertThatThrownBy(()-> bankService.retrieveMoneyOperation(operation))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessageContaining("you do not have enough money");
    }

    @Test
    void allOperation() {
        //when
        bankService.allOperation();
        //then
        Mockito.verify(operationRepo).findAll();
    }

    @Test
    void findOperationsByAccount() {
        //given
        //when
        Account account = new Account(10l,12332l);
        List<Operation> ops = new ArrayList<>();
        Operation operation = new Operation();
        ops.add(operation);
        operation.setAmount(3333l);operation.setDateOfOperation(new Date());
        account.setOperations(ops);
        operation.setAccount(account);
        BDDMockito.given(accountRepo.getOneAccountById(account.getAccountId())).willReturn(Optional.of(account));
        //then
        bankService.findOperationsByAccount(account.getAccountId());
        verify(accountRepo).getOneAccountById(account.getAccountId());
    }
}