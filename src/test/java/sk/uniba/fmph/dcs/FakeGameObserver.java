package sk.uniba.fmph.dcs;

public class FakeGameObserver implements ObserverInterface{
    @Override
    public void notifyEveryBody(String newState) {
        System.out.println(newState);
    }
}
