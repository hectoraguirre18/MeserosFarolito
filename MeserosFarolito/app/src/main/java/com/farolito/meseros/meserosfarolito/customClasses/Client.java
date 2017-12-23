package com.farolito.meseros.meserosfarolito.customClasses;

/**
 * Created by Hector on 22/12/2017.
 */

public class Client {
    private String name;
    private int count;

    public Client(String name_, int count_){
        name = name_;
        count = count_;
    }

    public String getName(){
        return this.name;
    }

    public int getCount(){
        return this.count;
    }

    public String getCountAsString(){
        return String.valueOf(this.count);
    }

}
