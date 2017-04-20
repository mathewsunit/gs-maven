package com.jspAuction.web.service;

import com.jspAuction.application.domain.Item;
import com.jspAuction.exceptions.ItemNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@Service
public class WebItemsService {

    @Autowired
    @LoadBalanced
    protected RestTemplate restTemplate;

    protected String serviceUrl;

    protected Logger logger = Logger.getLogger(WebItemsService.class.getName());

    public WebItemsService(String serviceUrl) {
        this.serviceUrl = serviceUrl.startsWith("http") ? serviceUrl
                : "http://" + serviceUrl;
    }

    /**
     * The RestTemplate works because it uses a custom request-factory that uses
     * Ribbon to look-up the service to use. This method simply exists to show
     * this.
     */
    @PostConstruct
    public void demoOnly() {
        // Can't do this in the constructor because the RestTemplate injection
        // happens afterwards.
        logger.warning("The RestTemplate request factory is "
                + restTemplate.getRequestFactory().getClass());
    }

    public Item findByNumber(String itemNumber) {

        logger.info("findByNumber() invoked: for " + itemNumber);
        return restTemplate.getForObject(serviceUrl + "/item/{number}",
                Item.class, itemNumber);
    }

    public List<Item> bySellerContains(String name) {
        logger.info("bySellerContains() invoked:  for " + name);
        Item[] item = null;

        try {
            item = restTemplate.getForObject(serviceUrl
                    + "/item/seller/{name}", Item[].class, name);
        } catch (HttpClientErrorException e) { // 404
            // Nothing found
        }

        if (item == null || item.length == 0)
            return null;
        else
            return Arrays.asList(item);
    }

    public Item getByNumber(String itemNumber) {
        Item account = restTemplate.getForObject(serviceUrl
                + "/item/{number}", Item.class, itemNumber);

        if (account == null)
            throw new ItemNotFoundException(itemNumber);
        else
            return account;
    }
}