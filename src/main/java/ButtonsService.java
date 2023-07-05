import fileManager.FileService;
import trajectory.Point;
import trajectory.Trajectory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.*;

public class ButtonsService {
    List<Trajectory> trajectories = new ArrayList<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    public void openClick( JTable table, JList<String> catalog, JTextArea file, JLabel fileNameLabel) throws IOException {
        Trajectory currentTrajectory = FileService.openFile();
        trajectories.add(currentTrajectory);
        listModel.addElement(currentTrajectory.getFileName());
        catalog.setModel(listModel);
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


}
