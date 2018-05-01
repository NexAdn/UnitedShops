package io.github.nexadn.unitedshops.usershop;

import java.util.Comparator;

public class OfferSellComparator implements Comparator<Offer> {

    public int compare (Offer o1, Offer o2)
    {
        return (o1.getSellPrice() < o2.getSellPrice()) ? -1 : ((o1.getSellPrice() == o2.getSellPrice()) ? 0 : 1);
    }

}

/*
 * Copyright (C) 2017, 2018 Adrian Schollmeyer
 * 
 * This file is part of UnitedShops.
 * 
 * UnitedShops is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
