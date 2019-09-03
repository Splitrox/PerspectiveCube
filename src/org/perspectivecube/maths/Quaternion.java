package org.perspectivecube.maths;

public class Quaternion {
    public double x, y, z, w;
    public Vector3 e = new Vector3();

    public Quaternion(double w, double x, double y, double z) {
        this.set(w, x, y, z);
    }

    public Quaternion() {
        this.set(1f, 0f, 0f, 0f);
    }

    public Quaternion(Vector3 v) {
        e.x = v.x;
        e.y = v.y;
        e.z = v.z;

        ETQ();
    }

    public Quaternion(Quaternion q) {
        this.set(q);
    }

    public Quaternion set(double w, double x, double y, double z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
	public Quaternion set(Vector3 axis, double angle) {
		double s = (double) Math.sin(angle / 2);
		w = (double) Math.cos(angle / 2);
		x = axis.getX() * s;
		y = axis.getY() * s;
		z = axis.getZ() * s;
		return this;
	}

    public Quaternion set(Quaternion q) {
        this.w = q.w;
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        return this;
    }

    private void ETQ() {
        double t0 = (double) Math.cos(e.z * 0.5f);
        double t1 = (double) Math.sin(e.z * 0.5f);
        double t2 = (double) Math.cos(e.x * 0.5f);
        double t3 = (double) Math.sin(e.x * 0.5f);
        double t4 = (double) Math.cos(e.y * 0.5f);
        double t5 = (double) Math.sin(e.y * 0.5f);

        w = t0 * t2 * t4 + t1 * t3 * t5;
        x = t0 * t3 * t4 - t1 * t2 * t5;
        y = t0 * t2 * t5 + t1 * t3 * t4;
        z = t1 * t2 * t4 - t0 * t3 * t5;
    }

    public Quaternion normalize() {
        double norm = (double) Math.sqrt(x * x + y * y + z * z + w * w);
        w = w / norm;
        x = x / norm;
        y = y / norm;
        z = z / norm;
        return this;
    }

    public Quaternion conjugate() {
        return new Quaternion(w, -x, -y, -z);
    }

    public Vector3 eulerAngles() {
        Vector3 eulerAngles = new Vector3();

        double sqw = w * w;
        double sqx = x * x;
        double sqy = y * y;
        double sqz = z * z;

        eulerAngles.x = (double) Math.atan2(2.0 * (x * y + z * w), (sqx - sqy - sqz + sqw));
        eulerAngles.y = (double) Math.atan2(2.0 * (y * z + x * w), (-sqx - sqy + sqz + sqw));
        eulerAngles.z = (double) Math.sin(-2.0 * (x * z - y * w));

        return eulerAngles;
    }

    public Quaternion multiply(Quaternion qb) {
        Quaternion qa = this;
        
        Quaternion result = qb.clone();
        
        result.w = (qa.w * qb.w) - (qa.x * qb.x) - (qa.y * qb.y) - (qa.z * qb.z);
        result.x = (qa.x * qb.w) + (qa.w * qb.x) + (qa.y * qb.z) - (qa.z * qb.y);
        result.y = (qa.y * qb.w) + (qa.w * qb.y) + (qa.z * qb.x) - (qa.x * qb.z);
        result.z = (qa.z * qb.w) + (qa.w * qb.z) + (qa.x * qb.y) - (qa.y * qb.x);
        
        return result;
    }

    public Quaternion cross(Quaternion q) {
        Quaternion q1 = new Quaternion(this);
        Quaternion q2 = new Quaternion(q);

        q.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
        q.x = q1.w * q2.x + q1.x * q2.w + q1.y * q2.z - q1.z * q2.y;
        q.y = q1.w * q2.y + q1.y * q2.w + q1.z * q2.x - q1.x * q2.z;
        q.z = q1.w * q2.z + q1.z * q2.w + q1.x * q2.y - q1.y * q2.x;

        return q;
    }

    public Vector3 rotateVector(Vector3 v) {
        Quaternion q = new Quaternion(0, v.x, v.y, v.z);
        
        Quaternion inverse_self = conjugate();
        Quaternion cross_product = cross(q);
        
        q = cross_product.cross(inverse_self);
        v = new Vector3(q.x, q.y, q.z);
        
        return v;
    }

    /*public Quaternion calculateTwist(Vector3 direction) {
        Vector3 ra = new Vector3(x, y, z);
        Vector3 p = ra.proj(direction);
        Quaternion twist = new Quaternion(w, p.x, p.y, p.z);
        twist = twist.normalize();
        return twist;
    }*/

    public String toString() {
        return "qw," + String.valueOf(w) + ",\nqx," + String.valueOf(x) +
                ",\nqy," + String.valueOf(y) + ",\nqz," + String.valueOf(z);
    }
    
    public Quaternion clone() {
    	return new Quaternion(this);
    }

}