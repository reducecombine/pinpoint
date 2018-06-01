# pinpoint

## Description

`Pinpoint` patches selected clojure.core functions (currently just one: `cast`), replicating their behavior except for the error-reporting part: their exception messages are improved with the _faulty value_.

So instead of:

`ClassCastException Cannot cast java.lang.String to java.lang.Number`

...you'll start getting:

`ExceptionInfo "Example" (java.lang.String) cannot be cast to java.lang.Number`

Often, seeing this culprit value will immediately give you a hint of what is actually wrong, saving you stacktrace deciphering or debugging sessions.

Let's say the faulty value is `[:a 1]`. Knowing that you're working in a piece of code concerned with maps and not vectors, the _pinpointed_ value will guide you back in the right direction.

For now only `cast` is patched. I might cover more as I identify more use cases.

## Responsible patching

* This library should be enabled in development environment only
* Ideally, you'll use its `component/Lifecycle` integration, so clojure.core is unpatched are you tear down the system
* If you read `pinpoint/core.clj`, you'll note that it asserts the actual source code that is being patched matches a specific known value. That way, we ensure feature parity between original and patched code.

## Notes

`Pinpoint` needs a Clojure runtime that hasn't been compiled with direct linking.

Accordingly you can use Clojure 1.7, or compile your own version with the flag set.

* https://dev.clojure.org/jira/browse/CLJ-1862
* https://github.com/technomancy/leiningen/issues/2437

## License

Copyright © 2018 vemv.net

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
