package c.j.g.ray.simd.geo.math;

//column major
public class Matrix4f {

	public static final int M00 = 0, M01 = 04, M02 = 8, M03 = 12;
	public static final int M10 = 1, M11 = 05, M12 = 9, M13 = 13;
	public static final int M20 = 2, M21 = 06, M22 = 10, M23 = 14;
	public static final int M30 = 3, M31 = 07, M32 = 11, M33 = 15;

	private float[] m;

	public Matrix4f(float[] m) {
		this.m = m;
	}

	public Matrix4f(Matrix4f other) {
		this(new float[] { other.m[0], other.m[1], other.m[2], other.m[3],
				other.m[4], other.m[5], other.m[6], other.m[7], other.m[8],
				other.m[9], other.m[10], other.m[11], other.m[12], other.m[13],
				other.m[14], other.m[15] });
	}

	public Matrix4f(Vector3f origin, Quaternion quat) {

		this(new float[16]);

		final float scaleX = 1, scaleY = 1, scaleZ = 1;

		final float xs = quat.getX() * 2f, ys = quat.getY() * 2f, zs = quat
				.getZ() * 2f;
		final float wx = quat.getW() * xs, wy = quat.getW() * ys, wz = quat
				.getW() * zs;
		final float xx = quat.getX() * xs, xy = quat.getX() * ys, xz = quat
				.getX() * zs;
		final float yy = quat.getY() * ys, yz = quat.getY() * zs, zz = quat
				.getZ() * zs;

		m[M00] = scaleX * (1.0f - (yy + zz));
		m[M01] = scaleY * (xy - wz);
		m[M02] = scaleZ * (xz + wy);
		m[M03] = origin.getX();

		m[M10] = scaleX * (xy + wz);
		m[M11] = scaleY * (1.0f - (xx + zz));
		m[M12] = scaleZ * (yz - wx);
		m[M13] = origin.getY();

		m[M20] = scaleX * (xz - wy);
		m[M21] = scaleY * (yz + wx);
		m[M22] = scaleZ * (1.0f - (xx + yy));
		m[M23] = origin.getZ();

		// m[M30] = 0.f;
		// m[M31] = 0.f;
		// m[M32] = 0.f;
		m[M33] = 1.0f;

	}

	public float get(int m) {
		return this.m[m];
	}

	public float[] getM() {
		return m;
	}

	public Matrix4f mul(Matrix4f r) {
		float[] matb = r.m, tmp = new float[16];

		tmp[M00] = m[M00] * matb[M00] + m[M01] * matb[M10] + m[M02] * matb[M20]
				+ m[M03] * matb[M30];
		tmp[M01] = m[M00] * matb[M01] + m[M01] * matb[M11] + m[M02] * matb[M21]
				+ m[M03] * matb[M31];
		tmp[M02] = m[M00] * matb[M02] + m[M01] * matb[M12] + m[M02] * matb[M22]
				+ m[M03] * matb[M32];
		tmp[M03] = m[M00] * matb[M03] + m[M01] * matb[M13] + m[M02] * matb[M23]
				+ m[M03] * matb[M33];
		tmp[M10] = m[M10] * matb[M00] + m[M11] * matb[M10] + m[M12] * matb[M20]
				+ m[M13] * matb[M30];
		tmp[M11] = m[M10] * matb[M01] + m[M11] * matb[M11] + m[M12] * matb[M21]
				+ m[M13] * matb[M31];
		tmp[M12] = m[M10] * matb[M02] + m[M11] * matb[M12] + m[M12] * matb[M22]
				+ m[M13] * matb[M32];
		tmp[M13] = m[M10] * matb[M03] + m[M11] * matb[M13] + m[M12] * matb[M23]
				+ m[M13] * matb[M33];
		tmp[M20] = m[M20] * matb[M00] + m[M21] * matb[M10] + m[M22] * matb[M20]
				+ m[M23] * matb[M30];
		tmp[M21] = m[M20] * matb[M01] + m[M21] * matb[M11] + m[M22] * matb[M21]
				+ m[M23] * matb[M31];
		tmp[M22] = m[M20] * matb[M02] + m[M21] * matb[M12] + m[M22] * matb[M22]
				+ m[M23] * matb[M32];
		tmp[M23] = m[M20] * matb[M03] + m[M21] * matb[M13] + m[M22] * matb[M23]
				+ m[M23] * matb[M33];
		tmp[M30] = m[M30] * matb[M00] + m[M31] * matb[M10] + m[M32] * matb[M20]
				+ m[M33] * matb[M30];
		tmp[M31] = m[M30] * matb[M01] + m[M31] * matb[M11] + m[M32] * matb[M21]
				+ m[M33] * matb[M31];
		tmp[M32] = m[M30] * matb[M02] + m[M31] * matb[M12] + m[M32] * matb[M22]
				+ m[M33] * matb[M32];
		tmp[M33] = m[M30] * matb[M03] + m[M31] * matb[M13] + m[M32] * matb[M23]
				+ m[M33] * matb[M33];

		return new Matrix4f(tmp);
	}

	public void set(int m, float value) {
		this.m[m] = value;
	}

	public void setM(float[] m) {
		this.m = m;
	}
}