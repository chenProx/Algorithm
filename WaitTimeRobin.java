package com.taotao.pojo;

import java.util.LinkedList;

public class WaitTimeRobin {

	public static double TaskScheduling(int[] arriveTime, int[] runTime, int timeSpan) {
		LinkedList<Integer> waitQueue = new LinkedList<Integer>();// 等待队列
																	// 存放还没运行的任务
		LinkedList<Integer> taskQueue = new LinkedList<Integer>();// 任务队列
																	// 存放正在运行的任务
		int currentTime = 0;
		int taskCount = arriveTime.length;// 任务个数
		int[] predictEndTime = new int[taskCount];// 预计结束的时间
		int[] actualEndTime = new int[taskCount];// 实际结束的时间

		for (int i = 0; i < taskCount; i++) {
			predictEndTime[i] = arriveTime[i] + runTime[i];
			waitQueue.add(i);
		}

		while (waitQueue.size() != 0 || taskQueue.size() != 0) {
			for (int i : waitQueue) {
				if (arriveTime[i] <= currentTime) {
					taskQueue.add(i);// 如果有任务到来，则把该任务从等待队列转到任务队列
					waitQueue.remove(i);
				}
				break;
			}
			if (waitQueue.size() > 0 && taskQueue.size() == 0)// 正在运行的任务队列里没有任务，等待队列里还有任务，但是任务还没来
			{
				currentTime++;
			}
			while (!taskQueue.isEmpty())// 依次处理任务队列的任务
			{
				int runTask = taskQueue.peek();
				if (runTime[runTask] > timeSpan)// 该任务剩余运行时间大于调度时间
				{
					currentTime += timeSpan;
					runTime[runTask] -= timeSpan;
				} else// 该任务剩余运行时间小于于调度时间
				{
					currentTime += runTime[runTask];
					runTime[runTask] = 0;
					actualEndTime[runTask] = currentTime;// 记录下该任务实际完成时间
				}
				// 在下面可能有add操作会导致执行队列迭代器失效，故先保存下来
				taskQueue.remove(Integer.valueOf(runTask));// 当前任务已经运行过，从队头移除
				for (int waitTask : waitQueue) {
					if (arriveTime[waitTask] <= currentTime) {
						taskQueue.add(waitTask);// 这里会导致执行队列迭代器失效
						waitQueue.remove(Integer.valueOf(waitTask));
					}
					break;

				}
				if (runTime[runTask] > 0)// 当前任务还没运行完
				{
					taskQueue.add(runTask);// 添加到任务队列后面
				}

			}
		}

		double totalWaitTime = 0;
		for (int i = 0; i < taskCount; i++) {
			totalWaitTime = totalWaitTime + actualEndTime[i] - predictEndTime[i];// 等待时间
																					// =
																					// 实际结束时间-
																					// 预计结束时间
		}

		return totalWaitTime / taskCount;
	}

	public static void main(String[] args) {
		// int[] ArriveTime = {0 ,1, 3, 9};
		// int[] RunTime = {2, 1, 7, 5};
		// int timeSpan = 2;
		int[] ArriveTime = { 0, 1, 4 };
		int[] RunTime = { 5, 2, 3 };
		int timeSpan = 3;
		System.out.println(TaskScheduling(ArriveTime, RunTime, timeSpan));
	}

}

class Atom {
	public int index;
}
