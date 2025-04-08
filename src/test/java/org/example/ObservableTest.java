package org.example;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ObservableTest {

    @Test
    public void testJustOperator() {
        List<Integer> results = new ArrayList<>();

        Observable.just(1, 2, 3).subscribe(new Observer<Integer>() {
            @Override public void onSubscribe(Disposable d) {}
            @Override public void onNext(Integer item) { results.add(item); }
            @Override public void onError(Throwable e) { fail("Unexpected error"); }
            @Override public void onComplete() {}
        });

        assertEquals(Arrays.asList(1, 2, 3), results);
    }

    @Test
    public void testMapOperator() {
        List<Integer> results = new ArrayList<>();

        Observable.just(1, 2, 3)
                .map(x -> x * 2)
                .subscribe(new Observer<Integer>() {
                    @Override public void onSubscribe(Disposable d) {}
                    @Override public void onNext(Integer item) { results.add(item); }
                    @Override public void onError(Throwable e) { fail("Unexpected error"); }
                    @Override public void onComplete() {}
                });

        assertEquals(Arrays.asList(2, 4, 6), results);
    }

    @Test
    public void testErrorHandling() {
        List<String> events = new ArrayList<>();

        Observable.just(1, 2, 0, 4)
                .map(x -> 10 / x)
                .subscribe(new Observer<Integer>() {
                    @Override public void onSubscribe(Disposable d) {}
                    @Override public void onNext(Integer item) { events.add("Next: " + item); }
                    @Override public void onError(Throwable e) { events.add("Error: " + e.getMessage()); }
                    @Override public void onComplete() { events.add("Complete"); }
                });

        assertTrue(events.contains("Error: / by zero"));
        assertFalse(events.contains("Complete"));
    }
}