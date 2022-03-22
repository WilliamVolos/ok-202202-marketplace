package builders.new

enum class Drink {
    WATER,
    COFEE,
    TEA
}

abstract class Meal {
    data class Breakfast(
        val eggs: Int,
        val bacon: Boolean,
        val drink: Drink,
        val title: String
    ) : Meal()
}

class BreakfastBuilder {
    var eggs = 0
    var bacon = false
    var title = ""
    var drink = Drink.WATER

    fun build() = Meal.Breakfast(eggs, bacon, drink, title)
}

fun breakfast(block: BreakfastBuilder.() -> Unit) = BreakfastBuilder().apply(block).build()

fun main() {
    val myBreakfast = breakfast {
        eggs = 3
        bacon = true
        title = "Simple"
        drink = Drink.COFEE }

    println(myBreakfast)

//    val myBreakfast = BreakfastBuilder().apply {
//        eggs = 3
//        bacon = true
//        title = "Simple"
//        drink = Drink.COFEE
//    }.build()

    // println(breakfast)
}