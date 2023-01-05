import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * DashboardFrame class
 *
 * This class is used to display and sort the user dashboards.
 *
 * Purdue University -- CS18000 -- Fall 2022 -- Project 05
 *
 * @author Aaron Shih
 * @version December 12, 2022
 */
public class DashboardFrame extends JFrame implements ActionListener {
    Container container;
    JFrame frame;
    PrintWriter writer;
    JPanel panel = new JPanel();
    BufferedReader reader;
    boolean read;
    JButton sortButton = new JButton("Sort Dashboard");
    JButton exitButton = new JButton("Exit");
    DefaultListModel model = new DefaultListModel();
    JList<String> dashboard = new JList<String>(model);
    JScrollPane scrollPane = new JScrollPane(dashboard);
    int len = 0;

    public void setLayout() {
        container.setLayout(new BorderLayout());
    }
    public void addComponents() {
        panel.add(sortButton);
        panel.add(exitButton);
        container.add(panel, BorderLayout.NORTH);
        container.add(scrollPane, BorderLayout.CENTER);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                writer.write(2);
                writer.println();
                writer.flush();
                try {
                    reader.readLine();
                } catch (Exception exception) {

                }
                reopenPrev();
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == sortButton) {
                if (len > 0) {
                    writer.write("go");
                    writer.println();
                    writer.flush();
                }
                writer.write("1");
                writer.println();
                writer.flush();
                model.removeAllElements();
                String s = reader.readLine();
                try {
                    len = Integer.parseInt(s);
                } catch (NumberFormatException exception) {
                    len = 0;
                }
                for (int i = 0; i < len; i++) {
                    try {
                        model.addElement(reader.readLine());
                    } catch (Exception exception) {

                    }
                    if (read) {
                        try {
                            model.addElement(reader.readLine());
                        } catch (Exception exception) {

                        }
                    }
                }
            }
            if (e.getSource() == exitButton) {
                writer.write("2");
                writer.println();
                writer.flush();
                this.setVisible(false);
                reopenPrev();
            }

        } catch (Exception exception) {
        }

    }
    public DashboardFrame() {
        container = getContentPane();
        setLayout();
        addComponents();
    }
    public void activateDash () throws FileNotFoundException, IOException {

        String s = reader.readLine();
        try {
            len = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            len = 0;
        }
        for (int i = 0; i < len; i++) {
            try {
                model.addElement(reader.readLine());
            } catch (Exception exception) {

            }
            if (read) {
                try {
                    model.addElement(reader.readLine());
                } catch (Exception exception) {

                }
            }
        }
        if (len > 0) {

        } else {
            JOptionPane.showMessageDialog(null,
                    "Dashboard is empty! Have conversations to add to it!", "Marketplace",
                    JOptionPane.ERROR_MESSAGE);
            writer.write("stop");
            writer.println();
            writer.flush();
            this.setVisible(false);
            reopenPrev();
        }
        sortButton.addActionListener(this);
        exitButton.addActionListener(this);
    }
    public void setFrameOnClose(JFrame frame) {
        this.frame = frame;
    }
    public void reopenPrev() {
        this.frame.setVisible(true);
    }
    public void setSocketReader(BufferedReader reader1)
    {
        reader = reader1;
    }

    public void setSocketWriter(PrintWriter writer1)
    {
        writer = writer1;
    }
    public void isReader(boolean read) {
        this.read = read;
    }
}
