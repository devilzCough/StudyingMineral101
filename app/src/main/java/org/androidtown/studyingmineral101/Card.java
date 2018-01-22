package org.androidtown.studyingmineral101;

/**
 * Created by iseungjin on 2018. 1. 23..
 */

// Card Class
// This Class Stores Information for each Card.

public class Card {

    private String cardName;
    private String[] cardInfo;

    // Mineral Card
    public Card (String name, String hardnessValue, String specificGravityValue, String cleavageValue,
                 String crustalAbundanceValue, String economicValue) {

        cardName = name;

        cardInfo = new String[5];
        cardInfo[0] = hardnessValue;
        cardInfo[1] = specificGravityValue;
        cardInfo[2] = cleavageValue;
        cardInfo[3] = crustalAbundanceValue;
        cardInfo[4] = economicValue;

    } // Card()

    public String getCardName() {
        return cardName;
    } // getCardName()

    public String getCardInfo(int cateIndex) {
        return cardInfo[cateIndex];
    } // getCardInfo()
} // Card Class

