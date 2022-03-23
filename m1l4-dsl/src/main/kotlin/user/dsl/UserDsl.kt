package user.dsl

import user.models.Action
import user.models.User
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.temporal.TemporalAdjusters
import java.util.UUID

//         val user = user {
//            name {
//                first = "Kirill"
//                last  = "Krylov"
//            }
//            contacts {
//                email = "email@yandex.ru"
//                phone = "1234567890"
//            }
//            actions {
//                add(Actions.UPDATE)
//                add(Actions.ADD)
//
//                +Actions.DELETE
//                +Actions.READ
//            }
//            availability {
//                monday ("11:30")
//                friday ("18:00")
//            }
//        }

    @UserDsl
    class NameContext {
        var first: String = ""
        var second: String = ""
        var last: String = ""
    }

    @UserDsl
    class ContactsContext {
        var email: String = ""
        var phone: String = ""
    }

    @UserDsl
    class ActionsContext {
        private val _actions: MutableSet<Action> = mutableSetOf()

        val actions: Set<Action>
            get() = _actions.toSet()

        fun add(action: Action) = _actions.add(action)

        fun add(value: String) = add(Action.valueOf(value))

        operator fun Action.unaryPlus() = add(this)

        operator fun String.unaryPlus() = add(this)
    }

    @UserDsl
    class AvailabilityContext {
        private val _availabilities: MutableList<LocalDateTime> = mutableListOf()

        val availabilities: List<LocalDateTime>
            get() = _availabilities.toList()

        fun sunday (time:String) = dateTimeOfWeek(DayOfWeek.SUNDAY, time)
        fun monday (time:String) = dateTimeOfWeek(DayOfWeek.MONDAY, time)
        fun tuesday (time:String) = dateTimeOfWeek(DayOfWeek.TUESDAY, time)
        fun wednesday (time:String) = dateTimeOfWeek(DayOfWeek.WEDNESDAY, time)
        fun thursday (time:String) = dateTimeOfWeek(DayOfWeek.THURSDAY, time)
        fun friday (time:String) = dateTimeOfWeek(DayOfWeek.FRIDAY, time)
        fun saturday (time:String) = dateTimeOfWeek(DayOfWeek.SATURDAY, time)

        fun dateTimeOfWeek(day: DayOfWeek, time: String) {
            val dDay = LocalDate.now().with(TemporalAdjusters.next(day))
            val dTime = time.split(":")
                .map { it.toInt() }
                .let { LocalTime.of(it[0], it[1]) }

            _availabilities.add(LocalDateTime.of(dDay, dTime))
        }
    }

    @UserDsl
    class UserBuilder {
        var id = UUID.randomUUID().toString()

        var firstName = ""
        var secondName = ""
        var lastName = ""

        var phone = ""
        var email = ""

        var actions = setOf<Action>()

        var avialable = listOf<LocalDateTime>()

        @UserDsl
        fun name(block: NameContext.() -> Unit) {
            val ctx = NameContext().apply(block)

            firstName = ctx.first
            secondName = ctx.second
            lastName = ctx.last
        }

        @UserDsl
        fun contacts(block: ContactsContext.() -> Unit) {
            val ctx = ContactsContext().apply(block)

            phone = ctx.phone
            email = ctx.email
        }

        @UserDsl
        fun actions(block: ActionsContext.() -> Unit) {
            val ctx = ActionsContext().apply(block)

            actions = ctx.actions
        }

        @UserDsl
        fun availability(block: AvailabilityContext.() -> Unit) {
            val ctx = AvailabilityContext().apply(block)

            avialable = ctx.availabilities
        }

        fun build() = User( id, firstName, secondName, lastName, phone, email, actions, avialable )
    }

    @UserDsl
    fun user(block: UserBuilder.() -> Unit) = UserBuilder().apply(block).build()

@DslMarker
annotation class UserDsl