package Clientside.Errors;
import Clientside.Client;
public class AllAloneException extends NullPointerException {

    private Client client;

    public AllAloneException (Client client) {
        this.client = client;
    }
}
