/*
 * Copyright (C) 2020 Charlotte Skye.
 *
 * This file is part of KNSTE.
 *
 * KNSTE is dually released under the GNU Lesser General Public License,
 * Version 3 or later, or the Mozilla Public License 2.0.
 */

package tf.lotte.knste.net.tcp

import tf.lotte.knste.ByteString
import tf.lotte.knste.exc.ClosedException
import tf.lotte.knste.exc.SocketException
import tf.lotte.knste.net.socket.StandardSocketOption
import tf.lotte.knste.system.FD
import tf.lotte.knste.system.Syscall
import tf.lotte.knste.util.Unsafe

/**
 * Implements a TCP socket on Linux.
 */
internal class LinuxTcpSocket : TcpClientSocket {
    // set to true when connected, never unset
    private var initialIsConnected: Boolean = false

    private var fd: FD = -1

    @OptIn(Unsafe::class)
    override fun connect(address: TcpSocketAddress) {
        if (initialIsConnected) TODO("Appropriate error")

        // naiive algorithm
        // TODO: Maybe throw a nicer error?
        val iterator = address.iterator()
        for (info in address) {
            val socket = Syscall.socket(info.family, info.type, info.protocol)
            val connected = Syscall.connect(socket, info)

            // success
            if (connected) {
                initialIsConnected = true
                // TODO: An actual connected property
                return
            }

            if (!iterator.hasNext()) {
                throw SocketException(message = "All attempts at connecting to $address failed")
            }
        }
    }

    @OptIn(Unsafe::class)
    override fun close() {
        if (!initialIsConnected) return
        Syscall.close(fd)
    }

    override fun <T> getSocketOption(option: StandardSocketOption<T>): T {
        TODO("not implemented")
    }

    override fun <T> setSocketOption(option: StandardSocketOption<T>, value: T) {
        TODO("not implemented")
    }

    override fun sendEof() {
        TODO("not implemented")
    }

    @OptIn(Unsafe::class)
    override fun readUpTo(bytes: Long): ByteString? {
        if (!initialIsConnected) throw ClosedException("This socket is not connected yet")

        val buffer = ByteArray(bytes.toInt())
        val size = Syscall.recv(fd, buffer)
        // EOF
        if (size == 0) return null

        return if (size == buffer.size) {
            ByteString.fromUncopied(buffer)
        } else {
            // ugh
            val copy = buffer.copyOfRange(0, size)
            ByteString.fromUncopied(copy)
        }
    }

    @OptIn(Unsafe::class)
    override fun writeAll(bs: ByteString) {
        if (!initialIsConnected) throw ClosedException("This socket is not connected yet")
        val unwrapped = bs.unwrap()
        Syscall.send(fd, unwrapped)
    }
}
