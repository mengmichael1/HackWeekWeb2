package proj1;

public class Info {
	private long tid;
	private double lat;
	private double lon;
	
	public Info() {
		
	}
	
	public Info(long tid, double lat, double lon) {
		this.tid = tid;
		this.lat = lat;
		this.lon = lon;
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
	
}

