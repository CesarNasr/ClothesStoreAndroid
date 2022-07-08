package com.example.clothesstoreapp.data.network.api

import com.example.clothesstoreapp.BuildConfig
import com.example.clothesstoreapp.data.model.ApiEntry
import retrofit2.Response
import retrofit2.http.GET

/**
 * Api Service class calling @GET , @POST,  @DELETE, @UPDATE, @EDIT etc ...
 */
interface ApiService {
    /*@GET(BuildConfig.API_KEY)*/
    @GET("0f78766a6d68832d309d")
    suspend fun fetchProducts(): Response<ApiEntry>
}


/*
- Endpoint : https://api.npoint.io/0f78766a6d68832d309d

- Response :

{
    "products": [
    {
        "name": "Blue Shirt",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/blue.jpg?alt=media&token=5be5eadd-c006-4bb9-83af-d6afc496475a",
        "price": 7.99,
        "stock": 3,
        "category": "Tops",
        "oldPrice": 8.99,
        "productId": "1"
    },
    {
        "name": "Red Shirt",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/red.jpg?alt=media&token=b2f6ffe9-add6-402e-b37c-e1b769b81985",
        "price": 7.99,
        "stock": 5,
        "category": "Tops",
        "productId": "2"
    },
    {
        "name": "Green Shirt",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/green.jpg?alt=media&token=c86cacc6-09f3-4023-be17-b41c855af414",
        "price": 7.99,
        "stock": 4,
        "category": "Tops",
        "productId": "3"
    },
    {
        "name": "White Shirt",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/white.jpg?alt=media&token=a29790a3-af00-475e-b0f8-07e454ec0b74",
        "price": 7.99,
        "stock": 1,
        "category": "Tops",
        "productId": "4"
    },
    {
        "name": "Blue Pants",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/bluepant.jpg?alt=media&token=d033ac8a-bb9f-4a41-be66-cf67f0cd8820",
        "price": 9.99,
        "stock": 8,
        "category": "Pants",
        "productId": "5"
    },
    {
        "name": "Red Pants",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/redpant.jpg?alt=media&token=e8e50875-f4e4-4240-ad6f-dae409811fa5",
        "price": 9.99,
        "stock": 2,
        "category": "Pants",
        "productId": "6"
    },
    {
        "name": "Green Pants",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/greenpant.jpg?alt=media&token=395f2ce9-a8e1-4fbd-bd29-1a2b5fcfefdc",
        "price": 9.99,
        "stock": 14,
        "category": "Pants",
        "productId": "7"
    },
    {
        "name": "White Pants",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/whitepant.jpg?alt=media&token=065a8451-05f0-4ba2-8b80-164e3f555ff2",
        "price": 9.99,
        "stock": 3,
        "category": "Pants",
        "oldPrice": 12.99,
        "productId": "8"
    },
    {
        "name": "Blue Shoes",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/shoesblue.jpg?alt=media&token=778ef569-e3ec-4078-b9ed-a84d4a613486",
        "price": 9.99,
        "stock": 0,
        "category": "Shoes",
        "productId": "9"
    },
    {
        "name": "Red Shoes",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/shoesred.jpg?alt=media&token=2f4fa3a6-e315-45fc-8356-2edf1dad2fd4",
        "price": 9.99,
        "stock": 12,
        "category": "Shoes",
        "productId": "10"
    },
    {
        "name": "Green Shoes",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/shoesgreen.jpg?alt=media&token=86ee6a48-f71b-49de-acbc-e695d93a3a68",
        "price": 9.99,
        "stock": 11,
        "category": "Shoes",
        "productId": "11"
    },
    {
        "name": "White Shoes",
        "image": "https://firebasestorage.googleapis.com/v0/b/techtest-1f1da.appspot.com/o/shoeswhite.jpg?alt=media&token=9eb0ae98-afab-4d2b-8912-ccce6d5ecd72",
        "price": 9.99,
        "stock": 4,
        "category": "Shoes",
        "oldPrice": 15.99,
        "productId": "12"
    }
    ]
}*/
