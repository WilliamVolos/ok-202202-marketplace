import org.junit.Test
import user.dsl.user
import user.models.Action
import kotlin.test.assertEquals


class UserTestCase {

    @Test
    fun `test user`() {
        val user = user {
            name {
                first = "Kirill"
                last  = "Krylov"
            }
            contacts {
                email = "email@yandex.ru"
                phone = "1234567890"
            }
            actions {
                add(Action.UPDATE)
                add(Action.ADD)

                +Action.DELETE
                +Action.READ
            }
            availability {
                monday ("11:30")
                friday ("18:00")
            }
        }
        assertEquals("Kirill", user.firstName)
    }
}