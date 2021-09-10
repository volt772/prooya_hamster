package com.apx5.data.response

import com.apx5.PrNetworkKeys
import com.google.gson.annotations.SerializedName

class PrResponse2<E> {
    @SerializedName(PrNetworkKeys.DATA)
    var data: E? = null
}
