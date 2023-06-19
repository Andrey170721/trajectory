package fileManager;

import trajectory.Trajectory;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {

    public Trajectory openFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        Trajectory trajectory = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
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

    public void renameFile(){
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String newName = JOptionPane.showInputDialog("Введите новое имя файла:");
            File newFile = new File(selectedFile.getParent(), newName + ".txt");

            if (selectedFile.renameTo(newFile)) {
                JOptionPane.showMessageDialog(null, "Файл успешно переименован!");
            } else {
                JOptionPane.showMessageDialog(null, "Не удалось переименовать файл!");
            }
        }
    }

    public void editFile(Trajectory trajectory){

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

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

                for(int i = 0; i < trajectory.getLinesCount(); i++){
                    List<Double> coordinates = trajectory.getCoordinates(i);
                    List<Double> speed = trajectory.getSpeed(i);
                    writer.write(trajectory.getTime(i) + "   " + coordinates.get(0) + "   " + coordinates.get(1) +
                            "   " + coordinates.get(2) + "   " + speed.get(0) + "   " + speed.get(1) + "   " +
                            speed.get(2) + "\n");
                }

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
