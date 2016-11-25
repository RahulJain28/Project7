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