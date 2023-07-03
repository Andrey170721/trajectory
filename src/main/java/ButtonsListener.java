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
    public void openClick(DefaultTableModel model, JTable table, DefaultListModel<String> listModel, JList<String> catalog) throws IOException {
        Trajectory trajectory = FileService.openFile();
        SortedMap<Double, Point> points = trajectory.getPoints();
        List<Double> times = trajectory.getTimes();
        listModel.addElement(trajectory.getFileName());
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
        table.setModel(newTableModel);

        catalog.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                String selectedValue = catalog.getSelectedValue();
                DefaultTableModel currentModel = tableModels.get(selectedValue);
                table.setModel(currentModel);
            }
        });
    }
}
