package com.acelost.inkerman;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SetupInk {

    @Nullable
    private static Printer sPrinter;

    @NonNull
    public static Printer getPrinter() {
        if (sPrinter == null) {
            sPrinter = new DefaultPrinter();
        }
        return sPrinter;
    }

    public static void setPrinter(@Nullable final Printer printer) {
        sPrinter = printer;
    }

    private static class DefaultPrinter implements Printer {
        @Override
        public void println(@NonNull String message) {
            Log.println(Log.INFO, "InkMessage", message);
        }
    }

    public interface Printer {

        void println(@NonNull String message);

    }

}
