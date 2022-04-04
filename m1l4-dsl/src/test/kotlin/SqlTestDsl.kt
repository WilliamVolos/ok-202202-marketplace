//import org.junit.Test
//import user.dsl.user
//import user.models.Action
//import kotlin.test.assertEquals
//
//class SqlTestDsl {
//
//    @Test
//    fun `test sql`() {
//        val result = resultSql {
//            select {
//                first = "Kirill"
//                last  = "Krylov"
//            }
//            contacts {
//                email = "email@yandex.ru"
//                phone = "1234567890"
//            }
//            actions {
//                add(Action.UPDATE)
//                add(Action.ADD)
//
//                +Action.DELETE
//                +Action.READ
//            }
//            availability {
//                monday ("11:30")
//                friday ("18:00")
//            }
//        }
//        assertEquals("Kirill", user.firstName)
//    }
//}
//
////data class Person (val id:Int, val name:String, val age:Int, val sex:Char)
////
////fun main() {
////    val persons = listOf(
////        Person(10, "Иван", 23, 'M'),
////        Person(10, "Ольга", 30, 'Ж'),
////        Person(10, "Сергей", 43, 'M'),
////        Person(10, "Антон", 25, 'M')
////    )
////    println(persons.map{it.name, it.age, it.sex})
////}