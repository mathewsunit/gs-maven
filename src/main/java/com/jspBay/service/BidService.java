package com.jspBay.service;

import com.jspBay.DTO.BidDTO;
import com.jspBay.domain.Bid;
import com.jspBay.domain.Item;
import com.jspBay.domain.User;
import com.jspBay.enums.BidStatus;
import com.jspBay.repository.BidRepository;
import com.jspBay.repository.ItemRepository;
import com.jspBay.repository.UserRepository;
import com.jspBay.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class BidService {

    private final ItemRepository itemRepo;
    private final UserRepository userRepo;
    private final BidRepository bidRepo;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public BidService(ItemRepository itemRepo, UserRepository userRepo, BidRepository bidRepo) {
        this.itemRepo = itemRepo;
        this.userRepo = userRepo;
        this.bidRepo = bidRepo;
    }

    public Bid createNewBid(BidDTO bidDTO){

        Item item = itemRepo.findOneById(bidDTO.getItemId());

        //Check if bid amount is too low
        if(bidDTO.getBidAmount()<=item.getCost()){
            return null;
        }

        //Check if item already has a bid
        if(null!=bidRepo.findByItem(item)){
            //Update the leading bid in table to REJECTED
            Bid bid = bidRepo.findOneByItemAndBidStatus(item,BidStatus.LEADING);
            bid.setBidStatus(BidStatus.REJECTED);
            bidRepo.save(bid);
        }

        //Create new bid and save to db
        Bid bid = new Bid();
        bid.setCreated(new Date());
        bid.setBidder(getLoggedUser());
        bid.setBidStatus(BidStatus.LEADING);
        bid.setItem(item);
        bid.setValue(bidDTO.getBidAmount());
        bidRepo.save(bid);

        item.setCost(bidDTO.getBidAmount());
        itemRepo.save(item);

        return bid;
    }


    public User getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findOneByUserName(principal.getUser().getUserName());
    }
}
