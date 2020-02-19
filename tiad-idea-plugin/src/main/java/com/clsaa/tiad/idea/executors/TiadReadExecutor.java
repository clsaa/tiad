package com.clsaa.tiad.idea.executors;

import com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

/**
 * A common executor for non-urgent tasks, which are expected to be fast most of the time.
 * Used to avoid spawning a lot of threads by different subsystems all reacting to the same event,
 * when all they have to do is several short PSI/index queries in reaction to a file or project model change,
 * or on project opening. If you're using {@link com.intellij.openapi.application.ReadAction#nonBlocking},
 * you might consider this executor as backend. This executor is bounded, so please don't perform long-running
 * or potentially blocking operations here.
 * <p></p>
 * <p>
 * Not to be used:
 * <ul>
 *   <li>For activities that can take significant time, e.g. project-wide Find Usages, or a Web query or slow I/O</li>
 *   <li>For background processes started by user actions, where people would wait for results, staring at the screen impatiently.</li>
 * </ul>
 */
public final class TiadReadExecutor implements Executor {
    private static final TiadReadExecutor ourInstance = new TiadReadExecutor();
    private final Executor myBackend;

    private TiadReadExecutor() {
        myBackend = AppExecutorUtil.createBoundedApplicationPoolExecutor("TiadReadExecutor", 1, false);
    }

    @Override
    public void execute(@NotNull Runnable command) {
        myBackend.execute(command);
    }

    @NotNull
    public static TiadReadExecutor getInstance() {
        return ourInstance;
    }
}
