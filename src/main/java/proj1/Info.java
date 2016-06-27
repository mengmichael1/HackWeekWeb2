package proj1;

public class Info {
	private String tid;
	private String lat;
	private String lon;
	private String iid;
	private String address;
	private String name;
	
	public Info() {
		
	}
	
	public Info(String tid, String lat, String lon, String iid, String address, String name) {
		this.tid = tid;
		this.lat = lat;
		this.lon = lon;
		this.iid = iid;
		this.address = address;
		this.name = name;
	}
	
	public String getTid() {
		return tid;
	}
	
	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
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

