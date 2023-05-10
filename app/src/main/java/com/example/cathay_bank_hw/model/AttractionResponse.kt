package com.example.cathay_bank_hw.model

data class AttractionResponse(
    val `data`: List<Data?>?=null,
    val total: Int?=null
) {
    data class Data(
        val address: String?=null,
        val category: List<Category?>?=null,
        val distric: String?=null,
        val elong: Double?=null,
        val email: String?=null,
        val facebook: String?=null,
        val fax: String?=null,
        val files: List<Any?>?=null,
        val friendly: List<Any?>?=null,
        val id: Int?=null,
        val images: List<Image?>?=null,
        val introduction: String?=null,
        val links: List<Any?>?=null,
        val modified: String?=null,
        val months: String?=null,
        val name: String?=null,
        val name_zh: Any?=null,
        val nlat: Double?=null,
        val official_site: String?=null,
        val open_status: Int?=null,
        val open_time: String?=null,
        val remind: String?=null,
        val service: List<Service?>?=null,
        val staytime: String?=null,
        val target: List<Target?>?=null,
        val tel: String?=null,
        val ticket: String?=null,
        val url: String?=null,
        val zipcode: String?=null
    ) {
        data class Category(
            val id: Int?=null,
            val name: String?=null
        )

        data class Image(
            val ext: String?=null,
            val src: String?=null,
            val subject: String?=null
        )

        data class Service(
            val id: Int?=null,
            val name: String?=null
        )

        data class Target(
            val id: Int?=null,
            val name: String?=null
        )
    }
}