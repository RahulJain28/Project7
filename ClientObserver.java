/**
 * Project 7 Chat Room
 * EE 422C submission by
 * Aditya Kharosekar amk3587
 * Rahul Jain rj8656
 * Fall 2016
 * Slip days used - 1 (on this project) (overall - 2)
 * This is the second slip day that we have used this semester
 */
package assignment7;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

public class ClientObserver extends PrintWriter implements Observer{

    ClientObserver(OutputStream out) {
        super(out);
    }

    @Override
    public void update(Observable observable, Object o) {
        this.println(o);
        this.flush();
    }
}