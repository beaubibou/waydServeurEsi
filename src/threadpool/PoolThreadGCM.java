package threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PoolThreadGCM {
public static ThreadPoolExecutor poolThread=new ThreadPoolExecutor(5, 5, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100));
	static{
		poolThread.setThreadFactory(Executors.defaultThreadFactory());
		poolThread.prestartAllCoreThreads();
	}
}
