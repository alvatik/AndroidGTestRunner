package org.paleozogt.gtestrunner;

import android.content.Context;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GTestRunner {
    public GTestRunner(Context ctx) {
        System.loadLibrary(getClass().getSimpleName());
        cptr= create(ctx.getCacheDir().getAbsolutePath());
        tests= loadTestsNames();
    }

    @Override
    protected void finalize() {
        destroy(cptr);
        cptr= 0;
    }

    public boolean run(String arg, MutableObject<String> output) {
        return run(cptr, arg, output);
    }

    public boolean getListOfTests(MutableObject<String> output) {
        return getListOfTests(cptr, output);
    }

    public boolean runOne(String testName, MutableObject<String> output) {
        return runOne(cptr, testName, output);
    }

    protected List<String> loadTestsNames() {
        List<String> tests= new ArrayList<>();

        MutableObject<String> output= new MutableObject<>();
//        run("--gtest_list_tests", output);
        getListOfTests(output);
        Scanner scanner= new Scanner(output.getValue());
        String parent= "";
        while (scanner.hasNextLine()) {
            String line= StringUtils.trim(scanner.nextLine());
            if (line.endsWith(".")) {
                parent= line;
            } else {
                if (line.contains("#")) {
                    line= StringUtils.trim(line.split("#")[0]);
                }

                tests.add(parent + line);
            }
        }
        scanner.close();

        return tests;
    }

    List<String> tests= new ArrayList<>();
    public List<String> getTests() { return tests; }

    protected long cptr;
    protected native static long create(String tempDir);
    protected native void destroy(long cptr);
    protected native boolean run(long cptr, String arg, MutableObject<String> output);

    protected native boolean getListOfTests(long cptr, MutableObject<String> output);
    protected native boolean runOne(long cptr, String testName, MutableObject<String> output);

}
