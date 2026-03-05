package hashMaps

// in maps, null keys may be allowed, but duplicate keys are not allowed
// Insert, get, etc is O(1)
// the objects used as keys should implement equals() and hashCode(), and ideally keys are immutable types
// The HashMap applies a secondary transformation (e.g., hash(key.hashCode())) to improve distribution
// if multiple keys have the same hash code, we have a hash collision... can resolve that by
// making it an array of linked lists
// note: HashMap is preferred over Hashtable when synchronization is not needed
val myMap = HashMap<String, Int>() // cannot override hash function (which converts key to array index) in built-in lib
myMap["apple"] = 1
myMap["banana"] = 2  // Using the indexing operator

val myMap2 = mutableMapOf("apple" to 1, "banana" to 2)
myMap2 += Pair("cherry", 3)
myMap2 -= "apple"
println(myMap2)
// return a new map with key/values modified:
println(myMap2.mapKeys { pair -> pair.key + " is key" })
println(myMap2.mapValues { pair -> pair.value + 2 })