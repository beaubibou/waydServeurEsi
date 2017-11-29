package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class PoolThreadGCM {
	private static final Logger LOG = Logger.getLogger(PoolThreadGCM.class);

	public static ThreadPoolExecutor poolThread = new ThreadPoolExecutor(5, 5,
			5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
	static {
		LOG.info("Mise en route des threads GCM");
		poolThread.setThreadFactory(Executors.defaultThreadFactory());
		poolThread.prestartAllCoreThreads();
	}
}
