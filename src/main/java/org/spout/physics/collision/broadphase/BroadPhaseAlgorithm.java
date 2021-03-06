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
package org.spout.physics.collision.broadphase;

import org.spout.physics.body.CollisionBody;
import org.spout.physics.collision.CollisionDetection;
import org.spout.physics.collision.broadphase.PairManager.BodyPair;
import org.spout.physics.collision.shape.AABB;

/**
 * This class is an abstract class that represents an algorithm used to perform the broad-phase of a collision detection. The goal of the broad-phase algorithm is to compute the pair of bodies that
 * can collide. It's important to understand that the broad-phase doesn't only compute body pairs that can collide, but could those that don't collide but are very close. The goal of the broad-phase
 * is to remove pairs of body that cannot collide in order to reduce the quantity of bodies to be tested in the narrow-phase.
 */
public abstract class BroadPhaseAlgorithm {
    protected final PairManager mPairManager;
    protected final CollisionDetection mCollisionDetection;

    /**
     * Constructs a new broad-phase algorithm from the collision detection it's associated to.
     *
     * @param collisionDetection The collision detection
     */
    protected BroadPhaseAlgorithm(CollisionDetection collisionDetection) {
        mPairManager = new PairManager(collisionDetection);
        mCollisionDetection = collisionDetection;
    }

    /**
     * Notify the broad-phase about the addition of an object from the world.
     *
     * @param body The body that was added
     * @param aabb The body's AABB
     */
    public abstract void addObject(CollisionBody body, AABB aabb);

    /**
     * Notify the broad-phase about the removal of an object from the world.
     *
     * @param body The body that was removed
     */
    public abstract void removeObject(CollisionBody body);

    /**
     * Notify the broad-phase that the AABB of an object has changed.
     *
     * @param body The body who had his AABB change
     * @param aabb The body's new AABB
     */
    public abstract void updateObject(CollisionBody body, AABB aabb);

    /**
     * Returns the array of overlapping pairs managed by the pair manager, for iteration purposes. Note that the array returned contains trailing null elements.
     *
     * @return The array of overlapping pairs
     */
    public BodyPair[] getOverlappingPairs() {
        return mPairManager.getOverlappingPairs();
    }

    /**
     * Return the last overlapping pair (used to iterate over the overlapping pairs) or returns null if there are no overlapping pairs. Note that the array returned by {@link #getOverlappingPairs()}
     * contains trailing null elements.
     *
     * @return The last overlapping pair
     */
    public BodyPair getLastOverlappingPair() {
        return mPairManager.getLastOverlappingPair();
    }
}
