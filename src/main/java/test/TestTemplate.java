package test;

import templates.RegisterTemplate;
import test.tester.A;
import test.tester.C;

public class TestTemplate extends RegisterTemplate {

    public TestTemplate() {
        setLegereId("1");
        addToAutoPull(C.class.getName());
        addToAutoPull(A.class.getName());
        setAutoPull(true);
    }

}
