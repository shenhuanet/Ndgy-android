package com.shenhua.nandagy;

import com.shenhua.commonlibs.utils.DESUtils;

import org.junit.Test;

import static android.R.attr.id;
import static android.R.attr.name;
import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);

        String num = "";
        String id = "";
        String name = "";
        System.out.println(DESUtils.getInstance().encrypt(num) + "  "
                            + DESUtils.getInstance().encrypt(id) + "  "
                            + DESUtils.getInstance().encrypt(name));

    }

}