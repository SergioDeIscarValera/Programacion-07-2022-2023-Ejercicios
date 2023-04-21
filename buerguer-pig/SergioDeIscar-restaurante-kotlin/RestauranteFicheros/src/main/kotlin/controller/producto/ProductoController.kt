package controller.producto

import com.github.michaelbull.result.*
import errors.ProductoError
import factories.ProductoFactory
import models.Bebida
import models.Hamburguesa
import models.Producto
import mu.KotlinLogging
import repository.producto.ProductoRepository
import services.storage.productos.ProductosStorageService
import validotors.validate

private val logger = KotlinLogging.logger {}

class ProductoController(
    private val repo: ProductoRepository,
    private val storage: ProductosStorageService
): IProductoController {
    override fun findAll(): Iterable<Producto> {
        logger.debug { "ProductoController ->\tfindAll" }
        return repo.findAll()
    }

    override fun findById(id: Long): Result<Producto, ProductoError> {
        logger.debug { "ProductoController ->\tfindById" }
        return repo.findById(id)?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun save(element: Producto, storage: Boolean): Result<Producto, ProductoError> {
        logger.debug { "ProductoController ->\tsave" }
        return element.validate().onSuccess {
            repo.save(element)
            if (storage) exportData()
        }
    }

    override fun saveAll(elements: Iterable<Producto>, storage: Boolean): Result<Boolean, ProductoError> {
        logger.debug { "ProductoController ->\tsaveAll" }
        elements.forEach{it.validate().onFailure {
            logger.error { "ProductoController -> ${it.message}" }
            return Err(it)
        }}
        repo.saveAll(elements)
        if (storage) exportData()
        return Ok(true)
    }

    override fun deleteById(id: Long): Result<Boolean, ProductoError> {
        logger.debug { "ProductoController ->\tdeleteById" }
        return if (repo.deleteById(id)){
            Ok(true)
        }else{
            Err(ProductoError.ProductoNoEncontradoError())
        }
    }

    override fun delete(element: Producto): Result<Boolean, ProductoError> {
        logger.debug { "ProductoController ->\tdelete" }
        return if (repo.delete(element)){
            Ok(true)
        }else{
            Err(ProductoError.ProductoNoEncontradoError())
        }
    }

    override fun deleteAll() {
        logger.debug { "ProductoController ->\tdeleteAll" }
        repo.deleteAll()
    }

    override fun existsById(id: Long): Result<Boolean, ProductoError> {
        logger.debug { "ProductoController ->\texistsById" }
        return if (repo.existsById(id)){
            Ok(true)
        }else{
            Err(ProductoError.ProductoNoEncontradoError())
        }
    }

    override fun exportData() {
        logger.debug { "ProductoController ->\texportData" }
        storage.saveAll(repo.findAll().toList())
    }

    override fun importData() {
        logger.debug { "ProductoController ->\timportData" }
        repo.saveAll(storage.loadAll())
    }

    override fun getOrderByPrecio(): List<Producto> {
        logger.debug { "ProductoController ->\tgetOrderByPrecio" }
        return repo.getOrderByPrecio()
    }

    override fun getProductoMasCaro(): Result<Producto, ProductoError> {
        logger.debug { "ProductoController ->\tgetProductoMasCaro" }
        return repo.getProductoMasCaro()?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun getProductoMasBarato(): Result<Producto, ProductoError> {
        logger.debug { "ProductoController ->\tgetProductoMasBarato" }
        return repo.getProductoMasBarato()?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun getBebidaConMenosCapacidad(): Result<Bebida, ProductoError> {
        logger.debug { "ProductoController ->\tgetBebidaConMenosCapacidad" }
        return repo.getBebidaConMenosCapacidad()?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun getHamburguesaMasCara(): Result<Hamburguesa, ProductoError> {
        logger.debug { "ProductoController ->\tgetHamburguesaMasCara" }
        return repo.getHamburguesaMasCara()?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun getHamburguesaConMasIngredientes(): Result<Hamburguesa, ProductoError> {
        logger.debug { "ProductoController ->\tgetHamburguesaConMasIngredientes" }
        return repo.getHamburguesaConMasIngredientes()?.let { Ok(it) } ?: Err(ProductoError.ProductoNoEncontradoError())
    }

    override fun getHamburguesasPrecioMedio(): Double {
        logger.debug { "ProductoController ->\tgetHamburguesasPrecioMedio" }
        return repo.getHamburguesasPrecioMedio()
    }

    override fun getPrecioMedioIngredientes(): Map<String, Double> {
        logger.debug { "ProductoController ->\tgetPrecioMedioIngredientes" }
        return repo.getPrecioMedioIngredientes()
    }
}