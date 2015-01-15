import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotificationDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel notificationLabel;

    public NotificationDialog() {
        setContentPane(contentPane);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        setSize(250, 100);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width - getSize().width / 2, dim.height - getSize().height / 2);

        setUndecorated(true);
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public void setNotificationText(String text) {
        this.notificationLabel.setText(text);
    }
}
