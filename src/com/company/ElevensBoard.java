package com.company;

import com.sun.javafx.scene.control.skin.IntegerFieldSkin;

import java.util.List;
import java.util.ArrayList;

/**
 * The ElevensBoard class represents the board in a game of Elevens.
 */
public class ElevensBoard extends Board {

    /**
     * The size (number of cards) on the board.
     */
    private static final int BOARD_SIZE = 9;

    /**
     * The ranks of the cards for this game to be sent to the deck.
     */
    private static final String[] RANKS =
            {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};

    /**
     * The suits of the cards for this game to be sent to the deck.
     */
    private static final String[] SUITS =
            {"spades", "hearts", "diamonds", "clubs"};

    /**
     * The values of the cards for this game to be sent to the deck.
     */
    private static final int[] POINT_VALUES =
            {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0, 0, 0};

    /**
     * Creates a new <code>ElevensBoard</code> instance.
     */
    public ElevensBoard() {
        super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
    }

    /**
     * Determines if the selected cards form a valid group for removal.
     * In Elevens, the legal groups are (1) a pair of non-face cards
     * whose values add to 11, and (2) a group of three cards consisting of
     * a jack, a queen, and a king in some order.
     * @param selectedCards the list of the indices of the selected cards.
     * @return true if the selected cards form a valid group for removal;
     *         false otherwise.
     */
    @Override
    public boolean isLegal(List<Integer> selectedCards) {
		/* *** TO BE MODIFIED IN ACTIVITY 11 *** */
        if (selectedCards.size() == 2) {
            return findPairSum11(selectedCards).size()>0;
        } else if (selectedCards.size() == 3) {
            return findJQK(selectedCards).size()>0;
        } else {
            return false;
        }
    }

    /**
     * Determine if there are any legal plays left on the board.
     * In Elevens, there is a legal play if the board contains
     * (1) a pair of non-face cards whose values add to 11, or (2) a group
     * of three cards consisting of a jack, a queen, and a king in some order.
     * @return true if there is a legal play left on the board;
     *         false otherwise.
     */
    @Override
    public boolean anotherPlayIsPossible() {
		/* *** TO BE MODIFIED IN ACTIVITY 11 *** */
        List<Integer> cIndexes = cardIndexes();
        return findPairSum11(cIndexes).size()>0 || findJQK(cIndexes).size()>0;
    }

    /**
     * Look for an 11-pair in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find an 11-pair.
     * @return a list of the indexes of an 11-pair, if an 11-pair was found;
     *         an empty list, if an 11-pair was not found.
     */
    private ArrayList<Integer> findPairSum11(List<Integer> selectedCards) {
		/* *** TO BE CHANGED INTO findPairSum11 IN ACTIVITY 11 *** */
        ArrayList <Integer> a=new ArrayList<>();
        if (selectedCards.size()>1) {
            for (int sk1 = 0; sk1 < selectedCards.size()-1; sk1++) {
                int k1 = selectedCards.get(sk1).intValue();
                for (int sk2 = sk1 + 1; sk2 < selectedCards.size(); sk2++) {
                    int k2 = selectedCards.get(sk2).intValue();
                    if (cardAt(k1).pointValue() + cardAt(k2).pointValue() == 11) {
                        a.add(k1);
                        a.add(k2);
                    }
                }
            }
        }
        return a;
    }

    /**
     * Look for a JQK in the selected cards.
     * @param selectedCards selects a subset of this board.  It is list
     *                      of indexes into this board that are searched
     *                      to find a JQK group.
     * @return a list of the indexes of a JQK, if a JQK was found;
     *         an empty list, if a JQK was not found.
     */
    private ArrayList<Integer> findJQK(List<Integer> selectedCards) {
		/* *** TO BE CHANGED INTO findJQK IN ACTIVITY 11 *** */
        ArrayList<Integer> a=new ArrayList<>();
        if (selectedCards.size()>2)
        for (int i=0;i<selectedCards.size()-2;i++) {
            for(int j=i+1;j<selectedCards.size()-1;j++) {
                for (int f=i+2;f<selectedCards.size();f++) {
                    if ((cardAt(selectedCards.get(i)).pointValue() + cardAt(selectedCards.get(j)).pointValue() + cardAt(selectedCards.get(f)).pointValue()) == 0) {
                        a.add(i);
                        a.add(j);
                        a.add(f);
                    }
                }
            }
        }
        return a;
    }

    /**
     * Looks for a legal play on the board.  If one is found, it plays it.
     * @return true if a legal play was found (and made); false othewise.
     */
    public boolean playIfPossible() {
       boolean b=false;
        b=b||playPairSum11IfPossible();
        if(!b)
            b=b||playJQKIfPossible();
        return b;
    }

    /**
     * Looks for a pair of non-face cards whose values sum to 11.
     * If found, replace them with the next two cards in the deck.
     * The simulation of this game uses this method.
     * @return true if an 11-pair play was found (and made); false othewise.
     */
    private boolean playPairSum11IfPossible() {
		/* *** TO BE IMPLEMENTED IN ACTIVITY 11 *** */
        System.out.print("pair  ");
        List<Integer> cIndexes = cardIndexes();
        ArrayList<Integer> a=findPairSum11(cIndexes);
        cIndexes=new ArrayList<>();
        if (a.size()>0){
            System.out.print("... ");
            cIndexes.add(a.get(0));
            cIndexes.add(a.get(1));
            replaceSelectedCards(cIndexes);
            System.out.println("y");
        }
        System.out.println("");
        return  a.size()>0;
    }

    /**
     * Looks for a group of three face cards JQK.
     * If found, replace them with the next three cards in the deck.
     * The simulation of this game uses this method.
     * @return true if a JQK play was found (and made); false othewise.
     */
    private boolean playJQKIfPossible() {
        System.out.print("JQK  ");
        List<Integer> cIndexes = cardIndexes();
         ArrayList<Integer>a=findJQK(cIndexes);
        cIndexes=new ArrayList<>();
        if(a.size()>0){
            System.out.print("... ");
            cIndexes.add(a.get(0));
            cIndexes.add(a.get(1));
            cIndexes.add(a.get(2));
            replaceSelectedCards(cIndexes);
            System.out.println("y");
        }
        System.out.println();
        return a.size()>0;
    }
}
