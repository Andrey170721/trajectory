import trajectory.Trajectory;

public class Main {

    public static void main(String[] args){
        Trajectory trajectory = new Trajectory(4.0, 1976091.0,  3974537.2,4564998.2,1.671 ,4.251,4.931, "/Users/andrejpodvysockij/Desktop/traject1.txt");
        System.out.println("Время: " + trajectory.getTime());
        System.out.println("Координаты: " + trajectory.getCoordinates());
        System.out.println("Скорость: " + trajectory.getSpeed());
        System.out.println("Источник: " + trajectory.getPath() + ", " + trajectory.getFileName());

    }
}
