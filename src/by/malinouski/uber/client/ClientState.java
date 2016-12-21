package by.malinouski.uber.client;

public class ClientState {

    private boolean waiting;
    private boolean riding;
    private boolean calling;
    
    public ClientState() {
        // TODO Auto-generated constructor stub
    }
    
    public boolean isWaiting() {
        return waiting;
    }
    
    public void waiting() {
        waiting = true;
    }
    
    public void riding() {
        riding = true;
    }
    
    public void calling() {
        calling = true;
    }
}
