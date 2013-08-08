/*
 * This file is part of React.
 *
 * Copyright (c) 2013 Spout LLC <http://www.spout.org/>
 * React is licensed under the Spout License Version 1.
 *
 * React is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 *
 * In addition, 180 days after any changes are published, you can use the
 * software, incorporating those changes, under the terms of the MIT license,
 * as described in the Spout License Version 1.
 *
 * React is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for
 * more details.
 *
 * You should have received a copy of the GNU Lesser General Public License,
 * the MIT license and the Spout License Version 1 along with this program.
 * If not, see <http://www.gnu.org/licenses/> for the GNU Lesser General Public
 * License and see <http://spout.in/licensev1> for the full license, including
 * the MIT license.
 */
package org.spout.physics.math;

import org.spout.math.imaginary.Quaternion;
import org.spout.math.vector.Vector3;
import org.spout.physics.ReactDefaults;

/**
 * Various mathematical functions.
 */
public class Mathematics {
	/**
	 * Returns true if the values a and b are approximately equal, using {@link ReactDefaults#MACHINE_EPSILON} as the acceptable error. Returns false if the values are not approximately equal.
	 *
	 * @param a The first value
	 * @param b The second value
	 * @return Whether or not the values are approximately equal
	 */
	public static boolean approxEquals(float a, float b) {
		return approxEquals(a, b, ReactDefaults.MACHINE_EPSILON);
	}

	/**
	 * Returns true if the values a and b are approximately equal, using the provided acceptable error. Returns false if the values are not approximately equal.
	 *
	 * @param a The first value
	 * @param b The second value
	 * @param epsilon The acceptable error
	 * @return Whether or not the values are approximately equal
	 */
	public static boolean approxEquals(float a, float b, float epsilon) {
		float difference = a - b;
		return difference < epsilon && difference > -epsilon;
	}

	/**
	 * X_AXIS, represents the x axis in the vector. Value of 0
	 */
	public static final int X_AXIS = 0;
	/**
	 * Y_AXIS, represents the y axis in the vector. Value of 1
	 */
	public static final int Y_AXIS = 1;
	/**
	 * Z_AXIS, represents the z axis in the vector. Value of 2
	 */
	public static final int Z_AXIS = 2;

	/**
	 * Return the axis with the minimal value
	 *
	 * @return {@link int} axis with minimal value
	 */
	public static int getMinAxis(Vector3 v) {
		return v.getX() < v.getY() ? (v.getX() < v.getZ() ? X_AXIS : Z_AXIS) : (v.getY() < v.getZ() ? Y_AXIS : Z_AXIS);
	}

	/**
	 * Return an orthogonal vector of this vector
	 *
	 * @return an orthogonal {@link Vector3} of the current vector
	 */
	public static Vector3 getOneUnitOrthogonalVector(Vector3 v) {
		if (v.length() <= ReactDefaults.MACHINE_EPSILON) {
			throw new IllegalArgumentException("Cannot normalize the zero vector");
		}
		final Vector3 vectorAbs = v.abs();
		final int minElement = getMinAxis(v);
		if (minElement == 0) {
			return new Vector3(0, v.getZ() * -1, v.getY()).div((float) Math.sqrt(v.getY() * v.getY() + v.getZ() * v.getZ()));
		} else if (minElement == 1) {
			return new Vector3(v.getZ() * -1, 0, v.getX()).div((float) Math.sqrt(v.getX() * v.getX() + v.getZ() * v.getZ()));
		} else {
			return new Vector3(v.getY() * -1, v.getX(), 0).div((float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY()));
		}
	}

	/**
	 * Constructs a quaternion from the rotation element of the matrix.
	 *
	 * @param matrix The matrix to get the rotation element from
	 */
	public static Quaternion QuaternionFromMatrix(Matrix3x3 matrix) {
		final float trace = matrix.getTrace();
		float x;
		float y;
		float z;
		float w;
		if (trace < 0) {
			if (matrix.get(1, 1) > matrix.get(0, 0)) {
				if (matrix.get(2, 2) > matrix.get(1, 1)) {
					final float r = (float) Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1);
					final float s = 0.5f / r;
					x = (matrix.get(2, 0) + matrix.get(0, 2)) * s;
					y = (matrix.get(1, 2) + matrix.get(2, 1)) * s;
					z = 0.5f * r;
					w = (matrix.get(1, 0) - matrix.get(0, 1)) * s;
				} else {
					final float r = (float) Math.sqrt(matrix.get(1, 1) - matrix.get(2, 2) - matrix.get(0, 0) + 1);
					final float s = 0.5f / r;
					x = (matrix.get(0, 1) + matrix.get(1, 0)) * s;
					y = 0.5f * r;
					z = (matrix.get(1, 2) + matrix.get(2, 1)) * s;
					w = (matrix.get(0, 2) - matrix.get(2, 0)) * s;
				}
			} else if (matrix.get(2, 2) > matrix.get(0, 0)) {
				final float r = (float) Math.sqrt(matrix.get(2, 2) - matrix.get(0, 0) - matrix.get(1, 1) + 1);
				final float s = 0.5f / r;
				x = (matrix.get(2, 0) + matrix.get(0, 2)) * s;
				y = (matrix.get(1, 2) + matrix.get(2, 1)) * s;
				z = 0.5f * r;
				w = (matrix.get(1, 0) - matrix.get(0, 1)) * s;
			} else {
				final float r = (float) Math.sqrt(matrix.get(0, 0) - matrix.get(1, 1) - matrix.get(2, 2) + 1);
				final float s = 0.5f / r;
				x = 0.5f * r;
				y = (matrix.get(0, 1) + matrix.get(1, 0)) * s;
				z = (matrix.get(2, 0) - matrix.get(0, 2)) * s;
				w = (matrix.get(2, 1) - matrix.get(1, 2)) * s;
			}
		} else {
			final float r = (float) Math.sqrt(trace + 1);
			final float s = 0.5f / r;
			x = (matrix.get(2, 1) - matrix.get(1, 2)) * s;
			y = (matrix.get(0, 2) - matrix.get(2, 0)) * s;
			z = (matrix.get(1, 0) - matrix.get(0, 1)) * s;
			w = 0.5f * r;
		}
		return new Quaternion(x, y, z, w);
	}

	/**
	 * Gets the 3x3 rotation matrix for this quaternion.
	 *
	 * @return The rotation matrix3x3
	 */
	public static Matrix3x3 MatrixFromQuaternion(Quaternion q) {
		final float x = q.getX();
		final float y = q.getY();
		final float z = q.getZ();
		final float w = q.getW();

		final float nQ = x * x + y * y + z * z + w * w;
		final float s;
		if (nQ > 0.0) {
			s = 2 / nQ;
		} else {
			s = 0;
		}
		final float xs = x * s;
		final float ys = y * s;
		final float zs = z * s;
		final float wxs = w * xs;
		final float wys = w * ys;
		final float wzs = w * zs;
		final float xxs = x * xs;
		final float xys = x * ys;
		final float xzs = x * zs;
		final float yys = y * ys;
		final float yzs = y * zs;
		final float zzs = z * zs;
		return new Matrix3x3(
				1 - yys - zzs, xys - wzs, xzs + wys,
				xys + wzs, 1 - xxs - zzs, yzs - wxs,
				xzs - wys, yzs + wxs, 1 - xxs - yys);
	}

	/**
	 * Interpolates a quaternion between two others using spherical linear interpolation.
	 *
	 * @param quaternion1 The first quaternion
	 * @param quaternion2 The second quaternion
	 * @param percent The percent for the interpolation, between 0 and 1 inclusively
	 * @return The interpolated quaternion
	 */
	public static Quaternion slerp(Quaternion quaternion1, Quaternion quaternion2, float percent) {
		if (percent < 0 && percent > 1) {
			throw new IllegalArgumentException("\"percent\" must be greater than zero and smaller than one");
		}
		final float invert;
		float cosineTheta = quaternion1.dot(quaternion2);
		if (cosineTheta < 0) {
			cosineTheta = -cosineTheta;
			invert = -1;
		} else {
			invert = 1;
		}
		final float epsilon = 0.00001f;
		if (1 - cosineTheta < epsilon) {
			return quaternion1.mul(1 - percent).add(quaternion2.mul(percent * invert));
		}
		final float theta = (float) Math.acos(cosineTheta);
		final float sineTheta = (float) Math.sin(theta);
		final float coeff1 = (float) Math.sin((1 - percent) * theta) / sineTheta;
		final float coeff2 = (float) Math.sin(percent * theta) / sineTheta * invert;
		return quaternion1.mul(coeff1).add(quaternion2.mul(coeff2));
	}	
}
