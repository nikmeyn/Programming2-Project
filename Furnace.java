public class Furnace
{
    //Instance variables
    private int btu;
    private double furnOut;
    
    /**
     * Constructor for Furnace
     **/public Furnace(int year, int size)
    {
        //checks year and size
        if (year > 1980)
        {
            if (size < 1300){btu = 40000;}
            else if (size < 1700) {btu = 45000;}
            else if (size < 2500) {btu= 50000;}
            else if (size < 3500) {btu = 60000;}
            else if (size < 4500) {btu = 70000;}
        }
        else if (year <= 1980)
        {
            if (size < 1300){btu = 50000;}
            else if (size < 1700) {btu = 55000;}
            else if (size < 2500) {btu = 65000;}
            else if (size < 3500) {btu = 80000;}
            else if (size < 4500) {btu = 100000;}
        }
        
    }
    public int getCap()
    {
        //sets capacity
        return btu;
        
    }
    public double getBTU(double eff, double time)
    {
        //calculates Q in
        int btuS = getCap();
        double efft = eff / 100;
        furnOut =  ((btuS * (efft) ) *(time/3600));
        
        return furnOut;
    }  
   
}
