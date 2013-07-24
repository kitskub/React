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

import org.spout.math.GenericMath;
import org.spout.math.vector.Vector3;
import org.spout.math.imaginary.Quaternion;
import org.spout.math.matrix.Matrix3;

/**
 * Represents a position and an orientation in 3D. It can also be seen as representing a translation
 * and a rotation.
 */
public class Transform {
	private Vector3 mPosition;
	private Quaternion mOrientation;

	/**
	 * Default constructor. Position will be the zero vector and the rotation, quaternion identity.
	 */
	public Transform() {
		this(new Vector3(0, 0, 0), Quaternion.IDENTITY);
	}

	/**
	 * Constructs a new transform from the position as a vector3 and the orientation as a 3x3
	 * matrix.
	 *
	 * @param position The position
	 * @param orientation The orientation
	 */
	public Transform(Vector3 position, Matrix3 orientation) {
		this(position, Quaternion.fromRotationMatrix(orientation));
	}

	/**
	 * Constructs a new transform from the position as a vector3 and the orientation as a
	 * quaternion.
	 *
	 * @param position The position
	 * @param orientation The orientation
	 */
	public Transform(Vector3 position, Quaternion orientation) {
		this.mPosition = position;
		this.mOrientation = orientation;
	}

	/**
	 * Copy constructor.
	 *
	 * @param transform The transform to copy
	 */
	public Transform(Transform transform) {
		this(transform.getPosition(), transform.getOrientation());
	}

	/**
	 * Gets the position component of this transform as a vector3.
	 *
	 * @return The position
	 */
	public Vector3 getPosition() {
		return mPosition;
	}

	/**
	 * Gets the orientation component of this transform as a quaternion.
	 *
	 * @return The orientation
	 */
	public Quaternion getOrientation() {
		return mOrientation;
	}

	/**
	 * Sets the position component of this transform to the desired vector3.
	 *
	 * @param position The position to set
	 */
	public void setPosition(Vector3 position) {
		mPosition = position;
	}

	/**
	 * Sets the orientation component of this transform to the desired quaternion.
	 *
	 * @param orientation The position to set
	 */
	public void setOrientation(Quaternion orientation) {
		mOrientation = orientation;
	}

	/**
	 * Sets the position and orientation of this transform to those of the provided transform.
	 *
	 * @param transform The transform to copy the position and orientation from
	 */
	public Transform set(Transform transform) {
		setPosition(transform.getPosition());
		setOrientation(transform.getOrientation());
		return this;
	}

	/**
	 * Sets this transform to identity. The vector is set to the zero vector, and the quaternion, to
	 * the identity quaternion.
	 */
	public void setToIdentity() {
		mPosition = Vector3.ZERO;
		mOrientation = Quaternion.IDENTITY;
	}

	/**
	 * Inverses the rotation and position of this transform and returns it as a new one.
	 *
	 * @return The transform which is the inverse of this one
	 */
	public Transform inverse() {
		final Quaternion invQuaternion = mOrientation.invert();
		final Matrix3 invMatrix = invQuaternion.toMatrix3();
		return new Transform(invMatrix.transform(mPosition.negate()), invQuaternion);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 11 * hash + mPosition.hashCode() ;
		hash = 11 * hash +  mOrientation.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transform)) {
			return false;
		}
		final Transform other = (Transform) obj;
		if (mPosition != other.mPosition && !mPosition.equals(other.mPosition)) {
			return false;
		}
		if (mOrientation != other.mOrientation && !mOrientation.equals(other.mOrientation)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Transform{position= " + mPosition + ", orientation= " + mOrientation + "}";
	}

	/**
	 * Returns a new identity transform. That is, a transform with the position as the zero vector
	 * and the orientation as the identity quaternion.
	 *
	 * @return A new identity transform
	 */
	public static Transform identity() {
		return new Transform(new Vector3(0, 0, 0), Quaternion.IDENTITY);
	}

	/**
	 * Multiplies the transform by a vector3 and returns the result as a new vector3.
	 *
	 * @param transform The transform
	 * @param vector The vector
	 * @return The result of the multiplication of the transform by a vector3 as a new vector3
	 */
	public static Vector3 multiply(Transform transform, Vector3 vector) {
		return transform.getOrientation().toMatrix3().transform(vector).add(transform.getPosition());
	}

	/**
	 * Multiplies the first transform by the second one and returns the result as a new transform.
	 *
	 * @param transform1 The first transform
	 * @param transform2 The second transform
	 * @return The result of the multiplication of the two transforms as a new transform
	 */
	public static Transform multiply(Transform transform1, Transform transform2) {
		return new Transform(transform1.getPosition().add(transform1.getOrientation().toMatrix3().transform(transform2.getPosition())),
				transform1.getOrientation().mul(transform2.getOrientation()));
	}

	/**
	 * Interpolates a transform between two other.
	 *
	 * @param transform1 The first transform
	 * @param transform2 The second transform
	 * @param percent The percent for the interpolation, between 0 and 1 inclusively
	 * @return The interpolated transform
	 */
	public static Transform interpolateTransforms(Transform transform1, Transform transform2, float percent) {
		final Vector3 v1 = transform1.getPosition().mul(1 - percent);
		final Vector3 v2 = transform2.getPosition().mul(percent);
		final Quaternion interOrientation = GenericMath.slerp(transform1.getOrientation(), transform2.getOrientation(), percent);
		return new Transform(v1.add(v2), interOrientation);
	}
}
