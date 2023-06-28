
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class TestFrame extends JComponent {
    private JLabel label;
    private JButton button;
    private Point mouseOffset;

    public TestFrame() {
        label = new JLabel("Drag me!");
        label.setBounds(10, 10, 100, 30);
        add(label);

        button = new JButton("Resize me!");
        button.setBounds(50, 50, 100, 30);
        button.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseOffset = e.getPoint();
            }
        });
        button.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - mouseOffset.x;
                int dy = e.getY() - mouseOffset.y;
                int newWidth = button.getWidth() + dx;
                int newHeight = button.getHeight() + dy;
                button.setSize(newWidth, newHeight);
                repaint();
            }
        });
        add(button);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Draggable Component");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 400));
        layeredPane.setBackground(Color.WHITE);

        TestFrame component = new TestFrame();
        component.setBounds(50, 50, 200, 200);
        layeredPane.add(component, JLayeredPane.DEFAULT_LAYER);

        frame.setContentPane(layeredPane);
        frame.setVisible(true);
    }
}
