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
package org.spout.physics.collision.shape;

import org.spout.math.matrix.Matrix3;
import org.spout.math.vector.Vector3;
import org.spout.physics.ReactDefaults;
import org.spout.physics.math.Transform;

/**
 * Represents the collision shape associated with a body that is used during the narrow-phase
 * collision detection.
 */
public abstract class CollisionShape {
	protected final CollisionShapeType mType;

	/**
	 * Constructs a new collision shape from its type.
	 *
	 * @param type The type of the collision shape
	 */
	protected CollisionShape(CollisionShapeType type) {
		mType = type;
	}

	/**
	 * Gets the type of collision shape associated to this shape.
	 *
	 * @return The collision shape type
	 */
	public CollisionShapeType getType() {
		return mType;
	}

	/**
	 * Gets a local support point in a given direction with the object margin.
	 *
	 * @param direction The desired direction
	 * @return The local support point as a vector3
	 */
	public abstract Vector3 getLocalSupportPointWithMargin(Vector3 direction);

	/**
	 * Gets a local support point in a given direction without the object margin.
	 *
	 * @param direction The desired direction
	 * @return The local support point as a vector3
	 */
	public abstract Vector3 getLocalSupportPointWithoutMargin(Vector3 direction);

	/**
	 * Gets the local extents in x,y and z direction with the desired margin .
	 *
	 * @param margin The required margin
	 * @return The local extents
	 */
	public abstract Vector3 getLocalExtents(float margin);

	/**
	 * Gets the margin distance around the shape.
	 *
	 * @return The margin for the shape
	 */
	public abstract float getMargin();

	/**
	 * Computes the local inertia tensor of the collision shape for the mass. Stores the results in the
	 * passed matrix3x3.
	 * @param mass The mass of the shape
	 */
	public abstract Matrix3 computeLocalInertiaTensor(float mass);

	/**
	 * Update the AABB of a body using its collision shape.
	 *
	 * @param aabb The AABB to update
	 * @param transform The AABB's transform
	 */
	public void updateAABB(AABB aabb, Transform transform) {
		final Vector3 extents = getLocalExtents(ReactDefaults.OBJECT_MARGIN);
		final Matrix3 worldAxis = transform.getOrientation().toMatrix3().abs();
		final Vector3 worldExtents = new Vector3(
				worldAxis.getColumn(0).dot(extents),
				worldAxis.getColumn(1).dot(extents),
				worldAxis.getColumn(2).dot(extents));
		final Vector3 minCoordinates = transform.getPosition().sub(worldExtents);
		final Vector3 maxCoordinates = transform.getPosition().add(worldExtents);
		aabb.setMin(minCoordinates);
		aabb.setMax(maxCoordinates);
	}

	/**
	 * An enumeration of the possible collision shape (box, sphere, cone and cylinder).
	 */
	public static enum CollisionShapeType {
		BOX, SPHERE, CONE, CYLINDER;
	}
}
