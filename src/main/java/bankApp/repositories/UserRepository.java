package bankApp.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import bankApp.model.*;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
	public Optional<User> findUser(String username);
}
