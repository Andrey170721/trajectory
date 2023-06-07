package trajectory;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    public Trajectory(Double time, Double X, Double Y, Double Z, Double Vx, Double Vy, Double Vz, String source){
        this.time = new Time(time);
        this.coordinates = new Coordinates(X, Y, Z);
        this.speed = new Speed(Vx, Vy, Vz);
        this.source = new Source(source);
    }
    private final Time time;
    private final Coordinates coordinates;
    private final Speed speed;
    private final Source source;

    public Double getTime(){
        return time.time();
    }

    public List<Double> getCoordinates(){
        List<Double> data= new ArrayList<>();
        data.add(coordinates.X);
        data.add(coordinates.Y);
        data.add(coordinates.Z);
        return data;
    }

    public List<Double> getSpeed(){
        List<Double> data= new ArrayList<>();
        data.add(speed.Vx);
        data.add(speed.Vy);
        data.add(speed.Vz);
        return data;
    }

    public String getFileName(){
        return source.fileName;
    }

    public String getPath(){
        return source.path;
    }
}
