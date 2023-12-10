package domain;

import java.util.Vector;
import java.util.*;

public class Client {
    private final int id;
    private final List<ClientStatus> statuses;
    private final int priority;
    private final double velocity;
    private java.util.Vector position;
    private int tickets;

    public Client(int id, java.util.Vector position, double velocity, int tickets, Collection<ClientStatus> statuses) {
        if (id < 0) {
            throw new IllegalArgumentException("Client ID must be non-negative.");
        }
        if(velocity < 0)
        {
            throw new IllegalStateException("Velocity must be non-negative.");
        }
        if (tickets < 0) {
            throw new IllegalArgumentException("Ticket count must be non-negative.");
        }
        if (containsDuplicates(statuses)) {
            throw new IllegalArgumentException("Statuses must not contain duplicates.");
        }
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.tickets = tickets;
        this.statuses = List.copyOf(statuses);
        this.priority = calculatePriority(statuses);
    }

    public int getId() {
        return id;
    }

    public java.util.Vector getPosition() {
        return position;
    }

    public int getTickets() {
        return tickets;
    }

    public List<ClientStatus> getStatuses() {
        return statuses;
    }

    public int getPriority() {
        return priority;
    }

    public void processTicket() {
        if (tickets <= 0) {
            throw new IllegalStateException("No more tickets to process.");
        }
        tickets--;
    }

    public void moveTo(Vector newPosition) {
        Vector direction = newPosition.subtract(position);
        double distanceToMove = velocity;

        if (direction.magnitude() <= distanceToMove) {
            moveToInstantly(newPosition);
        } else {
           Vector newDirection = direction.normalize().scale(distanceToMove);
            position = position.add(newDirection);
        }
    }

    public void moveToInstantly(Vector position) {
        this.position = position;
    }

    private static int calculatePriority(Collection<ClientStatus> statuses) {
        int prioritySum = 0;
        for (ClientStatus status : statuses) {
            prioritySum += status.getPriority();
        }
        return prioritySum;
    }

    private static boolean containsDuplicates(Collection<ClientStatus> statuses) {
        Set<String> statusSet = new HashSet<>();
        for (ClientStatus status : statuses) {
            if (!statusSet.add(status.getName())) {
                return true;
            }
        }
        return false;
    }
}
