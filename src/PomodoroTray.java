import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by qwer on 13.01.15.
 */
public class PomodoroTray {

    public static final int WORK_TIME = 25;
    public static final int REST_TIME = 5;

    public static void main(String[] args) {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }

        final PopupMenu popup = new PopupMenu();

        try {
            final TrayIcon trayIcon = new TrayIcon(ImageIO.read(PomodoroTray.class.getResource("images/circle_green.png")));

            final SystemTray tray = SystemTray.getSystemTray();

            // Create a pop-up menu components
            MenuItem aboutItem = new MenuItem("About");
            MenuItem startStopItem = new MenuItem("Start");
            MenuItem prefsItem = new MenuItem("About");
            MenuItem quitItem = new MenuItem("Quit");

            quitItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            //Add components to pop-up menu
            popup.add(aboutItem);
            popup.addSeparator();
            popup.add(startStopItem);
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
