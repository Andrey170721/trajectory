import fileManager.FileService;
import trajectory.Trajectory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //List<String> lines = new ArrayList<>();
        //lines.add("4.0, 1976091.0, 3974537.2, 4564998.2, 1.671, 4.251, 4.931");
        //lines.add("5.0, 2976091.0, 4974537.2, 5564998.2, 2.671, 5.251, 5.931");

        //FileService fileService = new FileService();
        //Trajectory trajectory = fileService.openFile();
        //fileService.renameFile();
        //fileService.editFile(trajectory);

        //Trajectory trajectory = new Trajectory(lines, "/Users/andrejpodvysockij/Desktop/traject1.txt");

        /*for(int i = 0; i < trajectory.getLinesCount(); i++){
            System.out.println("Время: " + trajectory.getTime(i));
            System.out.println("Координаты: " + trajectory.getCoordinates(i));
            System.out.println("Скорость: " + trajectory.getSpeed(i));
        }

        System.out.println("Источник: " + trajectory.getPath() + ", " + trajectory.getFileName());*/

        GUI app = new GUI();
        app.setVisible(true);

    }
}
