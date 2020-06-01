package com.apx5.apx5.repository

import android.content.Context
import com.apx5.apx5.base.BaseRepository
import com.apx5.apx5.network.PrDataApiHelper

class PrGameRepository (
    applicationContext: Context,
    executors: PrExecutors,
    apiHelper: PrDataApiHelper
): BaseRepository(applicationContext, executors, apiHelper) {

//    fun updateFavorite(boardRowId: Long, enabled: Boolean): LiveData<MpResource<MpBoard>> {
//        MpLogger.i(MailRepository.TAG, -1L, "updateFavorite")
//
//        return MpBoardQuery(applicationContext, db, apiHelper, boardsObserver)
//            .updateFavorite(boardRowId, enabled)
//            .executeAsLiveData(es.default)
//    }
}
