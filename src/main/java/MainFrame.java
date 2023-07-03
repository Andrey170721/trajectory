import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MainFrame extends JFrame{
    private JPanel panelMain;
    private JButton settingsButton;
    private JButton fileButton;
    private JList<String> catalog;
    private JTextArea file;
    private JTable table;
    private JTextArea schedule;
    private JScrollPane catalogScroller;
    private JScrollPane fileScroller;
    private JScrollPane tableScroller;
    private JSplitPane maneSplit;
    private JSplitPane rightSplit;
    private JSplitPane leftSplit;
    private JLabel catalogLabel;
    private JLabel fileLabel;
    private JLabel fileNameLabel;
    private JLabel tableLabel;
    private JLabel scheduleLabel;
    private final ButtonsListener buttonsListener = new ButtonsListener();


    public MainFrame() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        setContentPane(panelMain);
        javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setTitle("Trajectory");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        List<Integer> savedDividersLocations = getProperties();
        setBounds(savedDividersLocations.get(5), savedDividersLocations.get(6), savedDividersLocations.get(4), savedDividersLocations.get(3));
        createUIComponents();
        leftSplit.setDividerLocation(savedDividersLocations.get(0));
        rightSplit.setDividerLocation(savedDividersLocations.get(1));
        maneSplit.setDividerLocation(savedDividersLocations.get(2));
        setResizable(true);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // сохраняем текущее положение Divider
                List<Integer> properties = new ArrayList<>();

                properties.add(leftSplit.getDividerLocation());
                properties.add(rightSplit.getDividerLocation());
                properties.add(maneSplit.getDividerLocation());
                properties.add(getHeight());
                properties.add(getWidth());
                properties.add(getX());
                properties.add(getY());

                try {
                    saveProperties(properties);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private List<Integer> getProperties() throws IOException {
        Properties props = new Properties();
        List<Integer> properties = new ArrayList<>();
        try {
            props.load(new FileInputStream("config.properties"));
            properties.add(Integer.parseInt(props.getProperty("leftSplit")));
            properties.add(Integer.parseInt(props.getProperty("rightSplit")));
            properties.add(Integer.parseInt(props.getProperty("maneSplit")));
            properties.add(Integer.parseInt(props.getProperty("height")));
            properties.add(Integer.parseInt(props.getProperty("width")));
            properties.add(Integer.parseInt(props.getProperty("X")));
            properties.add(Integer.parseInt(props.getProperty("Y")));

        }catch (FileNotFoundException e){
            properties.add(150);
            properties.add(150);
            properties.add(160);
            properties.add(800);
            properties.add(1500);
            properties.add(500);
            properties.add(300);
        }

        return properties;
    }

    private void saveProperties(List<Integer> properties) throws IOException {
        Properties props = new Properties();
        props.setProperty("leftSplit", properties.get(0).toString());
        props.setProperty("rightSplit", properties.get(1).toString());
        props.setProperty("maneSplit", properties.get(2).toString());
        props.setProperty("height",properties.get(3).toString());
        props.setProperty("width",properties.get(4).toString());
        props.setProperty("X",properties.get(5).toString());
        props.setProperty("Y",properties.get(6).toString());
        props.store(new FileOutputStream("config.properties"), null);

    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel();
        DefaultListModel<String> listModel = new DefaultListModel<>();
        catalog.setModel(listModel);
        JPopupMenu filePopupMenu = new JPopupMenu();
        JPopupMenu settingsPopupMenu = new JPopupMenu();
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem openRecent = new JMenuItem("Открыть недавние>");
        JMenuItem close = new JMenuItem("Закрыть>");
        JMenuItem closeAll = new JMenuItem("Закрыть все");
        JMenuItem saveWindow = new JMenuItem("Сохранить положение окон");

        filePopupMenu.add(open);
        filePopupMenu.add(openRecent);
        filePopupMenu.add(close);
        filePopupMenu.add(closeAll);
        settingsPopupMenu.add(saveWindow);

        fileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                filePopupMenu.show(fileButton, 0, fileButton.getHeight());
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                settingsPopupMenu.show(settingsButton, 0, settingsButton.getHeight());
            }
        });


        model.addColumn("Т, с");
        model.addColumn("X, м");
        model.addColumn("Y, м");
        model.addColumn("Z, м");
        model.addColumn("Vx, м/с");
        model.addColumn("Vy, м/с");
        model.addColumn("Vz, м/с");
        table.setModel(model);

        schedule.setEditable(false);
        file.setEditable(false);

        catalogScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fileScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);




        open.addActionListener(e -> {
            try {
                buttonsListener.openClick(model, table, listModel, catalog);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

    }

}