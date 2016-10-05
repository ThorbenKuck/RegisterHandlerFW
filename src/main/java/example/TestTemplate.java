package example;

import de.thorbenkuck.rhfw.templates.RegisterTemplate;
import example.tester.A;
import example.tester.C;

public class TestTemplate extends RegisterTemplate {

    public TestTemplate() {
        setLegereId("1");
        addToAutoPull(C.class.getName());
        addToAutoPull(A.class.getName());
        setAutoPull(true);
    }

}
