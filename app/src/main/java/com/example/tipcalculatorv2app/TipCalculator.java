package com.example.tipcalculatorv2app;

public class TipCalculator {
    private double tip;
    private double bill;
    private int PeopleNo;
    public TipCalculator(double newBill, double newTip, int newPeopleNo)
    {
        setTip(newTip);
        setBill(newBill);
        setPeopleNo(newPeopleNo);
    }


    public void setTip(double newTip)
    {
        if( newTip > 0)
        {
            tip = newTip;
        }
        tip = tip /100;
        //this is divide the number to be a decimal
        //it allows the user to put in a whole number instead of a decimal
        //this is for user convenience

    }

    public void setPeopleNo(int newPeopleNo)
    {
        if( newPeopleNo > 0)
        {
            PeopleNo = newPeopleNo;
        }
    }

    public void setBill(double newBill)
    {
        if( newBill > 0)
        {
            bill = newBill;
        }
    }


    public double getTip()
    {

        return tip;
    }
    public double getBill()
    {
        return bill;
    }
    public int getPeopleNo()
    {
        return PeopleNo;
    }


    public double tipAmount()
    {
        return (getBill() * getTip());
    }
    public double totalAmount()
    {
        return(getBill() + tipAmount());
    }
    public double totalAmountPerPerson()
    {
        return (totalAmount()/getPeopleNo());
    }
    public double tipAmountPerPerson()
    {
        return (tipAmount()/getPeopleNo());
    }
    public double BillAmountPerPerson()
    {
        return (getBill()/getPeopleNo());
    }
}


