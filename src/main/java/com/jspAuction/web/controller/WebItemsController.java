package com.jspAuction.web.controller;

import com.jspAuction.application.domain.Item;
import com.jspAuction.web.service.SearchCriteria;
import com.jspAuction.web.service.WebItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by sunit on 4/20/17.
 */
@Controller
public class WebItemsController {

    @Autowired
    protected WebItemsService itemsService;

    protected Logger logger = Logger.getLogger(WebItemsController.class
            .getName());

    public WebItemsController(WebItemsService itemsService) {
        this.itemsService = itemsService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAllowedFields("itemNumber", "searchText");
    }

    @RequestMapping("/items")
    public String goHome() {
        return "index";
    }

    @RequestMapping("/items/{itemNumber}")
    public String byNumber(Model model,
                           @PathVariable("itemNumber") String itemNumber) {

        logger.info("web-service byNumber() invoked: " + itemNumber);

        Item item = itemsService.findByNumber(itemNumber);
        logger.info("web-service byNumber() found: " + item);
        model.addAttribute("item", item);
        return "item";
    }

    @RequestMapping("/items/owner/{text}")
    public String ownerSearch(Model model, @PathVariable("text") String name) {
        logger.info("web-service byOwner() invoked: " + name);

        List<Item> items = itemsService.bySellerContains(name);
        logger.info("web-service byOwner() found: " + items);
        model.addAttribute("search", name);
        if (items != null)
            model.addAttribute("items", items);
        return "items";
    }

    @RequestMapping(value = "/items/search", method = RequestMethod.GET)
    public String searchForm(Model model) {
        model.addAttribute("searchCriteria", new SearchCriteria());
        return "itemSearch";
    }

    @RequestMapping(value = "/items/dosearch")
    public String doSearch(Model model, SearchCriteria criteria,
                           BindingResult result) {
        logger.info("web-service search() invoked: " + criteria);

        criteria.validate(result);

        if (result.hasErrors())
            return "itemSearch";

        String itemNumber = criteria.getItemNumber();
        if (StringUtils.hasText(itemNumber)) {
            return byNumber(model, itemNumber);
        } else {
            String searchText = criteria.getSearchText();
            return ownerSearch(model, searchText);
        }
    }
}
