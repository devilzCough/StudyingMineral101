package org.androidtown.studyingmineral101;

/**
 * Created by iseungjin on 2018. 1. 24..
 */

public class Rank {

    private int rank;
    private String name;
    private int score;

    public Rank(String _rank, String _name, String _score) {

        rank = Integer.parseInt(_rank);
        name = _name;
        score = Integer.parseInt(_score);
    }

    public int getRank() { return rank; }
    public String getName() { return name; }
    public int getScore() { return score; }

    public void setRank(int _rank) { rank = _rank; }
    public void setName(String _name) { name = _name; }
    public void setScore(int _score) { score = _score; }
}
