package GUI;

import fileManager.FileService;
import trajectory.Point;
import trajectory.Trajectory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.*;

public class ButtonsService {
    private final List<Trajectory> trajectories = new ArrayList<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private Integer count = 1;
    public void openClick( JTable table, JList<String> catalog, JTextArea file, JLabel fileNameLabel) throws IOException {
        Trajectory currentTrajectory = FileService.openFile();
        addFile(table, catalog, file, fileNameLabel, currentTrajectory);
    }

    public void openRecentFile(JTable table, JList<String> catalog, JTextArea file, JLabel fileNameLabel, String source) throws IOException {
        Trajectory currentTrajectory = FileService.openRecentFile(source);
        addFile(table, catalog, file, fileNameLabel, currentTrajectory);
    }

    private void addFile(JTable table, JList<String> catalog, JTextArea file, JLabel fileNameLabel, Trajectory currentTrajectory) throws IOException {
        for (Trajectory item : trajectories) {
            if(Objects.equals(item.getSource(), currentTrajectory.getSource())){
                Trajectory tableTrajectory = getTrajectoryFromTable((DefaultTableModel) table.getModel(), item.getSource(), item.getName());
                if(compareTrajectories(tableTrajectory, item)){
                    JOptionPane.showMessageDialog(null, "Эта траектория уже добавлена!");
                    return;
                }else{
                    int result = JOptionPane.showConfirmDialog(null,
                            "Эта раектория была изменена. При открытии вы потеряете все изменения. Хотите продолжить?",
                            "Подтверждение", JOptionPane.YES_NO_OPTION);
                    switch (result){
                        case JOptionPane.YES_OPTION:
                            DefaultTableModel newTableModel = createTableModel(currentTrajectory);
                            table.setModel(newTableModel);
                            return;
                        case JOptionPane.NO_OPTION:
                        case JOptionPane.CLOSED_OPTION:
                            return;
                    }
                }
            }
        }

        String newName = JOptionPane.showInputDialog("Введите имя траектории:", "Траектория " + count);
        currentTrajectory.rename(newName);
        count++;
        trajectories.add(currentTrajectory);
        listModel.addElement(currentTrajectory.getName());
        catalog.setModel(listModel);
        catalog.setSelectedValue(listModel.getElementAt(listModel.getSize() - 1), true);
        DefaultTableModel newTableModel = createTableModel(currentTrajectory);
        table.setModel(newTableModel);
        file.setText(FileService.openFile(currentTrajectory.getPath() + "/" + currentTrajectory.getFileName()));
        fileNameLabel.setText(currentTrajectory.getFileName());

        catalog.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                String selectedValue = catalog.getSelectedValue();
                Trajectory trajectory = null;
                for (Trajectory item: trajectories) {
                    if(Objects.equals(item.getName(), selectedValue)) {
                        trajectory = item;
                    }
                }
                assert trajectory != null;
                DefaultTableModel currentModel = createTableModel(trajectory);
                table.setModel(currentModel);
                file.setText(FileService.openFile(trajectory.getSource()));
                fileNameLabel.setText(trajectory.getSource());
            }
        });
    }

    public void clearListModel(){
        listModel.clear();
        trajectories.clear();
    }

    public DefaultTableModel createTableModel(Trajectory trajectory){
        DefaultTableModel newTableModel = new DefaultTableModel();
        newTableModel.addColumn("Т, с");
        newTableModel.addColumn("X, м");
        newTableModel.addColumn("Y, м");
        newTableModel.addColumn("Z, м");
        newTableModel.addColumn("Vx, м/с");
        newTableModel.addColumn("Vy, м/с");
        newTableModel.addColumn("Vz, м/с");
        SortedMap<Double, Point> points = trajectory.getPoints();
        List<Double> times = trajectory.getTimes();

        for(Double time : times){
            Point point = points.get(time);
            Object[] rowData = {time, point.getX(), point.getY(), point.getZ(), point.getVx(), point.getVy(), point.getVz()};
            newTableModel.addRow(rowData);
        }
        return newTableModel;
    }

    public Trajectory getTrajectoryFromTable(DefaultTableModel tableModel, String name, String source){
        List<String> lines = new ArrayList<>();
        for(int i = 0; i < tableModel.getRowCount(); i++){
            StringBuilder lineBuilder = new StringBuilder();
            for(int j = 0; j < 7; j++){
                lineBuilder.append(tableModel.getValueAt(i, j));
                lineBuilder.append("   ");
            }
            String line = lineBuilder.toString();
            lines.add(line);
        }
        Trajectory trajectory= new Trajectory(lines, source);
        trajectory.rename(name);
        return trajectory;
    }

    public String getSource(String name) {
        for(Trajectory item : trajectories){
            if(Objects.equals(item.getName(), name)){
                return item.getSource();
            }
        }
        return null;
    }

    public DefaultListModel<String> getListModel(){
        return listModel;
    }

    public Boolean compareTrajectories(Trajectory trajectory1, Trajectory trajectory2){
        SortedMap<Double, Point> points1 = trajectory1.getPoints();
        SortedMap<Double, Point> points2 = trajectory2.getPoints();
        List<Double> times1 = trajectory1.getTimes();
        List<Double> times2 = trajectory2.getTimes();
        if(times1.equals(times2)){
            for (Double time: times1) {
                Point point1 = points1.get(time);
                Point point2 = points2.get(time);

                if(!(Objects.equals(point1.getX(), point2.getX()) && Objects.equals(point1.getY(), point2.getY())
                        && Objects.equals(point1.getZ(), point2.getZ()) && Objects.equals(point1.getVx(), point2.getVx())
                        && Objects.equals(point1.getVy(), point2.getVy()) && Objects.equals(point1.getVz(), point2.getVz()))){
                    return false;
                }
            }
        }else return false;
        return true;
    }
}

