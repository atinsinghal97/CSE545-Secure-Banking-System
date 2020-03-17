package bankApp.repositories;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bankApp.model.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

	public Iterable<Account> findAccountsByApprovalStatus(boolean approvalStatus);
	public Iterable<Account> findAccountsByUser(String username);	
	public Optional<Account> findAccountsByAccountNumber(String accountNumber);
}
