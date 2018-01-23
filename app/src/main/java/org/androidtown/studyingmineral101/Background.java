package org.androidtown.studyingmineral101;

class Background {
    public static void run(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}