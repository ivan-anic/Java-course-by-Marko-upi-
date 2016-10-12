package hr.fer.android.jmbag0036480345;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import a0036480345.android.fer.hr.simplecalculus.R;

/**
 * A simple representation of a calculator with two parameters. Supports the following mathematical
 * operations:
 * <ul>
 *     <li>Addittion</li>
 *     <li>Subtraction</li>
 *     <li>Multiplication</li>
 *     <li>Division</li>
 * </ul>
 *
 * @author Ivan Anić
 * @version 1.0
 */
public class CalculusActivity extends AppCompatActivity {

    /** Represents a code which signals the activity to refresh its input fields. */
    private static final Integer REQUEST_REFRESH = 1;
    /** A reference to the first argument text field. */
    private EditText et1;
    /** A reference to the second argument text field. */
    private EditText et2;
    /** A reference to all of the text fields in this activity. */
    private List<EditText> etList;
    /** A reference to the operation chooser. */
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        Button button = (Button) findViewById(R.id.button);
        et1 = (EditText) findViewById(R.id.etArg1);
        et2 = (EditText) findViewById(R.id.etArg2);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        etList = Arrays.asList(et1, et2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
                Double result = null;

                if (!checkTextFields())
                    return;

                final Double arg1 = NumberUtil.ParseDouble(et1.getText().toString());
                final Double arg2 = NumberUtil.ParseDouble(et2.getText().toString());

                String op = null;

                try {
                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case (R.id.rbAdd):
                            result = arg1 + arg2;
                            op = getResources().getString(R.string.op1);
                            break;
                        case (R.id.rbSub):
                            result = arg1 - arg2;
                            op = getResources().getString(R.string.op2);
                            break;
                        case (R.id.rbMul):
                            result = arg1 * arg2;
                            op = getResources().getString(R.string.op3);
                            break;
                        case (R.id.rbDiv):
                            result = arg1 / arg2;
                            op = getResources().getString(R.string.op4);
                            if (result.isNaN() || result.isInfinite()) {
                                throw new IllegalArgumentException("Can not calculate with these operands!");
                            }
                            break;
                        default:
                            Toast.makeText(CalculusActivity.this, getResources()
                                    .getString(R.string.noOperationSelected), Toast.LENGTH_SHORT).show();
                            return;
                    }
                } catch (Exception ex) {
                    intent.putExtra("Message", ex.getMessage());
                    Log.d("ex", et1.getText().toString());
                    Log.d("ex", ex.getMessage());
                    result = null;
                }

                intent.putExtra("Result", result);
                intent.putExtra("Argument1", arg1);
                intent.putExtra("Argument2", arg2);
                intent.putExtra("Operation", op);
                startActivityForResult(intent, REQUEST_REFRESH);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_REFRESH && resultCode == RESULT_OK) {
            clearFields();
        }
    }

    /**
     * Clears the input fields in this activity, the number input field, and the operation chooser.
     */
    private void clearFields(){
        radioGroup.clearCheck();
        et1.setText("");
        et2.setText("");
    }

    /**
     * Checks whether the text fields are empty.
     * @return true, if no fields are empty
     */
    public boolean checkTextFields() {
        boolean flag = true;
        for (EditText e : etList) {
            if (TextUtils.isEmpty(e.getText().toString())) {
                e.setError("Field empty!");
                flag = false;
            }
        }
        return flag;
    }
}
