public class Environment
{
    //instance variables
    private double environTemp;
    private double qLoss;
    
    /**
     * Constructor for Environment
     **/public Environment (double outTemp)
    {
        environTemp = outTemp;
    }
    public void setTemp(double outTemp)
    {
        //sets environment temp
        environTemp = outTemp;
    }
    public double getTemp()
    {
        //returns environment temp
        return environTemp;
    }
    public double getHeatLoss(int flrArea, double rmTemp,  double time)
    {
       //calculates Q Loss        
        qLoss =  ( 1.0  * flrArea * (rmTemp - environTemp)) * (time/3600);
       return qLoss;
    }
}
