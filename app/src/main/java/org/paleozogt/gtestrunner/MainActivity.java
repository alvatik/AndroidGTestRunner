package org.paleozogt.gtestrunner;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.apache.commons.lang3.mutable.MutableObject;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    GTestRunner gTestRunner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gTestRunner = new GTestRunner(this);

        final TextView testOutput = (TextView) findViewById(R.id.test_output);

        final Spinner testListSpinner = (Spinner) findViewById(R.id.test_list);
        List<String> list = gTestRunner.getTests();
        list.add(0, "*");
        testListSpinner.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, list));

        Button runTestsButton = (Button) findViewById(R.id.run_tests);
        runTestsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MutableObject<String> output= new MutableObject<>();
//                gTestRunner.run("--gtest_filter=" + testListSpinner.getSelectedItem(), output);
                gTestRunner.runOne(testListSpinner.getSelectedItem().toString(), output);
                testOutput.setText(output.getValue());
            }
        });
    }
}
