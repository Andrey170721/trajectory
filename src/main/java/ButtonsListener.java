import fileManager.FileService;
import trajectory.Point;
import trajectory.Trajectory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ButtonsListener {
    Map<String, DefaultTableModel> tableModels = new TreeMap<>();
    Map<String, String> files = new TreeMap<>();
    DefaultListModel<String> listModel = new DefaultListModel<>();

    public void openClick( JTable table, JList<String> catalog, JTextArea file, JLabel fileNameLabel) throws IOException {
        Trajectory trajectory = FileService.openFile();
        SortedMap<Double, Point> points = trajectory.getPoints();
        List<Double> times = trajectory.getTimes();
        listModel.addElement(trajectory.getFileName());
        catalog.setModel(listModel);
        DefaultTableModel newTableModel = new DefaultTableModel();

        newTableModel.addColumn("Т, с");
        newTableModel.addColumn("X, м");
        newTableModel.addColumn("Y, м");
        newTableModel.addColumn("Z, м");
        newTableModel.addColumn("Vx, м/с");
        newTableModel.addColumn("Vy, м/с");
        newTableModel.addColumn("Vz, м/с");


        for(Double time : times){
            Point point = points.get(time);
            Object[] rowData = {time, point.getX(), point.getY(), point.getZ(), point.getVx(), point.getVy(), point.getVz()};
            newTableModel.addRow(rowData);
        }
        tableModels.put(trajectory.getFileName(), newTableModel);
        files.put(trajectory.getFileName(), FileService.openFile(trajectory.getPath() + "/" + trajectory.getFileName()));
        table.setModel(newTableModel);
        file.setText(FileService.openFile(trajectory.getPath() + "/" + trajectory.getFileName()));
        fileNameLabel.setText(trajectory.getFileName());

        catalog.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                String selectedValue = catalog.getSelectedValue();
                DefaultTableModel currentModel = tableModels.get(selectedValue);
                table.setModel(currentModel);
                file.setText(files.get(selectedValue));
                fileNameLabel.setText(selectedValue);
            }
        });
    }

}
