Tinlok
------

Tinlok is a library that fills in the "missing pieces" from the Kotlin standard library on native
platforms as well as adding useful optional extra libraries.

Tinlok is entirely re-implementations of common standard library features for Native desktop only.
It is not designed for the JVM, iOS or Android and support will not be added.

Why?
====

The Kotlin stdlib is very barebones. Kotlin is typically a guest language on somebody else's
virtual machine, so most Kotlin code is designed around integrating with that platform's core
functionality, sometimes with helper glue code to make it more natural to use in a Kotlin context.

Kotlin/Native on Desktop, however, is a host language and has no platform to work with aside from
the platform libraries (libc or Win32) and these libraries are not ergonomic to use from a Kotlin
context.

Where?
======

Tinlok is not currently available anywhere. Stay tuned.

Stability
=========

For all I care, use Tinlok in production. I make zero stability guarantees or remarks on being
"production ready".

