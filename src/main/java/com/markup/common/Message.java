package com.markup.common;

import java.io.Serializable;

public class Message implements Serializable {
	private long missionId;
	private int seed;
	public final static int MIN_SEED = 1000;
	public final static int MAX_SEED = 20000;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (missionId ^ (missionId >>> 32));
		result = prime * result + seed;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (missionId != other.missionId)
			return false;
		if (seed != other.seed)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Message [missionId=" + missionId + ", seed=" + seed + "]";
	}
	
	public long getMissionId() {
		return missionId;
	}
	
	public void setMissionId(long missionId) {
		this.missionId = missionId;
	}
	
	public int getSeed() {
		return seed;
	}
	
	public void setSeed(int seed) {
		this.seed = seed;
	}
}
