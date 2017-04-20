package com.jspAuction.web.service;

import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

/**
 * Created by sunit on 4/20/17.
 */
public class SearchCriteria {
    private String itemNumber;

    private String searchText;

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isValid() {
        if (StringUtils.hasText(itemNumber))
            return !(StringUtils.hasText(searchText));
        else
            return (StringUtils.hasText(searchText));
    }

    public boolean validate(Errors errors) {
        if (StringUtils.hasText(itemNumber)) {
            if (itemNumber.length() != 9)
                errors.rejectValue("itemNumber", "badFormat",
                        "Item number should be 9 digits");
            else {
                try {
                    Integer.parseInt(itemNumber);
                } catch (NumberFormatException e) {
                    errors.rejectValue("itemNumber", "badFormat",
                            "Item number should be 9 digits");
                }
            }

            if (StringUtils.hasText(searchText)) {
                errors.rejectValue("searchText", "nonEmpty",
                        "Cannot specify item number and search text");
            }
        } else if (StringUtils.hasText(searchText)) {
            // Nothing to do
        } else {
            errors.rejectValue("itemNumber", "nonEmpty",
                    "Must specify either an item number or search text");

        }

        return errors.hasErrors();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return (StringUtils.hasText(itemNumber) ? "number: " + itemNumber
                : "")
                + (StringUtils.hasText(searchText) ? " text: " + searchText
                : "");
    }
}
