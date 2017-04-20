package com.jspAuction.application.controller;

import com.jspAuction.application.domain.Item;
import com.jspAuction.application.repository.ItemRepository;
import com.jspAuction.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@RestController
public class ApplicationController {

    protected Logger logger = Logger.getLogger(ApplicationController.class.getName());
    protected ItemRepository itemRepository;

    /**
     * Create an instance plugging in the respository of Items.
     *
     * @param itemRepository
     *            An item repository implementation.
     */
    @Autowired
    public ApplicationController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;

        logger.info("ItemRepository says system has "
                + itemRepository.countItems() + " items");
    }

    /**
     * Fetch an item with the specified item number.
     *
     * @param itemNumber
     *            A numeric, 9 digit item number.
     * @return The item if found.
     * @throws ItemNotFoundException
     *             If the number is not recognised.
     */
    @RequestMapping("/items/{itemNumber}")
    public Item byNumber(@PathVariable("itemNumber") String itemNumber) {

        logger.info("items-service byNumber() invoked: " + itemNumber);
        Item item = itemRepository.findOneById(Long.valueOf(itemNumber));
        logger.info("items-service byNumber() found: " + item);

        if (item == null)
            throw new ItemNotFoundException(itemNumber);
        else {
            return item;
        }
    }

    /**
     * Fetch items with the specified name. A partial case-insensitive match
     * is supported. So <code>http://.../items/seller/a</code> will find any
     * items with upper or lower case 'a' in their name.
     *
     * @param partialName
     * @return A non-null, non-empty set of items.
     * @throws ItemNotFoundException
     *             If there are no matches at all.
     */
    @RequestMapping("/items/seller/{name}")
    public List<Item> bySeller(@PathVariable("name") String partialName) {
        logger.info("items-service bySeller() invoked: "
                + itemRepository.getClass().getName() + " for "
                + partialName);

        List<Item> items = itemRepository.findBySellerContainingIgnoreCase(partialName);
        logger.info("items-service bySeller() found: " + items);

        if (items == null || items.size() == 0)
            throw new ItemNotFoundException(partialName);
        else {
            return items;
        }
    }
}