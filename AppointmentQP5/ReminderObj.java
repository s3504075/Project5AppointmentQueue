
package AppointmentQP5;


public class ReminderObj {

    public String buildReminder(Appointment appt) //build reminder and return to main
    {

        //datetime manipulator functions https://docs.oracle.com/javase/10/docs/api/java/time/ZonedDateTime.html#get(java.time.temporal.TemporalField)
        //StringBuilder manipulator functions https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
        StringBuilder S = new StringBuilder("");
        StringBuilder plusses = new StringBuilder("\n");

        String appt_as_string; //returns final string
        LocalDate ldt; //for printing local date
        int hour;  //to print local hour
        int minute; //to print local minute
        int max,min,maxlen,insloc,firststars; //setting string lengths with indexof

        S.append("\n\nSending the following SMS message to " + appt.getContact().getName()+ " " + appt.getContact().getPhone());
        S.append("\n\n+ Hello: " + appt.getContact().getName());
        S.append("\n+");
        S.append("\n+ This is a reminder that you have an upcoming appointment.");
        S.append("\n+ Title:" +appt.getAppointmentTitle());
        S.append("\n+ Description: " + appt.getDescription());
        //day=appt.getZdt().get(ChronoField.DAY_OF_MONTH);
        //year=appt.getZdt().get(ChronoField.YEAR);
        //m=appt.getZdt().getMonth();
        ldt=appt.getZdt().toLocalDate();
        hour=appt.getZdt().getHour();
        minute=appt.getZdt().getMinute();
        S.append("\n+ Date: " + ldt);
        S.append("\n+ Time: " + hour + ":" + minute + " " + appt.getZdt().getZone());
        min=S.indexOf("+ This",0);
        max=S.indexOf("appointment.",0)+12; //beginning + end
        //System.out.println(max+ " " + min);
        maxlen=max-min;
        //System.out.println(maxlen);
        for (int i=0; i < maxlen+1; i++)
        {
            plusses.append("+");
        }
        S.append(plusses);
        insloc=S.indexOf("+ Hello");
        //System.out.println(insloc);
        S.insert(insloc-1,plusses);
        //firststars=S.indexOf("+++")+maxlen;
        //System.out.println(firststars);
        //for(int k=2; k < 6; k++) //put in the plusses
        //{
        //S.insert(insloc+maxlen,'+');
        //}

        appt_as_string=S.toString();
        return appt_as_string;

    }

    public void sendReminder(String reminder)
    {
        System.out.println(reminder);
    }




}
