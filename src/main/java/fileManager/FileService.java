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
            trajectory = readFile(selectedFile);
        }
        return trajectory;
    }


    public static String openFile(String source){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(source));
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileChooser.getSelectedFile()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator());
            }
            reader.close();

            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


        public static Trajectory openRecentFile(String source) throws IOException {
            File file = new File(source);
            return readFile(file);
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


    private static File selectFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }


    private static Trajectory readFile(File file) throws IOException {
        Trajectory trajectory;
        try {
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            List<String> lines = new ArrayList<>();
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            trajectory = new Trajectory(lines, file.getAbsolutePath());
            bufferedReader.close();
            reader.close();
            return trajectory;
        }catch (FileNotFoundException e){
            JOptionPane.showMessageDialog(null, "Файл не существует. Возможно директория была изменена или файл был удален");
        }
        return null;
    }
}
