package com.dicoding.tourismapp.core.data.source.local

import com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity
import com.dicoding.tourismapp.core.data.source.local.room.TourismDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val tourismDao: com.dicoding.tourismapp.core.data.source.local.room.TourismDao) {

//    hapus kode berikut
//    companion object {
//        private var instance: LocalDataSource? = null
//
//        fun getInstance(tourismDao: TourismDao): LocalDataSource =
//            instance ?: synchronized(this) {
//                instance ?: LocalDataSource(tourismDao)
//            }
//    }

    fun getAllTourism(): Flow<List<com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity>> = tourismDao.getAllTourism()

    fun getFavoriteTourism(): Flow<List<com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity>> = tourismDao.getFavoriteTourism()

    suspend fun insertTourism(tourismList: List<com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity>) = tourismDao.insertTourism(tourismList)

    fun setFavoriteTourism(tourism: com.dicoding.tourismapp.core.data.source.local.entity.TourismEntity, newState: Boolean) {
        tourism.isFavorite = newState
        tourismDao.updateFavoriteTourism(tourism)
    }
}