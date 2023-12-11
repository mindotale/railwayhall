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

    private static RailwayHallConfig createRailwayHallConfig() {
        var ticketBoxConfigs = createTicketBoxConfigs();
        var reservedTicketBoxConfig = createReservedTicketBoxConfig();
        var entranceConfigs = createEntranceConfigs();
        var clientCapacity = 50;
        var restartClientCapacity = 40;
        return new RailwayHallConfig(ticketBoxConfigs, reservedTicketBoxConfig, entranceConfigs, clientCapacity, restartClientCapacity);
    }

    private static List<TicketBoxConfig> createTicketBoxConfigs() {
        var strategy = new RandomTicketTimeProcessingStrategy(1,3);
        var configs = new ArrayList<TicketBoxConfig>();
        configs.add(new TicketBoxConfig(1,new Vector(10, 50), strategy));
        return configs;
    }

    private static TicketBoxConfig createReservedTicketBoxConfig() {
        var id = 0;
        var position = new Vector(10,10);
        var strategy = new RandomTicketTimeProcessingStrategy(1,3);
        return new TicketBoxConfig(id, position, strategy);
    }

    private static List<EntranceConfig> createEntranceConfigs() {
        var idGenerator = new IntegerIdGenerator();
        var statuses = new ArrayList<ClientStatus>();
        statuses.add(new ClientStatus("Status 1", 1));
        var strategy = new RandomClientGenerationStrategy(idGenerator, 1, 3, new Vector(40, 10), new Vector(40, 10), 10, 30, 1, 3, statuses);
        var configs = new ArrayList<EntranceConfig>();
        configs.add(new EntranceConfig(0, new Vector(40, 40), strategy));
        return configs;
    }
}