package com.example.android.justjava;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

int numberOfCoffees = 2;
int price;
int pricePerCup = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox Whipped = findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = Whipped.isChecked();
        Log.v("MainActivity","Has whipped cream? " + hasWhippedCream);
        CheckBox Chocolate = findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = Chocolate.isChecked();
        EditText customerName_field = findViewById(R.id.customerName);
        String customerName = customerName_field.getText().toString();

        price = calculatePrice(numberOfCoffees,pricePerCup,hasChocolate,hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, customerName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + customerName);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
//        displayMessage(priceMessage);

    }

    private String createOrderSummary (int price, boolean hasWhippedCream, boolean hasChocolate, String customerName){
        String priceMessage = getString(R.string.customerName, customerName);
        priceMessage += "\nAdd whipped cream? " + hasWhippedCream;
        priceMessage += "\nAdd chocolate topping? " + hasChocolate;
        priceMessage += "\nQuantity: " + numberOfCoffees;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(int numberOfCoffees, int pricePerCup, boolean hasChocolate, boolean hasWhippedCream) {
        if (hasChocolate) {
            price = (numberOfCoffees * pricePerCup) + 2;
            if (hasWhippedCream){
                price = price + 1;
            }
        } else {
            price = numberOfCoffees * pricePerCup;
            if (hasWhippedCream){
                price = price + 1;
            }
        }
        return price;
    }

    /**
     * This method is called when the + button is clicked.
     */
    public void increment(View view) {
        if (numberOfCoffees == 100) {
            return;
        }
        numberOfCoffees = numberOfCoffees + 1;
        displayQuantity(numberOfCoffees);
    }
    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (numberOfCoffees == 1) {
            return;
        }
        numberOfCoffees = numberOfCoffees - 1;
        displayQuantity(numberOfCoffees);
    }

    /**
     * This method displays the given quantity value on the screen.
     * concatenating the number is essential regardless of the warning of the IDE
     * because if the "" + isn't there I get an error: String Resource if #0x3
     */
    @SuppressLint("SetTextI18n")
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen. (TextView) is called casting and it turns the View into a TextView
     * since findViewById returns a View object- but it only works if the view is a TextView! But it seems to be redundant...
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }
}