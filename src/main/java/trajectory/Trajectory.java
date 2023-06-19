package trajectory;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {
    public Trajectory(List<String> lines, String source){
        for (String str : lines) {
            String[] data = str.split("\\s+");
            for (int i = 0; i < data.length; i += 7) {
                Double newTime = Double.parseDouble(data[i]);
                Coordinates newCoordinates = new Coordinates(Double.parseDouble(data[i + 1]),
                        Double.parseDouble(data[i + 2]), Double.parseDouble(data[i + 3]));
                Speed newSpeed = new Speed(Double.parseDouble(data[i + 4]),
                        Double.parseDouble(data[i + 5]), Double.parseDouble(data[i + 6]));
                times.add(newTime);
                coordinates.add(newCoordinates);
                speeds.add(newSpeed);
            }
        }
        this.source = new Source(source);
    }
    private final List<Double> times = new ArrayList<>();
    private final List<Coordinates> coordinates = new ArrayList<>();
    private final List<Speed> speeds = new ArrayList<>();
    private final Source source;

    public Double getTime(int i){

        return times.get(i);
    }

    public List<Double> getCoordinates(int i){
        Coordinates getCoordinates = coordinates.get(i);
        List<Double> data = new ArrayList<>();
        data.add(getCoordinates.X);
        data.add(getCoordinates.Y);
        data.add(getCoordinates.Z);
        return data;
    }

    public List<Double> getSpeed(int i){
        Speed getSpeed = speeds.get(i);
        List<Double> data = new ArrayList<>();
        data.add(getSpeed.Vx);
        data.add(getSpeed.Vy);
        data.add(getSpeed.Vz);
        return data;
    }

    public String getFileName(){
        return source.fileName;
    }

    public String getPath(){
        return source.path;
    }

    public int getLinesCount(){
        return coordinates.size();
    }


}
