object path_dependent_types extends App {
  class Food
  class Grass(name: String) extends Food
  class Fish extends Food
  class DogFood extends Food

  abstract class Animal {
    type SuitableFood <: Food
    def eat(food: SuitableFood)
  }

  class Cow extends Animal {
    type SuitableFood = Grass
    def eat(food: Grass): Unit = {}
  }

  class Dog extends Animal {
    type SuitableFood = DogFood
    def eat(food: DogFood): Unit = {}
  }

  val b: Animal = new Cow
  val b2 = new Cow
  val l = new Dog
  val k = new Dog

  b2.eat(new b2.SuitableFood("a"))
  //b.eat(new b.SuitableFood("a"))
  l eat new k.SuitableFood

  class Outer {
    val i = new Inner
    class Inner
  }

  val o1 = new Outer
  val o2 = new Outer

  val i1 = new o1.Inner

  type RefinedAnimal = Animal { type SuitableFood = DogFood }

  class Pasture {
    val animals: List[Animal { type SuitableFood = Grass }] = Nil
    animals.map(x =>
      x.eat(new x.SuitableFood("Other grass"))
    )
  }
}
