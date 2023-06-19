import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.catalog.Catalog;

public class GUI extends JFrame{

    private JTable trajectoryTable;
    private JTree catalog;
    private JLabel fileLabel;
    private JLabel fileNameLabel;
    private JLabel labelTable;

    public GUI(){
        super("Trajectory");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 1000, 500);
        this.createUIComponents();
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("Т, с");
        model.addColumn("X, м");
        model.addColumn("Y, м");
        model.addColumn("Z, м");
        model.addColumn("Vx, м/с");
        model.addColumn("Vy, м/с");
        model.addColumn("Vz, м/с");
        model.addRow(new Object[]{"2", "1241", "1231", "15345", "1213,", "112412", "112312"});
        trajectoryTable.setModel(model);

        Container container = this.getContentPane();

        container.add(new JScrollPane(trajectoryTable));
    }
}
