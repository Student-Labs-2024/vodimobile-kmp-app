package com.vodimobile.utils.file

import okio.SYSTEM

fun okio.Path.writeBytes(content: ByteArray) = okio.FileSystem.SYSTEM.write(this) { write(content) }

fun okio.Path.getName() =
    okio.FileSystem.SYSTEM.canonicalize(this).name

fun okio.Path.readBytes(): ByteArray = okio.FileSystem.SYSTEM.read(this) { return readByteArray() }
