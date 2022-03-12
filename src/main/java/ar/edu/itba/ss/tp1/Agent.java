package ar.edu.itba.ss.tp1;

public class Agent {
    // Variables
    private Double x;
    private Double y;
    private Double vx;
    private Double vy;
    private Double radius;

    // Constructor
    public Agent(Double x, Double y, Double vx, Double vy, Double radius) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
    }

    // Methods
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
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
