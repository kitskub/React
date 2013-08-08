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
}
