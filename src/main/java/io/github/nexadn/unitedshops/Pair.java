package io.github.nexadn.unitedshops;

public class Pair<L, R> {
    public L first;
    public R second;

    public Pair()
    {
    }

    public Pair(L firstVal, R secondVal)
    {
        this.first = firstVal;
        this.second = secondVal;
    }

    @Override
    public String toString ()
    {
        return "Pair:" + this.first.toString() + ";" + this.second.toString();
    }
}

/*
 * Copyright (C) 2018 Adrian Schollmeyer
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