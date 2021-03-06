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
package org.spout.physics.engine;

import org.spout.physics.math.Vector3;

/**
 * Represents an impulse that we can apply to bodies in the contact or constraint solver.
 */
public class Impulse {
    private final Vector3 linearImpulseBody1;
    private final Vector3 linearImpulseBody2;
    private final Vector3 angularImpulseBody1;
    private final Vector3 angularImpulseBody2;

    /**
     * Constructs a new impulse from the linear and angular impulses on both bodies.
     *
     * @param linearImpulseBody1 The linear impulse on the first body
     * @param angularImpulseBody1 The linear impulse on the second body
     * @param linearImpulseBody2 The angular impulse on the first body
     * @param angularImpulseBody2 The angular impulse on the second body
     */
    public Impulse(Vector3 linearImpulseBody1, Vector3 angularImpulseBody1, Vector3 linearImpulseBody2, Vector3 angularImpulseBody2) {
        this.linearImpulseBody1 = linearImpulseBody1;
        this.angularImpulseBody1 = angularImpulseBody1;
        this.linearImpulseBody2 = linearImpulseBody2;
        this.angularImpulseBody2 = angularImpulseBody2;
    }

    /**
     * Returns the linear impulse on the first body.
     *
     * @return The linear impulse
     */
    public Vector3 getLinearImpulseFirstBody() {
        return linearImpulseBody1;
    }

    /**
     * Returns the linear impulse on the second body.
     *
     * @return The linear impulse
     */
    public Vector3 getLinearImpulseSecondBody() {
        return linearImpulseBody2;
    }

    /**
     * Returns the angular impulse on the first body.
     *
     * @return The angular impulse
     */
    public Vector3 getAngularImpulseFirstBody() {
        return angularImpulseBody1;
    }

    /**
     * Returns the angular impulse on the second body.
     *
     * @return The angular impulse
     */
    public Vector3 getAngularImpulseSecondBody() {
        return angularImpulseBody2;
    }
}
