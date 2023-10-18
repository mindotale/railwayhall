import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class ClientQueue {
    private final Queue<Client> queue = new PriorityQueue<>((c1, c2) -> c2.getPriority() - c1.getPriority());
    private final Set<Integer> clientIds = new HashSet<>();

    public int getSize() {
        return queue.size();
    }

    public void enqueue(Client client) {
        if (client != null && !clientIds.contains(client.getId())) {
            queue.add(client);
            clientIds.add(client.getId());
        }
    }

    public void enqueueAll(Client[] clients) {
        for (Client client : clients) {
            enqueue(client);
        }
    }

    public Client dequeue() {
        Client client = queue.poll();
        if (client != null) {
            clientIds.remove(client.getId());
        }
        return client;
    }

    public Client[] dequeueAll() {
        int size = queue.size();
        Client[] clients = new Client[size];
        queue.toArray(clients);
        queue.clear();
        clientIds.clear();
        return clients;
    }

    public Client peek() {
        return queue.peek();
    }
}
