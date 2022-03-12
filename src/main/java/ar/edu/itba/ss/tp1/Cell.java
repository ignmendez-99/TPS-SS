package ar.edu.itba.ss.tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cell {

    private Integer agentQty;
    private List<Agent> agents;


    public Cell() {
        this.agentQty = 0;
        this.agents = new ArrayList<>();
    }

    // Methods
    public List<Agent> getAgents() {
        return agents;
    }

    public void addAgents (Agent[] a) {
        this.agents.addAll(Arrays.asList(a));
        this.agentQty = a.length;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "agentQty=" + agentQty +
                ", agents=" + agents +
                '}';
    }
}
