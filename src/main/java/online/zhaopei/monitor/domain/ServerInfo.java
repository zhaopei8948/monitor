package online.zhaopei.monitor.domain;

import java.io.Serializable;
import java.util.List;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.Swap;

public class ServerInfo implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7579164185068181479L;

	private Server server;
	
	private Mem mem;
	
	private Swap swap;
	
	private CpuPerc cpuPerc;
	
	private List<Cpu> cpuList;
	
	private OperatingSystem operatingSystem;
	
	private List<FileSystemInfo> fileSystemInfoList;
	
	private List<NetInterfaceInfo> netInterfaceInfoList;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public Mem getMem() {
		return mem;
	}

	public void setMem(Mem mem) {
		this.mem = mem;
	}

	public Swap getSwap() {
		return swap;
	}

	public void setSwap(Swap swap) {
		this.swap = swap;
	}

	public CpuPerc getCpuPerc() {
		return cpuPerc;
	}

	public void setCpuPerc(CpuPerc cpuPerc) {
		this.cpuPerc = cpuPerc;
	}

	public List<Cpu> getCpuList() {
		return cpuList;
	}

	public void setCpuList(List<Cpu> cpuList) {
		this.cpuList = cpuList;
	}

	public OperatingSystem getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(OperatingSystem operatingSystem) {
		this.operatingSystem = operatingSystem;
	}


	public List<FileSystemInfo> getFileSystemInfoList() {
		return fileSystemInfoList;
	}

	public void setFileSystemInfoList(List<FileSystemInfo> fileSystemInfoList) {
		this.fileSystemInfoList = fileSystemInfoList;
	}

	public List<NetInterfaceInfo> getNetInterfaceInfoList() {
		return netInterfaceInfoList;
	}

	public void setNetInterfaceInfoList(List<NetInterfaceInfo> netInterfaceInfoList) {
		this.netInterfaceInfoList = netInterfaceInfoList;
	}
}
