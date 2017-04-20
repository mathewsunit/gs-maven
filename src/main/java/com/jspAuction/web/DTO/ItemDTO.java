package com.jspAuction.web.DTO;

import javax.validation.constraints.NotNull;
import java.util.Date;


/**
 * Created by sunit on 3/19/17.
 */

public class ItemDTO {


    @NotNull
    private Long itemCostMin;
    @NotNull
    private String itemName;
    @NotNull
    private String itemDesc;
    @NotNull
    private Date expiring;

    public Long getItemCostMin() {
        return itemCostMin;
    }

    public void setItemCostMin(Long itemCostMin) {
        this.itemCostMin = itemCostMin;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public Date getExpiring() {
        return expiring;
    }

    public void setExpiring(Date expiring) {
        this.expiring = expiring;
    }
}
