package sk.uniba.fmph.dcs;

public class FakeGameObserver implements GameObserverInterface{
    @Override
    public void notifyEveryBody(String newState) {
        System.out.println(newState);
    }
}
