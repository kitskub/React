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
package org.spout.physics.body;

import org.spout.math.matrix.Matrix3;
import org.spout.math.vector.Vector3;
import org.spout.physics.collision.shape.CollisionShape;
import org.spout.physics.math.Transform;

/**
 * Represents a rigid body for the physics engine. A rigid body is a non-deformable body that has a
 * constant mass. This class inherits from the CollisionBody class.
 */
public abstract class RigidBody extends CollisionBody {
	private static final RigidBodyMaterial DEFAULT_MATERIAL = RigidBodyMaterial.asUnmodifiableMaterial(new RigidBodyMaterial());
	protected RigidBodyMaterial mMaterial = DEFAULT_MATERIAL;

	/**
	 * Constructs a new rigid body from its transform, collision shape and ID.
	 *
	 * @param transform The transform (position and orientation)
	 * @param collisionShape The collision shape
	 * @param id The ID
	 */
	protected RigidBody(Transform transform, CollisionShape collisionShape, int id) {
		super(transform, collisionShape, id);
	}

	/**
	 * Gets the mass of the body.
	 *
	 * @return The body's mass
	 */
	public abstract float getMass();

	/**
	 * Gets the inverse of the mass of the body.
	 *
	 * @return The inverse of the mass
	 */
	public abstract float getMassInverse();

	/**
	 * Gets the linear velocity of the body.
	 *
	 * @return The linear velocity
	 */
	public abstract Vector3 getLinearVelocity();

	/**
	 * Gets the angular velocity of the body.
	 *
	 * @return The angular velocity
	 */
	public abstract Vector3 getAngularVelocity();

	/**
	 * Gets the current external force on the body.
	 *
	 * @return The current external force
	 */
	public abstract Vector3 getExternalForce();

	/**
	 * Sets the current external force on the body.
	 *
	 * @param force The external force to set
	 */
	public abstract void setExternalForce(Vector3 force);

	/**
	 * Gets the current external torque on the body.
	 *
	 * @return The current external torque
	 */
	public abstract Vector3 getExternalTorque();

	/**
	 * Sets the current external torque on the body.
	 *
	 * @param torque The external torque to set
	 */
	public abstract void setExternalTorque(Vector3 torque);

	/**
	 * Gets the inverse of the inertia tensor in world coordinates. The inertia tensor I_w in world
	 * coordinates is computed with the local inverse inertia tensor I_b^-1 in body coordinates by I_w
	 * = R * I_b^-1 * R^T, where R is the rotation matrix (and R^T its transpose) of the current
	 * orientation quaternion of the body.
	 *
	 * @return The world inverse inertia tensor
	 */
	public abstract Matrix3 getInertiaTensorInverseWorld();

	/**
	 * Sets the rigid body's material.
	 *
	 * @param material The material to set
	 */
	public void setMaterial(RigidBodyMaterial material) {
		mMaterial = material;
	}

	/**
	 * Gets the rigid body's material.
	 *
	 * @return The material
	 */
	public RigidBodyMaterial getMaterial() {
		return mMaterial;
	}

	/**
	 * Gets the restitution for this body.
	 *
	 * @return The restitution
	 */
	public float getRestitution() {
		return mMaterial.getRestitution();
	}

	/**
	 * Gets the friction for this body.
	 *
	 * @return The friction
	 */
	public float getFriction() {
		return mMaterial.getFriction();
	}
}
