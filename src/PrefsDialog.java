import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PrefsDialog extends JDialog {
    private final PomodoroTimer timer;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField workTimeField;
    private JTextField restTimeField;

    public PrefsDialog(PomodoroTimer timer) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
        pack();
        setTitle("Preferences");

        this.timer = timer;
        workTimeField.setText(timer.getWorkTime() + "");
        restTimeField.setText(timer.getRestTime() + "");
    }

    private void onOK() {
        timer.setWorkTime(Integer.parseInt(workTimeField.getText()));
        timer.setRestTime(Integer.parseInt(restTimeField.getText()));
        dispose();
    }

    private void onCancel() {
        dispose();
    }

}
