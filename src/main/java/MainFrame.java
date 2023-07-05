import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    List<String> savedProperties = getProperties();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    private final ButtonsService buttonsService = new ButtonsService();


    public MainFrame() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, IOException {
        setContentPane(panelMain);
        javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        setTitle("Trajectory");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(Integer.parseInt(savedProperties.get(5)), Integer.parseInt(savedProperties.get(6)),
                Integer.parseInt(savedProperties.get(4)), Integer.parseInt(savedProperties.get(3)));
        createUIComponents();
        leftSplit.setDividerLocation(Integer.parseInt(savedProperties.get(0)));
        rightSplit.setDividerLocation(Integer.parseInt(savedProperties.get(1)));
        maneSplit.setDividerLocation(Integer.parseInt(savedProperties.get(2)));
        setResizable(true);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
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

    private List<String> getProperties() throws IOException {
        Properties props = new Properties();
        List<String> properties = new ArrayList<>();
        try {
            props.load(new FileInputStream("config.properties"));
            properties.add(props.getProperty("leftSplit"));
            properties.add(props.getProperty("rightSplit"));
            properties.add(props.getProperty("maneSplit"));
            properties.add(props.getProperty("height"));
            properties.add(props.getProperty("width"));
            properties.add(props.getProperty("X"));
            properties.add(props.getProperty("Y"));


        }catch (FileNotFoundException e){
            properties.add("150");
            properties.add("150");
            properties.add("160");
            properties.add("800");
            properties.add("1500");
            properties.add("500");
            properties.add("300");
        }
        try{
            for(int i = 7; i < props.size(); i++){
                int j = 0;
                properties.add(props.getProperty("recent" + i));
            }
        }catch (NullPointerException e){
            return properties;
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
        for(int i = 0; i < listModel.getSize(); i++){
            props.setProperty("recent" + i, listModel.getElementAt(i));
        }
        props.store(new FileOutputStream("config.properties"), null);

    }

    private void createUIComponents() {
        DefaultTableModel tableModel = new DefaultTableModel();
        catalog.setModel(listModel);
        JPopupMenu filePopupMenu = new JPopupMenu();
        JPopupMenu settingsPopupMenu = new JPopupMenu();
        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem openRecent = new JMenuItem("Открыть недавние");
//        JMenu recentFilesMenu = new JMenu("Открыть недавние");
//        if(savedProperties.size() > 7){
//            openRecent.add(recentFilesMenu);
//            for(int i = 7; i < savedProperties.size(); i++){
//                recentFilesMenu.add(new JMenuItem(savedProperties.get(i)));
//            }
//        }
        JMenuItem close = new JMenuItem("Закрыть");
        JMenuItem closeAll = new JMenuItem("Закрыть все");
        JMenuItem saveWindow = new JMenuItem("Сохранить положение окон");

        filePopupMenu.add(open);
        filePopupMenu.add(openRecent);
        filePopupMenu.add(close);
        filePopupMenu.add(closeAll);
        settingsPopupMenu.add(saveWindow);

        fileButton.addActionListener(e -> filePopupMenu.show(fileButton, 0, fileButton.getHeight()));

        settingsButton.addActionListener(e -> settingsPopupMenu.show(settingsButton, 0, settingsButton.getHeight()));


        tableModel.addColumn("Т, с");
        tableModel.addColumn("X, м");
        tableModel.addColumn("Y, м");
        tableModel.addColumn("Z, м");
        tableModel.addColumn("Vx, м/с");
        tableModel.addColumn("Vy, м/с");
        tableModel.addColumn("Vz, м/с");
        table.setModel(tableModel);

        schedule.setEditable(false);
        file.setEditable(false);

        catalogScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fileScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);




        open.addActionListener(e -> {
            try {
                buttonsService.openClick(table, catalog, file, fileNameLabel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        closeAll.addActionListener(e -> {
            buttonsService.clearListModel();
            catalog.setModel(listModel);
            file.setText("");
            fileNameLabel.setText("Файл не выбран");
            table.setModel(tableModel);
        });

    }

}