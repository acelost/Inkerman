package com.acelost.inkerman;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Ink {

    @SuppressWarnings("ConstantConditions")
    private static final String CLASS_PREFIX = Ink.class.getPackage().getName() + ".";

    private static final String NULL_SIGN = "[unknown author]";

    @CheckResult
    @NonNull
    public static Letter sign(@Nullable final Object signature) {
        return new Letter().sign(signature);
    }

    @CheckResult
    @NonNull
    public static Letter title(@NonNull final Object title) {
        return new Letter().title(title);
    }

    @CheckResult
    @NonNull
    public static Letter message(@Nullable final Object... message) {
        return new Letter().message(message);
    }

    @CheckResult
    @NonNull
    public static Letter variable(@NonNull final String name, @Nullable final Object value) {
        return new Letter().variable(name, value);
    }

    @CheckResult
    @NonNull
    public static Letter trace(final int depth) {
        return new Letter().trace(depth);
    }

    public static final class Letter {

        @NonNull
        private final List<String> words = new ArrayList<>();

        @NonNull
        private final List<String> variables = new ArrayList<>();

        @Nullable
        private String signature;

        @Nullable
        private String title;

        private int traceDepth = 0;

        @CheckResult
        @NonNull
        public Letter sign(@Nullable final Object signature) {
            this.signature = signature != null ? signature.toString() : NULL_SIGN;
            return this;
        }

        @CheckResult
        @NonNull
        public Letter title(@NonNull final Object title) {
            this.title = title.toString();
            return this;
        }

        @CheckResult
        @NonNull
        public Letter message(@Nullable final Object... messages) {
            if (messages != null) {
                for (Object message : messages) {
                    words.add(message != null ? message.toString() : "null-message");
                }
            } else {
                words.add("null-message-array");
            }
            return this;
        }

        @CheckResult
        @NonNull
        public Letter variable(@NonNull final String name, @Nullable final Object variable) {
            variables.add(name + " = " + variable);
            return this;
        }

        @CheckResult
        @NonNull
        public Letter trace(final int depth) {
            if (depth < 0) throw new IllegalArgumentException("Depth should be non-negative!");
            traceDepth = depth;
            return this;
        }

        public void print() {
            SetupInk.getPrinter().println(buildMessage());
        }

        @NonNull
        private String buildMessage() {
            final StackTraceElement[] stackTrace;
            final int traceStartIndex;
            if (signature == null || traceDepth > 0) {
                stackTrace = Thread.currentThread().getStackTrace();
                traceStartIndex = findStartIndex(stackTrace);
            } else {
                stackTrace = null;
                traceStartIndex = -1;
            }
            final String author = signature != null ? signature : getAuthor(stackTrace, traceStartIndex);
            final StringBuilder builder = new StringBuilder("Printed by ");
            builder.append(author);
            if (title != null) {
                builder.append(" \\ ").append(title);
            }
            builder.append(": ");
            for (String word : words) {
                builder.append(word).append(' ');
            }
            if (!variables.isEmpty()) {
                builder.append("\nVariables:");
                for (String variable : variables) {
                    builder.append("\n\t").append(variable);
                }
            }
            if (stackTrace != null && traceStartIndex != -1) {
                final int length = stackTrace.length;
                final int before = Math.min(length, traceStartIndex + traceDepth);
                if (traceStartIndex < before) {
                    builder.append("\nStackTrace(").append(traceDepth).append("):");
                    for (int i = traceStartIndex; i < before; ++i) {
                        builder.append("\n\t").append(formatStackTraceElement(stackTrace[i]));
                    }
                }
            }
            return builder.toString();
        }

        private int findStartIndex(@NonNull StackTraceElement[] trace) {
            boolean inkPackageAchieved = false;
            for (int i = 0; i < trace.length; i++) {
                final StackTraceElement element = trace[i];
                final String className = element.getClassName();
                if (!inkPackageAchieved) {
                    inkPackageAchieved = className.startsWith(CLASS_PREFIX);
                } else if (!className.startsWith(CLASS_PREFIX)) {
                    return i;
                }
            }
            return -1;
        }

        @NonNull
        private String getAuthor(@NonNull final StackTraceElement[] stackTrace, final int startIndex) {
            final StackTraceElement caller = startIndex != -1 ? stackTrace[startIndex] : null;
            return caller != null ? formatStackTraceElement(caller) : "unknown-author";
        }

        @NonNull
        private String formatStackTraceElement(@NonNull final StackTraceElement element) {
            return String.format(
                    ".(%s:%s):%s",
                    element.getFileName(),
                    element.getLineNumber(),
                    element.getMethodName()
            );
        }

    }

}
