package com.example.hello_just_java;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int basePrice = 5;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void order(View view) {
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        Editable nameEdible = nameInput.getText();
        String name = nameEdible.toString();

        CheckBox whipper = (CheckBox) findViewById(R.id.shipped_checkbox);
        boolean hasWhipped = whipper.isChecked();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        int price = calculatePrice(hasWhipped, hasChocolate);

        String message = orderScore(name, price, hasWhipped, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public void decrement(View view) {
        if (quantity > 0) {
            --quantity;
        }
        displayQuantity(quantity);

    }

    public void increment(View view) {
        if (quantity <= 100) {
            ++quantity;
        }
        displayQuantity(quantity);

    }

    private int calculatePrice(boolean addWhipped, boolean addChocolate) {

        if (addWhipped) {
            basePrice = basePrice + 1;
        }

        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }


    private String orderScore(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantity = (TextView) findViewById(R.id.quantity_text);
//        TextView price = (TextView) findViewById(R.id.text_price);
        quantity.setText(" " + numberOfCoffees);
//        price.setText("$" + (5 * numberOfCoffees));
    }

}