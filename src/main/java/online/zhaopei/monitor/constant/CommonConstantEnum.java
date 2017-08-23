package online.zhaopei.monitor.constant;

public enum CommonConstantEnum {

	ALERT_MEM("1"),
	ALERT_CPU("2"),
	ALERT_TIMEOUT("4"),
	;
	
	private String value;
	
	private CommonConstantEnum(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}
	
	
}
