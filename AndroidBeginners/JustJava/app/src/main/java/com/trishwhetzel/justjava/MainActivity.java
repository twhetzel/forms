package com.trishwhetzel.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {

    private static final String TAG = "MainActivity";
    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //int price = quantity * 5;
        //int price = calculatePrice();

        //displayMessage(priceMessage + message);
        //displayPrice(quantity * 5);

        EditText firstNameEditText = (EditText) findViewById(R.id.first_name);
        String name = firstNameEditText.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.checkbox1);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.checkbox2);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String message = createOrderSummary(price, name, hasWhippedCream, hasChocolate);
        Log.d("TAG", message);
        //displayMessage(message);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null ) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream whether or not the user wants whipped cream topping
     * @param addChocolate whether or not the user wants chocolate topping
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        //int price = quantity * 5;

        // Add $1 if user wants whipped cream
        if (addWhippedCream) {
            basePrice += 1;
        }

        // Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice += 2;
        }
        int totalPrice = basePrice * quantity;
        return totalPrice;
    }

    /**
     * Creates order summary text
     *
     * @param orderPrice of the order
     * @return text summary
     */
    private String createOrderSummary(int orderPrice, String name, boolean addWhippedCream, boolean addChocolate){
        String priceMessage =  getString(R.string.order_summary_name, name);
        priceMessage += getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += getString(R.string.order_summary_quantity, quantity);
        priceMessage += getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(orderPrice));
        priceMessage += getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        CharSequence text = "The quantity can not be greater than 100.";
        int duration = Toast.LENGTH_SHORT;

        if(quantity == 100) {
            // Alternative way to code Toast vs. style in decrement method
            Toast.makeText(this, text, duration).show();
            return;
        }
        quantity ++;
        Log.d(TAG, String.valueOf(quantity));
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity --;

        Context context = getApplicationContext();
        CharSequence text = "The quantity must by 1 or greater.";
        int duration = Toast.LENGTH_SHORT;

        if(quantity <= 0) {
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            // Reset quantity value back to 1
            quantity = 1;
            return;
        }
        Log.d(TAG, String.valueOf(quantity));
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberToDisplay) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        //quantityTextView.setText("" + number);
        quantityTextView.setText(String.valueOf(numberToDisplay));
    }

    /**
     * This method displays the given price on the screen.
     */
//    private void displayPrice(int number) {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message) {
//        //TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }
}
