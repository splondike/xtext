/*
 * Copyright (C) 2009 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.common.util.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Utilities necessary for working with libraries that supply plain {@link
 * Future} instances.
 *
 * @author Sven Mawson
 * @since 10.0 (replacing {@code Futures.makeListenable}, which
 *     existed in 1.0)
 */
@Beta
public
final class JdkFutureAdapters {
  /**
   * Assigns a thread to the given {@link Future} to provide {@link
   * ListenableFuture} functionality.
   *
   * <p><b>Warning:</b> If the input future does not already implement {@link
   * ListenableFuture}, the returned future will emulate {@link
   * ListenableFuture#addListener} by taking a thread from an internal,
   * unbounded pool at the first call to {@code addListener} and holding it
   * until the future is {@linkplain Future#isDone() done}.
   *
   * <p>Prefer to create {@code ListenableFuture} instances with {@link
   * SettableFuture}, {@link MoreExecutors#listeningDecorator(
   * java.util.concurrent.ExecutorService)}, {@link ListenableFutureTask},
   * {@link AbstractFuture}, and other utilities over creating plain {@code
   * Future} instances to be upgraded to {@code ListenableFuture} after the
   * fact.
   */
  public static <V> ListenableFuture<V> listenInPoolThread(
      Future<V> future) {
    if (future instanceof ListenableFuture<?>) {
      return (ListenableFuture<V>) future;
    }
    return new ListenableFutureAdapter<V>(future);
  }

  @VisibleForTesting
  static <V> ListenableFuture<V> listenInPoolThread(
      Future<V> future, Executor executor) {
    checkNotNull(executor);
    if (future instanceof ListenableFuture<?>) {
      return (ListenableFuture<V>) future;
    }
    return new ListenableFutureAdapter<V>(future, executor);
  }

  /**
   * An adapter to turn a {@link Future} into a {@link ListenableFuture}.  This
   * will wait on the future to finish, and when it completes, run the
   * listeners.  This implementation will wait on the source future
   * indefinitely, so if the source future never completes, the adapter will
   * never complete either.
   *
   * <p>If the delegate future is interrupted or throws an unexpected unchecked
   * exception, the listeners will not be invoked.
   */
  private static class ListenableFutureAdapter<V> extends ForwardingFuture<V>
      implements ListenableFuture<V> {

    private static final ThreadFactory threadFactory =
        new ThreadFactoryBuilder()
            .setNameFormat("ListenableFutureAdapter-thread-%d")
            .build();
    private static final Executor defaultAdapterExecutor =
        Executors.newCachedThreadPool(threadFactory);

    private final Executor adapterExecutor;

    // The execution list to hold our listeners.
    private final ExecutionList executionList = new ExecutionList();

    // This allows us to only start up a thread waiting on the delegate future
    // when the first listener is added.
    private final AtomicBoolean hasListeners = new AtomicBoolean(false);

    // The delegate future.
    private final Future<V> delegate;

    ListenableFutureAdapter(Future<V> delegate) {
      this(delegate, defaultAdapterExecutor);
    }

    ListenableFutureAdapter(Future<V> delegate, Executor adapterExecutor) {
      this.delegate = checkNotNull(delegate);
      this.adapterExecutor = checkNotNull(adapterExecutor);
    }

    @Override
    protected Future<V> delegate() {
      return delegate;
    }

    @Override
    public void addListener(Runnable listener, Executor exec) {
      executionList.add(listener, exec);

      // When a listener is first added, we run a task that will wait for
      // the delegate to finish, and when it is done will run the listeners.
      if (hasListeners.compareAndSet(false, true)) {
        if (delegate.isDone()) {
          // If the delegate is already done, run the execution list
          // immediately on the current thread.
          executionList.execute();
          return;
        }

        adapterExecutor.execute(new Runnable() {
          @Override
          public void run() {
            try {
              delegate.get();
            } catch (Error e) {
              throw e;
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
              // Threads from our private pool are never interrupted.
              throw new AssertionError(e);
            } catch (Throwable e) {
              // ExecutionException / CancellationException / RuntimeException
              // The task is done, run the listeners.
            }
            executionList.execute();
          }
        });
      }
    }
  }

  private JdkFutureAdapters() {}
}