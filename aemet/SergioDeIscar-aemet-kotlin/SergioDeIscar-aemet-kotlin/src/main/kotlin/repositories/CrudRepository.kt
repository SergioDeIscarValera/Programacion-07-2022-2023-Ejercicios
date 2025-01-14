package repositories

interface CrudRepository<T,ID> {
    fun getAll(): Iterable<T>
    fun getById(id: ID): T?
    fun save(element: T, storage: Boolean = true): T
    fun saveAll(elements: Iterable<T>, storage: Boolean = true)
    fun deleteById(id: ID): T?
    fun update(element: T): T?
    fun updateById(id: ID, element: T): T?
}