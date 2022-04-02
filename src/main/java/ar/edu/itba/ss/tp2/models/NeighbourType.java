package ar.edu.itba.ss.tp2.models;

public enum NeighbourType {
    VON_NEUMANN(0),
    MOORE(1);

    public final int type;

    NeighbourType(int type) {
        this.type = type;
    }
}
