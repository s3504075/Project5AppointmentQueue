//Giancarlo Fruzzetti
// COP 2805 Project 5
//2-24-2023
//Reminder Enum



package AppointmentQP5;
import java.util.Random;
public enum Reminder {
    TEXT, EMAIL;

    private static Random apptmethod;

    public static Reminder randomMethod()  {
        apptmethod = new Random(System.currentTimeMillis());
        Reminder[] reminders = values();
        return reminders[apptmethod.nextInt(reminders.length)];
    }
}
