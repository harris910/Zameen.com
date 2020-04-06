/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class Trend {
    String SearchedKeyword;
    Integer TimesSearched;//keep the track of the count of SearchedKeyword

    public Trend(String SearchedKeyword, Integer TimesSearched) {
        this.SearchedKeyword = SearchedKeyword;
        this.TimesSearched = TimesSearched;
    }

    public String getSearchedKeyword() {
        return SearchedKeyword;
    }

    public Integer getTimesSearched() {
        return TimesSearched;
    }
    void Print(){
        System.out.println("Searched Keyword: "+SearchedKeyword+"  Searched Count: "+TimesSearched);
    }

}
