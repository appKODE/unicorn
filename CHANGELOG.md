# Changelog

## 2.3.0

* **New Maven groupId: `ru.kode`.** Use `ru.kode:unicorn2:2.3.0`; versions up to 2.2.0 stay at `ru.dimsuz:unicorn2`. The Kotlin package `ru.dimsuz.unicorn2` is unchanged.
* Kotlin 2.3.20, kotlinx.coroutines 1.10.2.
* Minimum JVM version is now 11 (was 1.8).
* Apple targets: `iosArm64`, `iosSimulatorArm64`, `iosX64`, `macosArm64`, `macosX64`. `macosX64` is deprecated in Kotlin 2.3 and will be dropped once Kotlin removes it.
* Published to Maven Central via the Central Portal; releases are automated with GitHub Actions.

## 2.2.0 - 2022-07-31

* Remove support for `initialLazy` state. This complicates things for library users, because it results in a state machine which semantically has no defined initial state, it's "suspended". The better way to do this (if needed) is for consumer to suspend until the initial state is ready and only after that construct the machine.

## 2.1.0 - 2022-05-15
* Remove built-in support for events

    Previously this was nice to have bundled in, but now that `transitionTo`
    and `action` are suspend functions, nothing stops you from easily doing
    something like

    ```kotlin
    val events = MutableSharedFlow<Event>(extraBufferCapacity = 12)

    machine<State> {
      onEach(events) {
        action {
          events.emit(Event.Another)
        }
      }
    }
    ```

## 2.0.0 - 2022-05-14
* Remove `unicorn-lint` sub-project. It wasn't finished, wasn't used, wasn't working.
* Remove `unicorn-rxjava2` support. Use coroutine's rxjava adapters if you need to work with RxJava streams
* Add support for nested states, expressed through a `sealed class`
* `transitionTo` and `action` are now `suspend`-functions

## 0.11.0 - 2022-02-22
* Add `initial` property to the `Machine` interface

## 0.10.0 - 2021-12-09
* Made it possible to have a nullable payload in `onEach` blocks for coroutine-based machines

## 0.9.1 - 2020-05-25

* Added coroutines module/artifact
* Actions are now executed internally, only state stream is present in the API
