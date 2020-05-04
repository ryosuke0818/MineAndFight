package jp.hack.minecraft.mineandfight.core.utils;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Threading {
    private final static int PRINT_WARNING_MIN_DURATION = 100; // ms

    public static void ensureServerThread(JavaPlugin plugin, Runnable runnable) {
        Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public static CompletableFuture<Void> postToServerThread(JavaPlugin plugin, Runnable runnable) {
        return postToServerThread(plugin, Executors.callable(runnable, null));
    }

    public static <T> CompletableFuture<T> postToServerThread(JavaPlugin plugin, Callable<T> runnable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        String currentThreadName = Thread.currentThread().getName();

        // Get stack trace.
        final String[] stackTrace = { "" };
        try {
            throw new Exception("Debug slow runnable insight");
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            stackTrace[0] = sw.toString();
        }

        Bukkit.getScheduler().runTask(plugin, () -> {
            T value;
            try {
                value = runnable.call();
                future.complete(value);
            } catch (Exception e) {
                e.printStackTrace();
                future.completeExceptionally(e);
            }
        });

        return future;
    }
}
