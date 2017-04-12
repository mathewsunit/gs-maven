package com.jspAuction.repository;

import com.jspAuction.domain.Bid;
import com.jspAuction.domain.Item;
import com.jspAuction.enums.BidStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* Created by sunit on 3/19/17.
*/

@Repository
public interface BidRepository extends CrudRepository<Bid, Long> {
    Bid findOneById(Long bidId);
    Bid findOneByItemAndBidStatus(Item item, BidStatus bidStatus);
    List<Bid> findByItem(Item item);
    int countByItem(Item item);
}
