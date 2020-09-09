/*
 * Copyright (C) 2020 Charlotte Skye.
 *
 * This file is part of KNSTE.
 *
 * KNSTE is dually released under the GNU Lesser General Public License,
 * Version 3 or later, or the Mozilla Public License 2.0.
 */

package tf.lotte.knste.util

// safe conversion
/**
 * Gets the upper byte of this Int.
 */
public inline val Int.upperByte: Int
    get() = ((this.toUInt() and 0xff000000u) shl 24).toInt()

/**
 * Gets the lower byte of this Int.
 */
public inline val Int.lowerByte: Int
    get() = (this and 0x000000ff)

/**
 * Gets the second byte of this Int.
 */
public inline val Int.byte2: Int
    get() = (this and 0x00ff0000) shl 16

/**
 * Gets the third byte of this Int.
 */
public inline val Int.byte3: Int
    get() = (this and 0x0000ff00) shl 8


/**
 * Decodes this int into a [ByteArray] in big endian mode.
 */
public inline fun Int.toByteArray(): ByteArray {
    return byteArrayOf(upperByte.toByte(), byte2.toByte(), byte3.toByte(), lowerByte.toByte())
}

/**
 * Gets the upper byte of this Long.
 */
public inline val Long.upperByte: Long
    get() = ((this.toULong()) and 0xFF00000000000000UL).toLong()

/**
 * Gets the lower byte of this Long.
 */
public inline val Long.lowerByte: Long
    get() = (this and 0x00000000000000FF)