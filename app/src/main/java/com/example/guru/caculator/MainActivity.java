package com.example.guru.caculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private int[] numaricButton={R.id.zero,R.id.one,R.id.two,R.id.three,R.id.four,R.id.five,R.id.six,R.id.seven,R.id.eight,R.id.nine,R.id.decimal};
    private int[]opretorbutton={R.id.add,R.id.sub,R.id.mul};
    private TextView txtScreen;

    private boolean lastNumeric;

    private boolean stateError;

    private boolean lastDot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        this.txtScreen=(TextView)findViewById( R.id.txt );

        SetNumOnClick();
        SetOprOnClick();

    }


    private  void SetNumOnClick(){
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button=(Button)v;

                if(stateError){
                    txtScreen.setText( button.getText() );
                    stateError=false;
                }else{
                    txtScreen.append( button.getText() );

                }

                lastNumeric=true;
            }
        };
        for(int id:numaricButton){
            findViewById(id ).setOnClickListener( listener );
        }

lastNumeric=true;
    }


    private  void SetOprOnClick(){
        View.OnClickListener listener= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(lastNumeric && !stateError){
                Button button=(Button)v;
                txtScreen.append( button.getText() );
                lastNumeric=false;
                lastDot=false;

            }
            }
        };
for(int id:opretorbutton){
    findViewById( id ).setOnClickListener( listener );

}
findViewById( R.id.cut ).setOnClickListener( new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        txtScreen.setText( "" );
        lastNumeric=false;
        lastDot=false;
    }
} );
        findViewById(R.id.decimal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastNumeric && !stateError && !lastDot) {
                    txtScreen.append(".");
                    lastNumeric = false;
                    lastDot = true;
                }
            }
        });

     findViewById( R.id.equal ).setOnClickListener( new View.OnClickListener() {
         @Override
         public void onClick(View v) {
onEqual();
         }
     } );

    }


    private void onEqual() {
        // If the current state is error, nothing to do.
        // If the last input is a number only, solution can be found.
        if (lastNumeric && !stateError) {
            // Read the expression
            String txt = txtScreen.getText().toString();
            // Create an Expression (A class from exp4j library)
            Expression expression = new ExpressionBuilder(txt).build();
            try {
                // Calculate the result and display
                double result = expression.evaluate();
                //txtScreen.setText(Double.toString(result));
                txtScreen.setText( Double.toString( result ) );
                lastDot = true; // Result contains a dot
            } catch (ArithmeticException ex) {
                // Display an error message
                txtScreen.setText("Error");
                stateError = true;
                lastNumeric = false;
            }
        }
    }



}
