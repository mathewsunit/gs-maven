package com.jspBay.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


/**
 * Created by sunit on 3/19/17.
 */

@Getter
@Setter
public class BidDTO {

    @NotNull
    private Long itemId;

    @NotNull
    private Long bidAmount;

    public Long getBidAmount() {
        return bidAmount;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setBidAmount(Long bidAmount) {
        this.bidAmount = bidAmount;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

}
