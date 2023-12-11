package domain;

import domain.clients.ClientStatus;
import domain.clients.RandomClientGenerationStrategy;
import domain.common.IntegerIdGenerator;
import domain.common.Vector;
import domain.entrances.EntranceConfig;
import domain.railwayhalls.RailwayHall;
import domain.railwayhalls.RailwayHallConfig;
import domain.ticketboxes.RandomTicketTimeProcessingStrategy;
import domain.ticketboxes.TicketBox;
import domain.ticketboxes.TicketBoxConfig;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var railwayHallConfig = createRailwayHallConfig();
        var railwayHall = new RailwayHall(railwayHallConfig);

        while(true){
            railwayHall.tick();
            if(railwayHall.getTicks() % 100 == 0){
                railwayHall.disableTicketBox(1);
            }
            if(railwayHall.getTicks() % 100 == 20){
                railwayHall.enableTicketBox(1);
            }
        }
    }

    public static RailwayHallConfig createRailwayHallConfig() {
        var ticketBoxConfigs = createTicketBoxConfigs();
        var reservedTicketBoxConfig = createReservedTicketBoxConfig();
        var entranceConfigs = createEntranceConfigs();
        var clientCapacity = 5;
        var restartClientCapacity = 4;
        return new RailwayHallConfig(ticketBoxConfigs, reservedTicketBoxConfig, entranceConfigs, clientCapacity, restartClientCapacity);
    }

    private static List<TicketBoxConfig> createTicketBoxConfigs() {
        var strategy = new RandomTicketTimeProcessingStrategy(1,3);
        var configs = new ArrayList<TicketBoxConfig>();
        configs.add(new TicketBoxConfig(1,new Vector(100, 50), strategy));
        configs.add(new TicketBoxConfig(2,new Vector(200, 100), strategy));
        configs.add(new TicketBoxConfig(3,new Vector(300, 70), strategy));
        return configs;
    }

    private static TicketBoxConfig createReservedTicketBoxConfig() {
        var id = 0;
        var position = new Vector(50,50);
        var strategy = new RandomTicketTimeProcessingStrategy(1,3);
        return new TicketBoxConfig(id, position, strategy);
    }

    private static List<EntranceConfig> createEntranceConfigs() {
        var idGenerator = new IntegerIdGenerator();
        var statuses = new ArrayList<ClientStatus>();
        statuses.add(new ClientStatus("Status 1", 1));
        statuses.add(new ClientStatus("Status 2", 2));
        statuses.add(new ClientStatus("Status 3", 3));
        var configs = new ArrayList<EntranceConfig>();

        configs.add(new EntranceConfig(0, new Vector(400, 50),
                new RandomClientGenerationStrategy(idGenerator, 1, 3, new Vector(40, 10), new Vector(40, 10), 10, 30, 1, 3, statuses)));

        configs.add(new EntranceConfig(1, new Vector(50, 350),
                new RandomClientGenerationStrategy(idGenerator, 1, 4, new Vector(50, 20), new Vector(50, 20), 20, 40, 2, 4, statuses)));
        return configs;
    }
}