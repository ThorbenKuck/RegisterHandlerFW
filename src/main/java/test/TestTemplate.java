package test;

import templates.RegisterTemplate;
import test.tester.C;

public class TestTemplate extends RegisterTemplate {

    public TestTemplate() {
        setLegereId("1");
        addToAutoPull(C.class.getName());
        setAutoPull(true);
    }

}
