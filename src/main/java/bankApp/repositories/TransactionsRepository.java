package bankApp.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bankApp.model.*;

@Repository
public interface TransactionsRepository extends JpaRepository<Transaction, Integer> {

	public Iterable<Transaction> findTransactions(boolean approvalStatus, Date decisionDate);
	public Iterable<Transaction> findAllApprovedTransactionsByType(boolean approvalStatus, Date decisionDate, String transactionType);

}
