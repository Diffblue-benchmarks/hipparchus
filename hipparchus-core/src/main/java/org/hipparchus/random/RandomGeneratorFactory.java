/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hipparchus.random;

import java.util.Random;

import org.hipparchus.exception.LocalizedCoreFormats;
import org.hipparchus.exception.MathIllegalArgumentException;

/**
 * Utilities for creating {@link RandomGenerator} instances.
 *
 */
public class RandomGeneratorFactory {
    /**
     * Class contains only static methods.
     */
    private RandomGeneratorFactory() {}

    /**
     * Creates a {@link RandomDataGenerator} instance that wraps a
     * {@link Random} instance.
     *
     * @param rng JDK {@link Random} instance that will generate the
     * the random data.
     * @return the given RNG, wrapped in a {@link RandomGenerator}.
     */
    public static RandomGenerator createRandomGenerator(final Random rng) {
        return new RandomGenerator() {
            /** {@inheritDoc} */
            @Override
            public void setSeed(int seed) {
                rng.setSeed((long) seed);
            }

            /** {@inheritDoc} */
            @Override
            public void setSeed(int[] seed) {
                rng.setSeed(convertToLong(seed));
            }

            /** {@inheritDoc} */
            @Override
            public void setSeed(long seed) {
                rng.setSeed(seed);
            }

            /** {@inheritDoc} */
            @Override
            public void nextBytes(byte[] bytes) {
                rng.nextBytes(bytes);
            }

            /** {@inheritDoc} */
            @Override
            public int nextInt() {
                return rng.nextInt();
            }

            /** {@inheritDoc} */
            @Override
            public int nextInt(int n) {
                if (n <= 0) {
                    throw new MathIllegalArgumentException(LocalizedCoreFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED,
                                                           n, 0);
                }
                return rng.nextInt(n);
            }

            /** {@inheritDoc} */
            @Override
            public long nextLong() {
                return rng.nextLong();
            }

            /** {@inheritDoc} */
            @Override
            public boolean nextBoolean() {
                return rng.nextBoolean();
            }

            /** {@inheritDoc} */
            @Override
            public float nextFloat() {
                return rng.nextFloat();
            }

            /** {@inheritDoc} */
            @Override
            public double nextDouble() {
                return rng.nextDouble();
            }

            /** {@inheritDoc} */
            @Override
            public double nextGaussian() {
                return rng.nextGaussian();
            }
        };
    }

    /**
     * Converts seed from one representation to another.
     *
     * @param seed Original seed.
     * @return the converted seed.
     */
    public static long convertToLong(int[] seed) {
        // The following number is the largest prime that fits
        // in 32 bits (i.e. 2^32 - 5).
        final long prime = 4294967291l;

        long combined = 0l;
        for (int s : seed) {
            combined = combined * prime + s;
        }

        return combined;
    }
}
