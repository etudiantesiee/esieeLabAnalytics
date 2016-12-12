package fr.esiee.pic.analytics.domain;

import java.io.Serializable;

/**
 * Stats
 * 
 * @author etudiant
 *
 */
public class Statistics implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1444211491426553516L;

	/**
	 * Nom de la stat
	 */
	private String counterName;
	
	/**
	 * Valeur de la stat
	 */
	private Integer counterValue;

	public Statistics(String counterName, Integer counterValue) {
		super();
		this.counterName = counterName;
		this.counterValue = counterValue;
	}

	public String getCounterName() {
		return counterName;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public Integer getCounterValue() {
		return counterValue;
	}

	public void setCounterValue(Integer counterValue) {
		this.counterValue = counterValue;
	}

	@Override
	public String toString() {
		return "Statistics [counterName=" + counterName + ", counterValue="
				+ counterValue + "]";
	}
}
