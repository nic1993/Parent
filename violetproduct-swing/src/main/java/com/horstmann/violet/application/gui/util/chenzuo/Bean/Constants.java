package com.horstmann.violet.application.gui.util.chenzuo.Bean;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by geek on 2017/8/15.
 */
public class Constants {
    public static AtomicBoolean ISFINISH = new AtomicBoolean(false);
    public static int PEROID = 5;
    public static int PORT = 5555;
    public static TimeUnit TIME_TYPE = TimeUnit.SECONDS;
}
