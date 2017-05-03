package com.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;

/**
 * Executor and Error Handler for asynchronous tasks
 * @author anupama
 *
 */
public class AsynchTaskExecutorHandler implements AsyncTaskExecutor {
	    private AsyncTaskExecutor executor;
		private static final Logger logger = LoggerFactory.getLogger(AsynchTaskExecutorHandler.class);

	    public AsynchTaskExecutorHandler(AsyncTaskExecutor executor) {
	        this.executor = executor;
	    }

	    public void execute(Runnable task) {
	        executor.execute(createWrappedRunnable(task));
	    }

	    public void execute(Runnable task, long startTimeout) {
	        executor.execute(createWrappedRunnable(task), startTimeout);
	    }

	    public Future submit(Runnable task) {
	          return executor.submit(createWrappedRunnable(task));
	    }

	    public  Future submit(final Callable task) {
	    	return executor.submit(createCallable(task));
	    }

	    private  Callable createCallable(final Callable task) {
	          return new Callable() {
	              public Object call() throws Exception {
	                  try {
	                      return task.call();
	                  } catch (Exception ex) {
	                      handle(ex);
	                      throw ex;
	                  }
	              }
	          };
	      }

	      private Runnable createWrappedRunnable(final Runnable task) {
	          return new Runnable() {
	              public void run() {
	                  try {
	                      task.run();
	                  } catch (Exception ex) {
	                      handle(ex);
	                  }
	              }
	          };
	      }

	      private void handle(Exception ex) {
	    	  logger.error("Error during @Async execution: " + ex);
	    	  ex.printStackTrace();
	      }

}
