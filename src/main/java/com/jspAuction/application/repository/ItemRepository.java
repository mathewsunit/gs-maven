package com.jspAuction.application.repository;

import com.jspAuction.application.domain.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/
@Repository
public interface ItemRepository extends CrudRepository<Item, Long> {
    Item findOneById(Long id);

    List<Item> findBySellerContainingIgnoreCase(String partialName);

    @Query("SELECT count(*) from Item")
    String countItems();
}
