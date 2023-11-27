package sk.uniba.fmph.dcs;

public class ConsoleGameObserver implements ObserverInterface{
    @Override
    public void notifyEverybody(String newState) {
        System.out.println(newState);
    }
}
