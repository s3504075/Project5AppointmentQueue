//Giancarlo Fruzzetti
// COP 2805 Project 5
//2-24-2023
//contact class

package AppointmentQP5;

import java.time.ZoneId;

public class Contact
{
    private StringBuilder name;
    private String email;
    private String phone;

    private Reminder remind;
    private ZoneId zone;

    Contact (String fName, String lName, String email, String phone, Reminder r, ZoneId z) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.email = email;
        this.phone = phone;
        this.remind = r;
        this.zone = z;
    } //constructor

    public StringBuilder getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public Reminder getReminder()
    {
        return remind;
    }

    public Reminder setReminder(Reminder R)
    {
        this.remind=R;
        return remind;
    }
    {}

    public ZoneId getZone()
    {
        return zone;
    }

    @Override
    public String toString() {
        String s = this.name + " email: " + this.email + " phone: " + this.phone + " reminder: " + this.remind + " time zone: " + this.zone;
        return s;
    }

}
