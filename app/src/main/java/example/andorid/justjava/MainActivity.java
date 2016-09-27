package example.andorid.justjava;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

//import static android.R.attr.name;

//import static android.R.id.message;


public class MainActivity extends AppCompatActivity {

    int numberOfCoffees = 0;
    int numberOfTopping = 0;
    boolean addCream, addChocolate, addBanana = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addListenerOnButton();
    }

    /**
     * This method called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // set variables
        String addresses[] = {"setiawan.wawan17@gmail.com"};
        String subject = "Order Coffee Just Java";
        String summary = createOrderSummary(numberOfCoffees);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(summary);
        numberOfTopping = 0;
    }

    /**
     * Increase order
     *
     * @param view of order
     */
    public void increaseOrder(View view) {
        numberOfCoffees += 1;
        display(numberOfCoffees);
        displayPrice(numberOfCoffees * 5);
    }

    /**
     * Decrease order
     *
     * @param view of order
     */
    public void decreaseOrder(View view) {
        if (numberOfCoffees > 0) {
            numberOfCoffees -= 1;
        }
        display(numberOfCoffees);
        displayPrice(numberOfCoffees * 5);
    }

    /**
     * This method display the quantity on the screen
     */
    private void display(int number) {
        TextView quantityView = (TextView) findViewById(R.id.number_quantity_text_view);
        quantityView.setText(String.valueOf(number));
    }

    /**
     * This method display the price on the screen
     *
     * @param number as price number
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.number_price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This is method for displaying message
     *
     * @param message as String
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.number_price_text_view);
        priceTextView.setText(message);
    }

    /**
     * Method for calculate price
     *
     * @return price
     */
    private int calculatePrice() {
        if (addCream) {
            numberOfTopping += 1;
        }
        if (addChocolate) {
            numberOfTopping += 1;
        }
        if (addBanana) {
            numberOfTopping += 1;
        }
        return (numberOfCoffees * (5 + (numberOfTopping * 2)));
    }

    /**
     * This method to create order summary
     *
     * @param numberOfCoffees as number of coffees
     * @return string
     */
    private String createOrderSummary(int numberOfCoffees) {
        String name = ((EditText) findViewById(R.id.name)).getText().toString();
        int price = calculatePrice();
        return "Name: " + name + " \r\n" +
                "Add whipped cream? " + addCream + "\r\n" +
                "Add whipped cream? " + addChocolate + "\r\n" +
                "Add whipped cream? " + addBanana + "\r\n" +
                "Quantity: " + numberOfCoffees + "\r\n" +
                "Total: " + NumberFormat.getCurrencyInstance().format(price) + "\r\n" +
                "Thank you!";
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.topping_checkbox:
                if (checked) {
                    addCream = true;
                } else {
                    addCream = false;
                }
                break;
            case R.id.topping_chocolate_checkbox:
                if (checked) {
                    addChocolate = true;
                } else {
                    addChocolate = false;
                }
                break;
            case R.id.topping_banana_checkbox:
                if (checked) {
                    addBanana = true;
                } else {
                    addBanana = false;
                }
                break;
        }

    }

    private void addListenerOnButton() {
        Button btnSend = (Button) findViewById(R.id.sendBtn);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"Message sent...",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
