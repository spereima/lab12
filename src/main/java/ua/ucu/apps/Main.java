package ua.ucu.apps;

public class Main {
    public static void main(String[] args) {
        Group<Integer> nestedGroup = new Group<>();
        nestedGroup.addTask(new Signature<>(System.out::println)).addTask(new Signature<>(x -> System.out.println(x * x)));
        Group<Integer> group = new Group<>();
        group.addTask(nestedGroup).addTask(new Signature<>(x -> System.out.println(x * x * x)));
        group.apply(10);

        CoinHandler h10 = new CoinHandler(10) {};
        CoinHandler h5 = new CoinHandler(5) {};
        CoinHandler h1 = new CoinHandler(1) {};

        h10.setNext(h5);
        h5.setNext(h1);

        int targetAmount = 28;
        h10.handle(targetAmount);

        Group<Integer> nestedGroup1 = new Group<>();
        nestedGroup1.addTask(new Signature<>(x -> System.out.println("Nested: " + x)));

        Group<Integer> group1 = new Group<>();
        group1.addTask(nestedGroup1)
             .addTask(new Signature<>(x -> System.out.println("Outer: " + x)));

        group1.apply(5);
        // System.out.println("Outer signature groupId: " + group1.getHeader("groupId"));
        for (Task<Integer> task : group1.getTasks()) {
            if (task instanceof Signature) {
                System.out.println("Signature groupId: " + ((Signature<Integer>) task).getHeader("groupId"));
            }
        }
    }
}
