package com.example.tipcalculatorv2app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    private EditText etAmountBill , etTipAmount, etPeopleAmount;
    private TextView tvTipTotal, tvTotalBill, tvTotalPerPerson, tvTipPerPerson, tvBillPerPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextChangeHandler tch = new TextChangeHandler();

        //The EditTexts are here
        etAmountBill =findViewById(R.id.etNDAmtBill);
        etTipAmount = findViewById(R.id.etTipNumber);
        etPeopleAmount = findViewById(R.id.etPeopleNumber);

        //The TextChangers are here
        etAmountBill.addTextChangedListener(tch);
        etTipAmount.addTextChangedListener(tch);
        etPeopleAmount.addTextChangedListener(tch);


        //The TextViews are here
        tvTipTotal = findViewById(R.id.tvTipAmt);
        tvTotalBill = findViewById(R.id.tvTotalAmt);
        tvTotalPerPerson = findViewById(R.id.tvPeopleCalTotal);
        tvTipPerPerson = findViewById(R.id.tvTipPerPersonDisplay);
        tvBillPerPerson = findViewById(R.id.tvBillEachDisplay);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }
    private class TextChangeHandler implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            TipCalculation();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private boolean isValidBill(double bill, double maxBill) {
        if (bill <= 0) {
            showError("Bill can not be less than or equal to 0.");
            return false;
        }

        if (bill > maxBill) {
            showError("Bill is higher than the max limit allowed.");
            return false;
        }

        return true;
    }

    private boolean isValidTip(double tip) {
        if (tip < 0 || tip > 100) {
            showError("Tip must be between 0 and 100.");
            return false;
        }

        return true;
    }

    private boolean isValidPeople(int people, int maxPeople) {
        if (people <= 0 || people > maxPeople) {
            showError("The number of people must be between 1 and " + maxPeople + ".");
            return false;
        }

        return true;
    }

    private void showError(String errorMessage) {
        Snackbar.make(findViewById(android.R.id.content), errorMessage, Snackbar.LENGTH_LONG).show();
    }

    private void TipCalculation()
    {
    try {
        String BillText = etAmountBill.getText().toString();
        String TipText = etTipAmount.getText().toString();
        String PeopleText = etPeopleAmount.getText().toString();

        if (BillText.isEmpty() || TipText.isEmpty() || PeopleText.isEmpty()) {
            // Handles the case where any field is empty
            return;
        }

        double BillTotal = Double.parseDouble(BillText);
        double TipPercentage = Double.parseDouble(TipText);
        int NumOfPeople = Integer.parseInt(PeopleText);

        //to set the max values for each of the main variables
        double maxBill = 2000.00;
        int maxPeople = 150;

        if (!isValidBill(BillTotal, maxBill) || !isValidTip(TipPercentage) || !isValidPeople(NumOfPeople, maxPeople))
        {
            //shows an error message from any of the functions above if the conditions are not meet
            return;
        }

        TipCalculator tipCal = new TipCalculator(BillTotal, TipPercentage, NumOfPeople);
        double TipTotal = tipCal.tipAmount();
        double TotalAmount = tipCal.totalAmount();
        double AmountPerPersonTotal = tipCal.totalAmountPerPerson();
        double TipPerPersonTotal = tipCal.tipAmountPerPerson();
        double BillPerPersonTotal = tipCal.BillAmountPerPerson();

        tvTipTotal.setText(String.format("%.2f", TipTotal));
        tvTotalBill.setText(String.format("%.2f", TotalAmount));
        tvTotalPerPerson.setText(String.format("%.2f", AmountPerPersonTotal));
        tvTipPerPerson.setText(String.format("%.2f", TipPerPersonTotal));
        tvBillPerPerson.setText(String.format("%.2f", BillPerPersonTotal));
    }
    catch (NumberFormatException e) {
        // Handle the case where parsing fails (e.g., non-numeric input)
        // shows an error message if it fails for debugging.
        showError("Please enter valid numeric values.");
        e.printStackTrace();
        }
    }


}
