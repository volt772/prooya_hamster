package com.apx5.data.response

import com.google.gson.annotations.SerializedName

class PrResponse2<E> {
    @SerializedName("data")
    var data: E? = null
}
