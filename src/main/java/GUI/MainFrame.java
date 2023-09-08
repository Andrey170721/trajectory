package GUI;

import org.jfree.chart.ChartPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;



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
    private JCheckBox XCheckBox;
    private JCheckBox YCheckBox;
    private JCheckBox ZCheckBox;
    private JCheckBox VzCheckBox;
    private JCheckBox VyCheckBox;
    private JCheckBox VxCheckBox6;
    private JSplitPane graphPanel;
    Graph graph = new Graph(XCheckBox, YCheckBox, ZCheckBox, VxCheckBox6, VyCheckBox, VzCheckBox);
    JMenu recentFilesMenu = new JMenu("Открыть недавние");
    List<String> savedProperties = getProperties();
    DefaultListModel<String> listModel = new DefaultListModel<>();
    private final ButtonsService buttonsService = new ButtonsService(graph);


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

    public void saveRecentFiles() throws IOException {
        ListModel<String> lModel = catalog.getModel();
        Properties recentFiles = new Properties();
        try {
            recentFiles.load(new FileInputStream("recentFiles.properties"));
        }catch (IOException e){
            System.out.println("File recentFiles.properties not found");
        }
        List<String> keys = getPropertyKeys(recentFiles);

        for (int i = 0; i < lModel.getSize(); i++) {
            if(!keys.contains(lModel.getElementAt(i))){
                recentFiles.setProperty(buttonsService.getSource(lModel.getElementAt(i)), buttonsService.getSource(lModel.getElementAt(i)));
            }
        }

        recentFiles.store(new FileOutputStream("recentFiles.properties"), null);
    }

    private List<String> getPropertyKeys(Properties property){
        Enumeration<?> eKeys = property.propertyNames();
        List<String> keys = new ArrayList<>();
        while (eKeys.hasMoreElements()) {
            keys.add((String) eKeys.nextElement());
        }
        return keys;
    }

    private void createUIComponents() throws IOException {
        DefaultTableModel tableModel = new DefaultTableModel();
        catalog.setModel(listModel);
        JPopupMenu filePopupMenu = new JPopupMenu();
        JPopupMenu settingsPopupMenu = new JPopupMenu();

        JMenuItem open = new JMenuItem("Открыть");
        JMenuItem close = new JMenuItem("Закрыть");
        JMenuItem closeAll = new JMenuItem("Закрыть все");
        JMenuItem saveWindow = new JMenuItem("Сохранить положение окон");

        addRecentOpenedFile();

        filePopupMenu.add(open);
        filePopupMenu.add(recentFilesMenu);
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

        file.setEditable(false);

        catalogScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        fileScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tableScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        ChartPanel chartPanel = graph.getChartPanel();
        graphPanel.setRightComponent(chartPanel);

        open.addActionListener(e -> {
            try {
                buttonsService.openClick(table, catalog, file, fileNameLabel);
                saveRecentFiles();
                addRecentOpenedFile();
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

        close.addActionListener(e -> {
            DefaultListModel<String> model = buttonsService.getListModel();
            buttonsService.deleteTrajectoryFromList(catalog.getSelectedValue());
            model.removeElement(catalog.getSelectedValue());
            catalog.clearSelection();
            catalog.updateUI();
            file.setText("");
            fileNameLabel.setText("Файл не выбран");
            table.setModel(tableModel);
        });

        saveWindow.addActionListener(e -> {
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
        });
    }

    public void addRecentOpenedFile(){

        Properties recentFiles = new Properties();
        try {
            recentFiles.load(new FileInputStream("recentFiles.properties"));
            List<String> keys = getPropertyKeys(recentFiles);
            recentFilesMenu.removeAll();
            for(String item : keys){
                JMenuItem recentButton = new JMenuItem(recentFiles.getProperty(item));
                recentFilesMenu.add(recentButton);
                recentButton.addActionListener(e -> {
                    try {
                        JMenuItem menuItem = (JMenuItem) e.getSource();
                        Boolean isFileExist = buttonsService.openRecentFile(table, catalog, file, fileNameLabel, menuItem.getText());
                        if(!isFileExist){
                            recentFilesMenu.remove(menuItem);
                            Properties recFiles = new Properties();
                            try {
                                recFiles.load(new FileInputStream("recentFiles.properties"));
                            }catch (FileNotFoundException ex){
                                ex.printStackTrace();
                            }
                            Set<Object> keySet = recFiles.keySet();
                            for (Object key : keySet) {
                                Object value = recFiles.get(key);
                                if (value != null && value.toString().equals(menuItem.getText())) {
                                    recFiles.remove(key);
                                    break;
                                }
                            }
                            recFiles.remove(menuItem.getText());

                            recFiles.store(new FileOutputStream("recentFiles.properties"), null);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }catch (IOException e){
            System.out.println("File recentFiles.properties not found");
        }
    }

}
