package com.jspAuction.service;

import com.jspAuction.DTO.ItemDTO;
import com.jspAuction.domain.Item;
import com.jspAuction.domain.User;
import com.jspAuction.enums.ItemStatus;
import com.jspAuction.repository.ItemRepository;
import com.jspAuction.repository.UserRepository;
import com.jspAuction.security.ContextUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by sunit on 3/20/17.
 */

@Service
@Transactional
public class ItemService {

    private final UserRepository userRepo;
    private final ItemRepository itemRepo;

    @Autowired
    public ItemService(UserRepository userRepo, ItemRepository itemRepo) {
        this.userRepo = userRepo;
        this.itemRepo = itemRepo;
    }

    public Item createNewItem(ItemDTO itemDTO){
        User user = getLoggedUser();
        Item item = new Item();
        item.setSeller(user);
        item.setCost(itemDTO.getItemCostMin());
        item.setDescription(itemDTO.getItemDesc());
        item.setCreated(new Date());
        item.setExpiring(itemDTO.getExpiring());
        item.setItemStatus(ItemStatus.ONSALE);

        itemRepo.save(item);

        return item;
    }

    public User getLoggedUser() {
        ContextUser principal = (ContextUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepo.findOneByUserName(principal.getUser().getUserName());
    }
}
