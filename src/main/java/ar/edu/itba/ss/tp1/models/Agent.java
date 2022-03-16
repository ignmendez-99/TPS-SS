package ar.edu.itba.ss.tp1.models;

import java.math.BigDecimal;
import java.util.UUID;

public class Agent {

    // Global Variables
    private static Integer counterId = 0;
    private String id;
    // Variables
    private BigDecimal x;
    private BigDecimal y;
    private Double vx;
    private Double vy;
    private Double radius;
    private Double param;

    // Constructor
    public Agent(BigDecimal x, BigDecimal y, Double vx, Double vy, Double radius) {
        counterId++;
        this.id = counterId.toString();
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.param = 0.0;
    }

    // Methods

    public String getId() {
        return id;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public Double getVx() {
        return vx;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public Double getVy() {
        return vy;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public Double getRadius() {
        return radius;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    //To String
    @Override
    public String toString() {
        return "Agent{" +
                "x=" + x +
                ", y=" + y +
                ", vx=" + vx +
                ", vy=" + vy +
                ", radius=" + radius +
                '}';
    }
}
