import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Pomodoro technique tray application
 *
 * @author timur560
 */
public class PomodoroTray {

    public static void main(String[] args) {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "SystemTray is not supported", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        final PopupMenu popup = new PopupMenu();

        try {
            final TrayIcon trayIcon = new TrayIcon(ImageIO.read(PomodoroTray.class.getResource("images/circle_yellow.png")));

            final SystemTray tray = SystemTray.getSystemTray();

            final PomodoroTimer timer = new PomodoroTimer(trayIcon);

            // Create a pop-up menu items
            final MenuItem aboutItem = new MenuItem("About");
            final MenuItem toggleItem = new MenuItem("Start");
            final MenuItem prefsItem = new MenuItem("Preferences...");
            final MenuItem quitItem = new MenuItem("Quit");

            quitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (JOptionPane.showConfirmDialog(null, "Are you sure?", "Quit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });

            toggleItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.toggle(toggleItem);
                }
            });

            prefsItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer.showPrefsDialog();
                }
            });

            aboutItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null, "Pomodoro technique tray application\nby timur560\n" +
                            "(c) 2015", "About", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            //Add items to pop-up menu
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(toggleItem);
            popup.add(prefsItem);
            popup.addSeparator();
            popup.add(quitItem);

            trayIcon.setPopupMenu(popup);

            tray.add(trayIcon);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}
