package com.rmsz.rmszclient.test;

import java.util.ArrayList;

/**
 * Created by WALES7 on 2016/12/16.
 */

public class duotai {


    public static void main(String args[]){
        System.out.println("this is main fucntion");

        ArrayList<Object> list= new ArrayList<Object>();
        list.add(new Stringnew1("test11111"));
        list.add(new Stringnew2("test22222"));
        int i = 0;
        do{
            System.out.println(list.get(i));
            i++;
        }while(i<list.size());


    }
}


class Stringnew{
    @Override
    public String toString() {
        return "Stringnew{" +
                "name='" + name + '\'' +
                '}';
    }

    private String name = "Stringnew";
    public Stringnew(String name){
        this.name =name;
    }
}

class Stringnew1 extends Stringnew{

    public Stringnew1(String name) {
        super(name);
    }
}

class Stringnew2 extends Stringnew{

    public Stringnew2(String name) {
        super(name);
    }
}
