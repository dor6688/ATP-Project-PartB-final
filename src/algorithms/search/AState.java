package algorithms.search;

public abstract class AState {

    public AState(){
        arrived = false;
    }

    protected int costToMove;

    protected boolean arrived;

    protected AState cameFrom;


}
