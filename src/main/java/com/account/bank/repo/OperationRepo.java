package com.account.bank.repo;

import com.account.bank.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepo extends JpaRepository<Operation,Long> {
}