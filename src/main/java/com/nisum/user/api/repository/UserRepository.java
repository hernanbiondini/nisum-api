package com.nisum.user.api.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.nisum.user.api.model.User;

@Repository
public interface UserRepository  extends PagingAndSortingRepository<User, String> {
	
	Optional<User> findByEmail(String email);
	
	List<User> findAll();

}
