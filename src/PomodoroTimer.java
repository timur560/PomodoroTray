import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by qwer on 14.01.15.
 */
public class PomodoroTimer {

    public static final int WORK_TIME_DEFAULT = 25;
    public static final int REST_TIME_DEFAULT = 5;

    public static final int STATE_ON_HOLD = 0;
    public static final int STATE_WORK = 1;
    public static final int STATE_REST = 2;

    private int workTime;
    private int restTime;
    private int state;

    private Image yIcon, gIcon, rIcon;

    private int currentTime;

    private Timer timer = new Timer();

    private TrayIcon trayIcon;

    public PomodoroTimer(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
        workTime = WORK_TIME_DEFAULT;
        restTime = REST_TIME_DEFAULT;
        state = STATE_ON_HOLD;

        try {
            yIcon = ImageIO.read(PomodoroTray.class.getResource("images/circle_yellow.png"));
            gIcon = ImageIO.read(PomodoroTray.class.getResource("images/circle_green.png"));
            rIcon = ImageIO.read(PomodoroTray.class.getResource("images/circle_red.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.trayIcon.setImage(yIcon);
        this.trayIcon.setToolTip("Standby");
    }

    public void toggle(MenuItem menuItem) {
        switch (state) {
            case STATE_ON_HOLD:
                state = STATE_WORK;
                trayIcon.setImage(rIcon);
                currentTime = workTime * 60;
                menuItem.setLabel("Stop");
                schedule();
                break;
            case STATE_WORK:
            case STATE_REST:
                state = STATE_ON_HOLD;
                trayIcon.setImage(yIcon);
                menuItem.setLabel("Start");
                break;
        }
    }

    public void schedule() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runPomodoro();
            }
        }, 1000);
    }

    public void runPomodoro() {
        switch (state) {
            case STATE_ON_HOLD:
                timer.cancel();
                timer.purge();
                trayIcon.setImage(yIcon);
                break;
            case STATE_WORK:
                currentTime--;
                if (currentTime == 0) {
                    currentTime = restTime * 60;
                    state = STATE_REST;
                    trayIcon.setImage(gIcon);
                }
                trayIcon.setToolTip("Work hard! (" + (currentTime / 60) + ":" + (currentTime % 60) + " left)");
                schedule();
                break;
            case STATE_REST:
                currentTime--;
                if (currentTime == 0) {
                    currentTime = workTime * 60;
                    state = STATE_WORK;
                    trayIcon.setImage(rIcon);
                }
                trayIcon.setToolTip("Party time! (" + (currentTime / 60) + ":" + (currentTime % 60) + " left)");
                schedule();
                break;
        }
    }

    public void showPrefsDialog() {
        PrefsDialog dialog = new PrefsDialog(this);
        dialog.pack();
        dialog.setTitle("Preferences");

        // center dialog window on screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setLocation(dim.width / 2 - dialog.getSize().width / 2, dim.height / 2 - dialog.getSize().height / 2);

        dialog.setVisible(true);
    }

    public int getWorkTime() {
        return workTime;
    }

    public int getRestTime() {
        return restTime;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    public void setRestTime(int restTime) {
        this.restTime = restTime;
    }
}
