package me.nexadn.unitedshops.shop.user;

import java.util.Comparator;

public class OfferBuyComparator implements Comparator<Offer> {

    @Override
    public int compare(Offer o1, Offer o2) {
        return (o1.getBuyPrice() < o2.getBuyPrice()) ? -1 : ((o1.getBuyPrice() == o2.getBuyPrice()) ? 0 : 1);
    }

}