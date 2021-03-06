package com.jspAuction.repository;

import com.jspAuction.domain.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
* Created by sunit on 3/19/17.
*/
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Item findOneById(Long id);
}
