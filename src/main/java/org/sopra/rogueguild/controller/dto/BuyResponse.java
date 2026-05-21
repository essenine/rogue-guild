package org.sopra.rogueguild.controller.dto;

import org.sopra.rogueguild.repository.model.Item;

public class BuyResponse {
    public enum Status { SUCCESS, NOT_FOUND, NOT_ENOUGH_GOLD }

    private final Status status;
    private final Item item;
    private final int missingGold;
    private final Integer requestedId;

    private BuyResponse(Status status, Item item, int missingGold, Integer requestedId) {
        this.status = status;
        this.item = item;
        this.missingGold = missingGold;
        this.requestedId = requestedId;
    }

    public static BuyResponse success(Item item) {
        return new BuyResponse(Status.SUCCESS, item, 0, null);
    }

    public static BuyResponse notFound(int id) {
        return new BuyResponse(Status.NOT_FOUND, null, 0, id);
    }

    public static BuyResponse notEnoughGold(Item item, int playerGold) {
        int missing = item.getBasePrice() - playerGold;
        return new BuyResponse(Status.NOT_ENOUGH_GOLD, item, missing, null);
    }

    public Status getStatus() { return status; }
    public Item getItem() { return item; }
    public int getMissingGold() { return missingGold; }
    public Integer getRequestedId() { return requestedId; }
}
