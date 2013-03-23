/**
 * 
 */
package com.uta.byos;

import java.util.Comparator;
import java.util.EnumSet;

/**
 * @author Wildman
 *
 */
public class CardComparator implements Comparator<Card> {

	@Override
	public int compare(Card arg0, Card arg1) {
		if(arg0.mCardLand == arg1.mCardLand){
			return arg0.mCardValue - arg1.mCardValue;
		}else{
			int i0 = 0, i1 = 0, it = 0;
			for(Card.CardLand suit : EnumSet.range(Card.CardLand.EClub, Card.CardLand.ESpade)){
				it += 1;
				if(arg0.mCardLand == suit)
					i0 = it;
				if(arg1.mCardLand == suit)
					i1 = it;
			}
			return i1 - i0;
		}
	}

}
