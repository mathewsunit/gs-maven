package com.jspAuction.application.repository;

import com.jspAuction.application.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
* Created by sunit on 3/19/17.
*/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findOneByUserName(String userName);
}
