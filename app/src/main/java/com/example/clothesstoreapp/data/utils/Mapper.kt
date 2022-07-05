package com.example.clothesstoreapp.data.utils

import com.example.clothesstoreapp.data.localdatasource.model.Basket
import com.example.clothesstoreapp.data.localdatasource.model.Wishlist
import com.example.clothesstoreapp.data.model.Product
import javax.inject.Inject
/**
 * Each level of our app (local data source/ remote datasource / UI) has it's own entity/ models and this class helps us map
 * them to one another
 */
class WishlistMapper
@Inject
constructor() : EntityMapper<Wishlist, Product> {

    override fun mapFromLocalEntity(entity: Wishlist): Product {
        return Product(
            productId = entity.productId,
            image = entity.image,
            name = entity.name,
            price = entity.price,
        )
    }

    override fun mapToEntity(domainModel: Product): Wishlist {
        return Wishlist(
            productId = domainModel.productId,
            image = domainModel.image,
            name = domainModel.name,
            price = domainModel.price,
        )
    }

    fun mapFromEntityList(entities: List<Wishlist>): List<Product> {
        return entities.map { mapFromLocalEntity(it) }
    }
}

class BasketMapper
@Inject
constructor() : EntityMapper<Basket, Product> {

    override fun mapFromLocalEntity(entity: Basket): Product {
        return Product(
            productId = entity.productId,
            image = entity.image,
            name = entity.name,
            price = entity.price,
            qty = entity.qty
        )
    }

    override fun mapToEntity(domainModel: Product): Basket {
        return Basket(
            productId = domainModel.productId,
            image = domainModel.image,
            name = domainModel.name,
            price = domainModel.price,
            qty = domainModel.qty
        )
    }

    fun mapFromEntityList(entities: List<Basket>): List<Product> {
        return entities.map { mapFromLocalEntity(it) }
    }
}





















