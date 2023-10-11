package com.antandbuffalo.quicknote.utilities;


import android.os.Handler;

import java.util.HashMap;

public class Debouncer {
    private final HashMap<String, Runnable> delayedMap = new HashMap<>();
    private final Handler handler = new Handler();

    public void debounce(final String key, final Runnable runnable, long delay) {
        // Cancel previous one
        handler.removeCallbacks(delayedMap.get(key));

        // Now add a new one
        Runnable task = new Runnable() {
            @Override
            public void run() {
                runnable.run();
                delayedMap.remove(key);
            }
        };
        handler.postDelayed(task, delay);
        delayedMap.put(key, task);
    }
}
