
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.lang.*;
import java.io.*;
public class UserInt extends JFrame 
{
    //Instance variables and objects, and their declarations
    JLabel labelCapacity, labelEff, labelPilot, labelHeating, labelYear, labelRoomTemp, labelRuntime;
    JLabel labelArea, labelSHC, labelBLC, labelEnvTemp, labelThermoTemp, labelOverheat, labelFreq; 
    JTextField textCap, textEff, textPilot, textHeating, textYear, textArea, textSHC, textBLC;
    JTextField textEnv, textThermo, textOverheat, textFreq, textRuntime, textRoomTemp;
    JRadioButton elecBut, gasBut;
    JButton startButton, closeButton;
    ButtonGroup ftype;
    JPanel wind;
    private Timer timer1;
    private  final int TIMER_DELAY = 5000;  // note that timer delay is in milliseconds
    private int printCount =1; // to count the number of times it prints
    DecimalFormat myFormat = new DecimalFormat("0.00");
    PrintWriter out;
    /**
     * Constructor for UserInt
     **/
    public UserInt() throws IOException
    {
        setSize(500,500);
        setTitle("Home Heating Program");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(200,200);
        createPanel();
        add(wind);
        setVisible(true); 
        FileWriter fileWrite = new FileWriter ("Report.txt", true);
        out = new PrintWriter (fileWrite);
    } 

    /**
     *Creates the window
     */public void createPanel()
    {
        //sets layout and panel
        wind = new JPanel();
        wind.setLayout(new GridLayout(15,2));

        //creates labels
        labelCapacity = new JLabel("Gas Furnace Capacity", JLabel.RIGHT);
        labelEff = new JLabel("Efficiency (in percentage)", JLabel.RIGHT);
        labelPilot = new JLabel("Pilot on", JLabel.RIGHT);
        labelHeating = new JLabel("Furnace status", JLabel.RIGHT);
        labelYear = new JLabel("Year built", JLabel.RIGHT);
        labelRoomTemp = new JLabel("Current Room Temperature(in Celsius)", JLabel.RIGHT);
        labelArea = new JLabel("Room Area (in Square foot)", JLabel.RIGHT);
        labelSHC = new JLabel ("Specific Heat Capacity (in BTU/sq. ft)", JLabel.RIGHT);
        labelBLC = new JLabel ("Basic Load Constant (in BTU/sq. ft)", JLabel.RIGHT);
        labelEnvTemp = new JLabel ("Environment Temperature (in Celsius)", JLabel.RIGHT);
        labelThermoTemp = new JLabel ("Thermostat Temp Setting (in Celsius)", JLabel.RIGHT);
        labelOverheat = new JLabel ("Overheat setting", JLabel.RIGHT);
        labelFreq = new JLabel ("Display Frequency(secs)", JLabel.RIGHT);
        labelRuntime = new JLabel ("RunTime", JLabel.RIGHT);
        String furnOns = "OFF";

        //creates textfields
        textCap = new JTextField("Calculated BTU(40,000 - 75,000)");
        textEff = new JTextField("Choose gas or electric");
        textPilot = new JTextField("true");
        textHeating = new JTextField(furnOns);
        textYear = new JTextField(4);
        textRoomTemp = new JTextField(4);
        textArea = new JTextField();
        textSHC = new JTextField("4.0");
        textBLC = new JTextField("1.0");
        textEnv = new JTextField();
        textThermo = new JTextField();
        textOverheat = new JTextField();
        textFreq = new JTextField();
        textRuntime = new JTextField();

        //creates buttons
        gasBut = new JRadioButton("Gas Furnace");
        elecBut = new JRadioButton("Electric Furnace");
        startButton = new JButton("Run");
        closeButton = new JButton ("Close");

        //sets radio buttons in a group
        ftype = new ButtonGroup();
        ftype.add(gasBut);
        ftype.add(elecBut);

        //adds labels, textfields and buttons to the panel
        wind.add(labelCapacity);
        wind.add(textCap);
        wind.add(gasBut);
        wind.add(elecBut);
        wind.add(labelEff);
        wind.add(textEff);
        wind.add(labelHeating);
        wind.add(textHeating);
        wind.add(labelYear);
        wind.add(textYear);
        wind.add(labelArea);
        wind.add(textArea);
        wind.add(labelRoomTemp);
        wind.add(textRoomTemp);
        wind.add(labelSHC);
        wind.add(textSHC);
        wind.add(labelBLC);
        wind.add(textBLC);
        wind.add(labelEnvTemp);
        wind.add(textEnv);
        wind.add(labelThermoTemp);
        wind.add(textThermo);
        wind.add(labelOverheat);
        wind.add(textOverheat);
        wind.add(labelFreq);
        wind.add(textFreq);
        wind.add(labelRuntime);
        wind.add(textRuntime);
        wind.add(startButton);
        wind.add(closeButton);

        //sets some fields as static
        textSHC.setEditable(false);
        textBLC.setEditable(false);
        textEff.setEditable(false);
        textCap.setEditable(false);
        textPilot.setEditable(false);
        textHeating.setEditable(false);

        //creates action listener for buttons
        gasBut.addActionListener(new GasEff());
        elecBut.addActionListener(new ElecEff());
        closeButton.addActionListener(new Close());
        startButton.addActionListener(new Run());

    }
    private class GasEff implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            //sets efficiency text field as 90%
            textEff.setText("90");
        }
    } 
    private class ElecEff implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            //sets efficiency text field as 92%
            textEff.setText("92");
        }        
    }
    private class Close implements ActionListener
    {
        public void actionPerformed(ActionEvent a)
        {
            //closes program
            System.exit(0);
        }        
    }
    public class Run implements ActionListener
    {
        //creates a timer object
        Timer timer1;

        //instance variables
        int eff;
        int year;
        int area;
        double roomTemp;
        double envTemp;
        double thermoTemp;
        double overH;
        int freq;
        int length;

        Furnace furnace;
        Environment environment;
        Thermostat thermostat;
        LivingArea room;
        public void actionPerformed(ActionEvent a)
        {           
            boolean run = true;

            //loop for invalid input
            while(run)
            {
                try{
                    eff = Integer.parseInt(textEff.getText());
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please select a furnace type", "Invalid furnace type", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try{
                    year = Integer.parseInt(textYear.getText());
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid year", "Invalid year", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try{
                    area = Integer.parseInt(textArea.getText());
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid area roundest to the nearest square foot", "Invalid area", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try{
                    roomTemp = Double.parseDouble(textRoomTemp.getText());
                }
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid room temperature", "Invalid room temperature", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try {envTemp = Double.parseDouble(textEnv.getText());}
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid value for the environment temperature", "Invalid environment temperature", JOptionPane.ERROR_MESSAGE);
                    break;
                }
                try {thermoTemp = Double.parseDouble(textThermo.getText());}
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid value for the thermostat temperature setting", "Invalid thermostat temperature setting", JOptionPane.ERROR_MESSAGE);
                    break; 
                }
                try {overH = Double.parseDouble(textOverheat.getText());}
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter a valid value for the overheat setting", "Invalid overheat setting", JOptionPane.ERROR_MESSAGE);
                    break; 
                }
                try {freq = Integer.parseInt(textFreq.getText());}
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter the frequency at which you want the statements to print", "Invalid frequency", JOptionPane.ERROR_MESSAGE);
                    break; 
                }
                try {length = Integer.parseInt(textRuntime.getText());}
                catch (NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"please enter the duration of the program", "Invalid run time", JOptionPane.ERROR_MESSAGE);
                    break; 
                }
                furnace = new Furnace(year, area);
                environment = new Environment(envTemp);
                thermostat = new Thermostat (thermoTemp, overH);
                room = new LivingArea (roomTemp);
                textCap.setText(""+ furnace.getCap());

                //prints to file
                out.println("Gas Furnace: Capacity = " + textCap.getText()+ "\t" + "Efficiency = " + eff +"\t"+"Pilot = on" + "\t" +"Heating = false");
                out.println("Room: Year built = " + year + "\t" + "Temperature = " + roomTemp + "\t" + "Area(sq. ft.) = " + area + "SHC = 4.0" + "/t" + "BLC = 1.0");
                out.println("Environment Temperature = " + envTemp + "\t" + "Thermostat: Desired Temperature = " + thermoTemp + "\t" + "OverHeat setting = " + overH);
                out.println("Simulation: Frequency = " + freq + "\t" + "Runtime = " + length);

                //sets parameters for timer
                int timerC = freq * 60;
                int timer = freq * 1000;
                timer1 = new Timer(timer, new TimerTestListener());

                //prints to console
                System.out.println("Time\t\tInside\t\tOutside\t\tFurnace Status");
                System.out.println("----\t\t------\t\t-------\t\t------------");
                System.out.println("0\t\t" + roomTemp +"\t\t"+ envTemp + "\t\tOFF"); 

                //starts timer
                timer1.start();             
                timerC = freq * 60;  
                double currentTemp = roomTemp;
                run = false;

            }

        }
        private class TimerTestListener implements ActionListener
        {
            //instance variables
            int count = 0;
            int timerC = freq * 60;  
            double currentTemp = roomTemp;

            public void actionPerformed(ActionEvent e)
            {
                currentTemp = room.calcNewTemp(eff, year, area, envTemp, thermoTemp,overH); 

                //PRINTS VALUES AT EVRY FREQUENCY INTERVAL TO BOTH THE CONSOLE AND FILE
                if (printCount <= (length/300)) // to control the number of times the action is performed
                {
                    int switcheS = thermostat.setSwitch(currentTemp);

                    if (switcheS == 1)
                    {
                        System.out.println(timerC+"\t\t"+ myFormat.format(currentTemp)+"\t\t"+ envTemp +"\t\tON");

                        out.println(timerC+"\t\t"+ myFormat.format(currentTemp)+"\t\t"+ envTemp +"\t\tON");

                    }
                    else
                    {
                        System.out.println(timerC+"\t\t"+ myFormat.format(currentTemp)+"\t\t"+ envTemp +"\t\tOFF");

                        out.println(timerC+"\t\t"+ myFormat.format(currentTemp)+"\t\t"+ envTemp +"\t\tOFF");

                    }
                    printCount++;
                    timerC += 300;
                }
                else 
                {
                    //stops timer and closes writer
                    timer1.stop(); 
                    out.println("\n\n\n");
                    out.close();
                }

            }
        }

    }

}

