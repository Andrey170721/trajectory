package trajectory;

import java.util.*;

public class Trajectory {
    public Trajectory(List<String> lines, String source){
        for (String str : lines) {
            String[] data = str.split("\\s+");
            for (int i = 0; i < data.length; i += 7) {
                Point point = new Point(Double.parseDouble(data[i + 1]),
                        Double.parseDouble(data[i + 2]), Double.parseDouble(data[i + 3]), Double.parseDouble(data[i + 4]),
                        Double.parseDouble(data[i + 5]), Double.parseDouble(data[i + 6]));
                Double time = Double.parseDouble(data[i]);
                points.put(time, point);
                times.add(time);
            }
            Collections.sort(times);
        }
        this.source = new Source(source);
    }
    private final SortedMap<Double, Point> points = new TreeMap<>();
    private final Source source;
    private final List<Double> times = new ArrayList<>();

    public SortedMap<Double, Point> getPoints(){
        return points;
    }

    public List<Double> getTimes(){
        return times;
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
