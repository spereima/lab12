package ua.ucu.apps;

public abstract class CoinHandler {
    protected CoinHandler next;
    protected int denomination;

    public CoinHandler(int denomination) {
        this.denomination = denomination;
    }

    public void setNext(CoinHandler next) {
        this.next = next;
    }

    public void handle(int amount) {
        if (amount >= denomination) {
            int count = amount / denomination;
            int remainder = amount % denomination;
            System.out.println("Use " + count + " coin(s) of " + denomination);
            if (remainder > 0 && next != null) {
                next.handle(remainder);
            } else if (remainder > 0) {
                System.out.println("Cannot provide exact change for " + amount);
            }
        } else if (next != null) {
            next.handle(amount);
        } else {
            System.out.println("Cannot provide exact change for " + amount);
        }
    }
}
