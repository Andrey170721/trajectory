package fileManager;

import trajectory.Point;
import trajectory.Trajectory;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

public class FileService {

    public static Trajectory openFile() throws IOException {
        File selectedFile = selectFile();
        Trajectory trajectory = null;
        if (selectedFile != null) {
            FileReader reader = new FileReader(selectedFile);

            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            List<String> lines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            trajectory = new Trajectory(lines, selectedFile.getAbsolutePath());
            bufferedReader.close();
            reader.close();

        }
        return trajectory;
    }

    public static void renameFile(){
        File selectedFile = selectFile();

        if (selectedFile != null) {
            String newName = JOptionPane.showInputDialog("Введите новое имя файла:");
            File newFile = new File(selectedFile.getParent(), newName + ".txt");

            if (selectedFile.renameTo(newFile)) {
                JOptionPane.showMessageDialog(null, "Файл успешно переименован!");
            } else {
                JOptionPane.showMessageDialog(null, "Не удалось переименовать файл!");
            }
        }
    }

    public static void editFile(Trajectory trajectory){
        File selectedFile = selectFile();

        if (selectedFile != null) {
            if (selectedFile.exists()) {
                int option = JOptionPane.showConfirmDialog(null,
                        "Файл уже существует. Вы хотите его перезаписать?",
                        "Предупреждение", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.NO_OPTION) {
                    return;
                }
            }
            try {
                FileWriter writer = new FileWriter(selectedFile);
                List<Double> times = trajectory.getTimes();
                SortedMap<Double, Point> points = trajectory.getPoints();

                for(Double time : times){
                    Point point = points.get(time);
                    writer.write(time + "   " + point.getX() + "   " + point.getY() +
                            "   " + point.getZ() + "   " + point.getVx() + "   " + point.getVy() + "   " +
                            point.getVz() + "\n");
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }
}
