package com.hf.idgen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Twitter snowflake
 *
 */
public class IdWorker {
	protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

	private long workerId;
	private long datacenterId;
	private long sequence = 0L;

	private long twepoch = 1288834974657L;
	// 机器标识位数
	private long workerIdBits = 5L;
	// 数据中心标识位数
	private long datacenterIdBits = 5L;
	// 机器ID最大值
	private long maxWorkerId = -1L ^ (-1L << workerIdBits);
	// 数据中心ID最大值
	private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
	// 毫秒内自增位
	private long sequenceBits = 12L;

	// 机器ID偏左移12位
	private long workerIdShift = sequenceBits;
	// 数据中心ID左移17位
	private long datacenterIdShift = sequenceBits + workerIdBits;
	// 时间毫秒左移22位
	private long timestampLeftShift = sequenceBits + workerIdBits
			+ datacenterIdBits;
	private long sequenceMask = -1L ^ (-1L << sequenceBits);

	private long lastTimestamp = -1L;

	public IdWorker(long workerId, long datacenterId) {
		// sanity check for workerId
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					maxWorkerId));
		}
		if (datacenterId > maxDatacenterId || datacenterId < 0) {
			throw new IllegalArgumentException(String.format(
					"datacenter Id can't be greater than %d or less than 0",
					maxDatacenterId));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
		LOG.info(String
				.format("worker starting. timestamp left shift %d, datacenter id bits %d, worker id bits %d, sequence bits %d, workerid %d",
						timestampLeftShift, datacenterIdBits, workerIdBits,
						sequenceBits, workerId));
	}

	public synchronized long nextId() {
		long timestamp = timeGen();

		// 时间错误
		if (timestamp < lastTimestamp) {
			LOG.error(String.format(
					"clock is moving backwards.  Rejecting requests until %d.",
					lastTimestamp));
			throw new RuntimeException(
					String.format(
							"Clock moved backwards.  Refusing to generate id for %d milliseconds",
							lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & sequenceMask;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}

		lastTimestamp = timestamp;

		// ID偏移组合生成最终的ID，并返回ID
		return ((timestamp - twepoch) << timestampLeftShift)
				| (datacenterId << datacenterIdShift)
				| (workerId << workerIdShift) | sequence;
	}

	// 等待下一个毫秒的到来
	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	protected long timeGen() {
		return System.currentTimeMillis();
	}
}
