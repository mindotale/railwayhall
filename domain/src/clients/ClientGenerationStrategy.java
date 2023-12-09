package clients;

import common.Ticker;

import java.util.List;

public interface ClientGenerationStrategy extends Ticker {
    List<Client> popNext();
}
