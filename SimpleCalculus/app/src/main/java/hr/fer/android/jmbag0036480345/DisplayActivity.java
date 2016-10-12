package hr.fer.android.jmbag0036480345;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import a0036480345.android.fer.hr.simplecalculus.R;

/**
 * Displays the calculation result from the previous, {@linkplain CalculusActivity} activity.
 * If an error occurred, displays an appropriate message. Also offers the option to send an
 * e-mail to the company with the operation results.
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        Bundle extras = getIntent().getExtras();
        initLayout(extras);
    }

    /**
     * Initializes the layout, which depends on the calculation result.
     *
     * @param extras - the resource bundle received from the previous activity
     */
    private void initLayout(Bundle extras) {
        TextView textView = (TextView) findViewById(R.id.textView);
        String text;
        final String outText;

        String op = extras.getString("Operation");
        Double arg1 = extras.getDouble("Argument1");
        Double arg2 = extras.getDouble("Argument2");
        String msg = extras.getString("Message");
        Object result = extras.get("Result");

        if (result == null) {
            text = getResources().getString(R.string.caclculationError);
            outText = String.format(text, op, arg1, arg2, msg);
        } else {
            text = getResources().getString(R.string.result);
            outText = String.format(text, op, result);
        }

        textView.setText(outText);

        Button buttonOK = (Button) findViewById(R.id.bOK);
        Button buttonReport = (Button) findViewById(R.id.bReport);

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);
                finish();
            }
        });

        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, getResources().getStringArray(R.array.mailList));
                i.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.mailSubject));
                i.putExtra(Intent.EXTRA_TEXT, outText);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(DisplayActivity.this,
                            getResources().getString(R.string.noMailClients),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
