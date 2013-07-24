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
package org.spout.physics.collision.narrowphase;

import org.spout.math.vector.Vector3;
import org.spout.physics.collision.ContactInfo;
import org.spout.physics.collision.shape.CollisionShape;
import org.spout.physics.collision.shape.SphereShape;
import org.spout.physics.math.Transform;

/**
 * This class is used to compute the narrow-phase collision detection between two sphere shaped
 * collision volumes.
 */
public class SphereVsSphereAlgorithm extends NarrowPhaseAlgorithm {
	@Override
	public boolean testCollision(CollisionShape collisionShape1, Transform transform1,
								 CollisionShape collisionShape2, Transform transform2,
								 ContactInfo contactInfo) {
		final SphereShape sphereShape1 = (SphereShape) collisionShape1;
		final SphereShape sphereShape2 = (SphereShape) collisionShape2;

		final Vector3 vectorBetweenCenters = transform2.getPosition().sub(transform1.getPosition());
		final float squaredDistanceBetweenCenters = vectorBetweenCenters.lengthSquared();
		final float sumRadius = sphereShape1.getRadius() + sphereShape2.getRadius();
		if (squaredDistanceBetweenCenters <= sumRadius * sumRadius) {
			final Vector3 centerSphere2InBody1LocalSpace = Transform.multiply(transform1.inverse(), transform2.getPosition());
			final Vector3 centerSphere1InBody2LocalSpace = Transform.multiply(transform2.inverse(), transform1.getPosition());
			final Vector3 intersectionOnBody1 =  centerSphere2InBody1LocalSpace.normalize().mul(sphereShape2.getRadius());
			final Vector3 intersectionOnBody2 = centerSphere1InBody2LocalSpace.normalize().mul(sphereShape2.getRadius());
			final float penetrationDepth = sumRadius - (float) Math.sqrt(squaredDistanceBetweenCenters);
			contactInfo.set(vectorBetweenCenters.normalize(), penetrationDepth, intersectionOnBody1, intersectionOnBody2);
			return true;
		}
		return false;
	}
}
