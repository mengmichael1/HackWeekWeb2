package proj1;

public class Info {
	private long tid;
	private double lat;
	private double lon;
	private long iid;
	private String address;
	private String name;
	
	public Info() {
		
	}
	
	public Info(long tid, double lat, double lon, long iid, String address, String name) {
		this.tid = tid;
		this.lat = lat;
		this.lon = lon;
		this.iid = iid;
		this.address = address;
		this.name = name;
	}
	
	public long getTid() {
		return tid;
	}
	
	public void setTid(long tid) {
		this.tid = tid;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public long getIid() {
		return iid;
	}

	public void setIid(long iid) {
		this.iid = iid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}

