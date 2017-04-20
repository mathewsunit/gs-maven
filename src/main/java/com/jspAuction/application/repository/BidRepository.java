package com.jspAuction.application.repository;

import com.jspAuction.application.domain.Bid;
import com.jspAuction.application.domain.Item;
import com.jspAuction.application.enums.BidStatus;
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
