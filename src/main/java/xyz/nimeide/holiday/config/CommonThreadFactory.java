package xyz.nimeide.holiday.config;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangjian
 */
public class CommonThreadFactory implements ThreadFactory {

    private AtomicInteger counter = new AtomicInteger();
    private String name;

    public CommonThreadFactory(String name) {
        this.name = name;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, name + "-" + counter.incrementAndGet());
    }
}
