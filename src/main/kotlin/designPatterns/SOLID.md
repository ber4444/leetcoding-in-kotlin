Dependency Inversion Principle - "Depend on abstractions, not concretions." Decouple via interfaces, it helps Dependency Injection, testing. Abstraction should be owned by the high-level module. In Kotlin/Android, this means your ViewModel defines the interface it needs, and the Data layer implements it.

Interface Segregation Principle - "Clients should not be forced to depend on methods they do not use." Define specific, smaller interfaces. Instead of a Worker interface with eat() and work(), have Workable and Eatable. A Robot class only implements Workable, a Human implements both interfaces.

Liskov Substitution Principle - "Subtypes must be substitutable for their base types without altering the correctness of the program." Use inheritance only when a true "is-a" relationship exists (e.g., ArrayList is a List). 
But be careful, inheritance can violate LSP when overriding methods in a subclass unexpectedly changes behavior, such as when inheriting a mutable Square from a mutable Rectangle, since it will break logic that expects changing the width won't affect the height. Fix:
interface Shape {
    fun area(): Int
}
class Rectangle(
    var width: Int,
    var height: Int
) : Shape {
    override fun area() = width * height
}
class Square(var side: Int) : Shape { // Square should not inherit from Rectangle, "Composition over Inheritance"
    override fun area() = side * side
}

Open/Closed Principle - "Software entities should be open for extension, but closed for modification." By using interfaces and polymorphism:
interface Shape
class Rectangle(val width: Double, val height: Double) : Shape
class Circle(val radius: Double) : Shape
// This function is "Closed" for modification - never has to change, even when you add 100 new shape types
fun calculateTotalArea(shapes: List<Shape>): Double {
    return shapes.sumOf { it.area() } 
}

Single Responsibility Principle - "A class should have only one reason to change." If a class handles both database logic and UI formatting, it has two "masters" and two reasons to break. If you find yourself using the word 'and' to describe what a class does, it’s likely violating SRP.
