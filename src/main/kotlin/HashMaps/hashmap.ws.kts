@file:Suppress("SuspiciousEqualsCombination")

package Maps

// in maps, null keys may be allowed, but duplicate keys are not allowed
// the objects used as keys should implement equals() and hashCode(), and ideally keys are immutable types
// hashing will convert the key to an Int. Insert, get, etc is O(1)
// if multiple keys have the same hash code, we have a hash collision... can resolve that by
// mapping it to a key-value list (chaining) - basically the hashmap becomes an array of linked lists
// note: HashMap is preferred over Hashtable when synchronization is not needed
private class Node<K, V>(
    val hash: Int,
    val key: K,
    var value: V,
    var next: Node<K, V>?) {

    override fun toString() = "$key=$value"

    override fun hashCode(): Int {
        val h = key?.hashCode() ?: 0
        return h xor (h ushr 16)
    }

    override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other is Node<*, *> && this.key == other.key && this.value == other.value) return true
        return false
    }
}

class HashMap<K, V> {
    private val minCapacity = 1 shl 4
    private val maxCapacity = 1 shl 30
    private var table: Array<Node<K, V>?> = arrayOfNulls(minCapacity)
    // the hashcode should be further mapped to the actual array index
    private fun getBucketIndex(key: K) = key.hashCode() % 100
    // ...
}

