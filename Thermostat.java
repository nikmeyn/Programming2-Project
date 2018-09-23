public class Thermostat
{
    //Instance variables
    private double setting;
    private double overHeat;    
    private double thresh;;
    LivingArea room;
    int eff;
    int year;
    int area;
    double roomTemp;
    double envTemp;
    double thermoTemp;
    double overH;
    private int furnSwitch = 0;

    /**
     * Constructor for Thermostat
     **/public Thermostat (double set, double over)
    {

        setting = set;
        overHeat = over;

        thresh = setting + overHeat;

    }

    public double getThresh()
    {
        return thresh;
    }

    public int setSwitch(double temp)
    {
        //local variables
        double currTemp = temp;

        //checks if furnace should be on or off
        if (currTemp < thresh && furnSwitch == 0)
        {
            furnSwitch = 1;
        }
        if (currTemp <= thresh && furnSwitch == 2)
        {
            furnSwitch = 0;
        }
        if (currTemp < setting)
        {
            furnSwitch =1;
        }
        if (currTemp >= thresh)
        {
            furnSwitch =2;
        }
        
        return furnSwitch;
    }
}
