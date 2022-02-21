package com.account.bank;

import com.account.bank.model.Account;
import com.account.bank.model.Operation;
import com.account.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/Accounts")
public class BankResource {

    private final BankService bankService;

    @Autowired
    public BankResource(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping
    public ResponseEntity <List<Account>> getAllAccount(){
        List<Account> account = bankService.allAccount();
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @PostMapping("/addAccount")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        Account naccount = bankService.addAccount(account);
        return new ResponseEntity<>(naccount, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteAccount/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable("id") Long id){
        bankService.deleteAccount(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/findAccount/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id){
        Account account = bankService.oneAccount(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
    @GetMapping("/findOperations/{id}")
    public ResponseEntity<List<Operation>> getOperationOfAccount(@PathVariable("id") Long id){
        List<Operation> operation = bankService.findOperationsByAccount(id);
        return new ResponseEntity<>(operation, HttpStatus.OK);
    }
    @PostMapping("/saveOperation")
    public ResponseEntity<Operation> saveOperation(@RequestBody Operation operation){
        Operation nOperation = bankService.saveMoneyOperation(operation);
        return new ResponseEntity<>(nOperation, HttpStatus.CREATED);
    }
    @PostMapping("/retrieveOperation")
    public ResponseEntity<Operation> retrieveOperation(@RequestBody Operation operation) throws Exception {
        Operation nOperation = bankService.retrieveMoneyOperation(operation);
        return new ResponseEntity<>(nOperation, HttpStatus.CREATED);
    }
    @PostMapping("/retrieveAllOperation")
    public ResponseEntity<Operation> retrieveAllOperation(@RequestBody Operation operation) throws Exception {
        Operation nOperation = bankService.retrieveAllMoneyOperation(operation);
        return new ResponseEntity<>(nOperation, HttpStatus.CREATED);
    }
    @GetMapping("/historic")
    public ResponseEntity <List<Operation>> getAllOperations(){
        List<Operation> operations = bankService.allOperation();
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }


}
