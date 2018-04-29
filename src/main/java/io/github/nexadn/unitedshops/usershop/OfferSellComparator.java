package io.github.nexadn.unitedshops.usershop;

import java.util.Comparator;

public class OfferSellComparator implements Comparator<Offer> {

    public int compare (Offer o1, Offer o2)
    {
        return (o1.getSellPrice() < o2.getSellPrice()) ? -1 : ((o1.getSellPrice() == o2.getSellPrice()) ? 0 : 1);
    }

}
