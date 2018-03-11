package central.com;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    String myText = "";
    int myNum = 10;
    boolean increasing = false;

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        final TextView tv = findViewById(R.id.my_text);
        final ScrollView myScroll = findViewById(R.id.my_scroll);
        Button clearButton = findViewById(R.id.clear);
        Button upButton = findViewById(R.id.up);
        Button downButton = findViewById(R.id.down);
        final EditText myInput = findViewById(R.id.my_input);
        Switch mySwitch1 = findViewById(R.id.my_switch1);
        final Button myButton = findViewById(R.id.my_button);

        myInput.setText(String.valueOf(myNum));
        myInput.clearFocus();

        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            myText = "";
            tv.setText(myText);
            }
        });

        upButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            myNum++;
            myInput.setText(String.valueOf(myNum));
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            myNum--;
            myInput.setText(String.valueOf(myNum));
            }
        });

        myInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0
                        && Integer.parseInt(editable.toString()) > 0
                        && Integer.parseInt(editable.toString()) < 1000) {
                    myNum = Integer.parseInt(editable.toString());
                }
            }
        });

        mySwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                increasing = isChecked;
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                myText += stringFromJNI(myNum, increasing);
                tv.setText(myText);
            }
        });

        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myScroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     * @param myNum
     */
    public native String stringFromJNI(int myNum, boolean increasing);
}
