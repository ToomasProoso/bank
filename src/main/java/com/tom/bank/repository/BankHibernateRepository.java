package com.tom.bank.repository;

import com.tom.bank.objects.CreateAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankHibernateRepository extends JpaRepository<CreateAccount, String> {
 CreateAccount getByAccountNumber(String accountNumber);
 List <CreateAccount> findAllByAccountNumberContaining (String accountNumber);

}
