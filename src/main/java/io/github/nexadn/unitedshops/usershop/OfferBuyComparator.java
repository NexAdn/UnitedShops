package io.github.nexadn.unitedshops.usershop;

import java.util.Comparator;

public class OfferBuyComparator implements Comparator<Offer> {

    public int compare (Offer o1, Offer o2)
    {
        return (o1.getBuyPrice() < o2.getBuyPrice()) ? -1 : ((o1.getBuyPrice() == o2.getBuyPrice()) ? 0 : 1);
    }

}
