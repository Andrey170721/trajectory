package trajectory;

import java.util.*;

public class Trajectory {
    private final SortedMap<Double, Point> points = new TreeMap<>();
    private final Source source;

    public Trajectory(List<String> lines, String source){
        for (String str : lines) {
            String[] data = str.split("\\s+");
                Point point = new Point(Double.parseDouble(data[1]),
                        Double.parseDouble(data[2]), Double.parseDouble(data[3]), Double.parseDouble(data[4]),
                        Double.parseDouble(data[5]), Double.parseDouble(data[6]));
                Double time = Double.parseDouble(data[0]);
                points.put(time, point);
        }
        this.source = new Source(source);
    }

    public SortedMap<Double, Point> getPoints(){
        return points;
    }

    public List<Double> getTimes(){
        return new ArrayList<>(points.keySet());
    }

    public String getFileName(){
        return source.getFileName();
    }

    public String getPath(){
        return source.getPath();
    }

    public int getLinesCount(){
        return points.size();
    }


}
