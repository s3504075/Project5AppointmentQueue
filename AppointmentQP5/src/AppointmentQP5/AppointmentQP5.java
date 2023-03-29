//Giancarlo Fruzzetti
// COP 2805 Project 5
//2-24-2023
//main program


package AppointmentQP5;

import java.time.*;
import java.util.*;
//import java.time.ZoneId;
//import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.format.DateTimeFormatter;
import Dispatch.Dispatcher; //import local dispatcher


public class AppointmentQP5 implements CalendarReminder, Dispatcher<ReminderObj>{ /*will add here Dispatcher<AppointmentAppQP5> */

    public static final Scanner input = new Scanner(System.in); //a global scanner input

    private ArrayList<Appointment> clientappts = new ArrayList<>();
    private Queue<ReminderObj> queue = new LinkedList<ReminderObj>(); //dispatching queue

    public AppointmentQP5() {
    }

    public void dispatch(ReminderObj O) {
        this.queue.add(O);
        Reminder R;
        R=O.getContact().getReminder();
        System.out.println("current queue length is " + this.queue.size());
        if(R==Reminder.TEXT) {  //text reminder
            System.out.println("sending appointment email reminder to " + O.getContact().getName() +  " "  + O.getContact().getEmail() + "\n");
            //System.out.println(O.toString() + "\n");

        }
        else { //email reminder
            System.out.println("sending appointment text reminder to " + O.getContact().getName() +  " "  + O.getContact().getPhone() + "\n");
            //System.out.println(O.toString() + "\n");

        }
    }

    public void runAppts(ZonedDateTime reminder, AppointmentQP5 A1)
    {
        int timecomparison;
        //String Rem;
        ZonedDateTime currentTime;
        ReminderObj O; //reminder objectd

        for (Appointment A : A1.clientappts)
        {
            currentTime=ZonedDateTime.now();
            timecomparison=currentTime.compareTo(reminder);
            //System.out.println(timecomparison);
            if(timecomparison > 0)
            {
                O=A1.buildReminder(A);  //build reminder object
                A1.sendReminder(O);  //send reminder object
            }

        }

    }

    public ReminderObj buildReminder(Appointment appt) //build reminder and return to main
    {

        //datetime manipulator functions https://docs.oracle.com/javase/10/docs/api/java/time/ZonedDateTime.html#get(java.time.temporal.TemporalField)
        //StringBuilder manipulator functions https://docs.oracle.com/javase/7/docs/api/java/lang/StringBuilder.html
        StringBuilder S = new StringBuilder("");
        StringBuilder plusses = new StringBuilder("\n");
        ReminderObj O; //reminder object
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


        appt_as_string=S.toString();
        O=new ReminderObj(appt.getContact(), appt_as_string, appt.getReminderTime());
        return O;

    }

    public void sendReminder(ReminderObj O)
    {
        //System.out.println(O.toString());
        Reminder R = Reminder.randomMethod(); //random reminder delivery
        O.getContact().setReminder(R);
        //dispatch(O); //call dispatcher

        //lambda call for dispatcher
        Dispatcher<ReminderObj> d = (c)-> {
            this.queue.add(c);
            //Reminder R;
            //R=c.getContact().getReminder();
            System.out.println("current queue length is " + this.queue.size());
            if(R==Reminder.TEXT) {  //text reminder
                System.out.println("sending appointment email reminder to " + c.getContact().getName() +  " "  + c.getContact().getEmail() + "\n");
                System.out.println(c.toString() + "\n");

            }
            else { //email reminder
                System.out.println("sending appointment text reminder to " + c.getContact().getName() +  " "  + c.getContact().getPhone() + "\n");
                System.out.println(c.toString() + "\n");

            }
        };
        d.dispatch(O);
    }


    public void addAppointments(Appointment... appointments) {
        for (Appointment A : appointments) {
            clientappts.add(A);
        }
    }

    public static int getRandomMonth() {
        // define the range
        int max = 12;
        int min = 1;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        //System.out.println("Months ahead appt:" + rand); testing random month
        return (rand);

    }

    public static int getRandomHours() {
        // define the range
        int max = 24;
        int min = 2;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        //System.out.println("hours before reminder:" + rand); testing random hours
        return (rand);

    }


    public static void main(String[] args) {


        int month, currentmonth, currentyr, numberappts;//random appt month, currentmonth, number appts to generate as per user
        ZonedDateTime currentTime, apptdate, appttime, reminder;
        var zone = ZoneId.of("US/Eastern");
        //int minushours;
        //String tbf;
        //final int appthours = 12; //set offset for the appointment time vs now
        //int timecomparison; //is current time later than reminder time
        //String Rem;  //for the final reminder string in the box

        //default info
        Reminder R=null; //reminder object
        Contact client = new Contact("Olivia", "Migiano", "OliviaM@att.net", "904-666-2424", R, zone);
        Contact client2 = new Contact("Matthew", "Webb", "MWebb@golf.com", "904-252-6633", R, zone);
        Contact client3 = new Contact("Janet", "Logan", "JLogan@dcps.net", "966-222-2222", R, zone);
        String apptitle = "Medical Appointment with Dr. I.M.A. Quack.";
        String description = "Pending Appointment.";
        AppointmentQP5 A1 = new AppointmentQP5();  //object



        System.out.print("Enter number of random appointments for the clients: ");
        numberappts = input.nextInt();


        reminder=ZonedDateTime.now(); //set this default to this moment to ensure delivery

        //set appt general information
        for (int i = 0; i < numberappts; i++) //create n random appts for 3 clients
        {
            Appointment clientappt = new Appointment(); //pass info to constructor here
            Appointment clientappt2 = new Appointment();
            Appointment clientappt3 = new Appointment();

            clientappt.setContact(client); //set the client info, clientappt.getContact() gets the contact
            clientappt2.setContact(client2);
            clientappt3.setContact(client3);

            currentTime = ZonedDateTime.now(); //get current time
            String formattedZdt = currentTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            ZonedDateTime zoneddatetime = ZonedDateTime.parse(formattedZdt);
            month = AppointmentQP5.getRandomMonth(); //get random month
            currentmonth = zoneddatetime.getMonthValue();
            //minushours = AppointmentQP5.getRandomHours();
            //reminder = apptdate.minusHours(minushours);
            //currentyr = zoneddatetime.getYear();

            //System.out.println(currentTime + " " + month + " " + currentmonth + " " + currentyr + " " + minushours);
            if (month + currentmonth < 12) {
                apptdate = currentTime.plusMonths(month); //set to random month n months into the future
                apptdate = apptdate.plusHours(4);

                //apptdate=ZonedDateTime.now().plusHours(appthours);

                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);

                apptdate = apptdate.plusHours(6);

                clientappt2.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt2);

                apptdate = apptdate.plusHours(7);

                clientappt3.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt3);

            }
            else //overlapped to 13 mos
            {
                apptdate = currentTime.plusYears(1); //add year
                apptdate = apptdate.plusMonths(month + currentmonth - 12); //add month
                apptdate = apptdate.plusHours(4);
                //reminder = apptdate.minusHours(minushours);
                //apptdate=ZonedDateTime.now().plusHours(appthours);

                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);

                apptdate = apptdate.plusHours(5);
                clientappt2.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt2);


                apptdate = apptdate.plusHours(6);
                clientappt3.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt3);
            }
            //var appointmenttime = ZonedDateTime.of(apptdate, appttime, zone);
            //var remindertime = ZonedDateTime.of(reminder, appttime, zone);
            //clientappt.display();
            //System.out.println(apptitle + " " + description + " " + apptdate);
            //System.out.println(appttime + " " + reminder);
        }
        if (A1.clientappts.isEmpty() == false)
        {

            // for (Appointment A : A1.clientappts)
            // {
            //     A.display();

            // }

            A1.runAppts(reminder, A1);
         /*
            for (Appointment A : A1.clientappts)
            {
                currentTime=ZonedDateTime.now();
                timecomparison=currentTime.compareTo(reminder);
                //System.out.println(timecomparison);
                if(timecomparison > 0)
                {
                    Rem=A1.buildReminder(A);
                    A1.sendReminder(Rem);
                }

            } */
        }


    }
}
