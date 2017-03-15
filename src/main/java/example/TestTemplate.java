package example;

import de.thorbenkuck.rhfw.templates.RegisterTemplate;
import example.tester.A;
import example.tester.C;
import example.tester.IC;

public class TestTemplate extends RegisterTemplate {

    public TestTemplate() {
        setlegerId("1");
        addToAutoPull(C.class);
        addToAutoPull(A.class);
        addToAutoPull(IC.class);
        setAutoPull(true);
    }

}
