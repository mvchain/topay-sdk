package com.mvc.to_pay_sdk.bean

import android.os.Parcel
import android.os.Parcelable

data class BlockBean(
        /**
         * channelName : string
         * contact : string
         * createdAt : 0
         * id : 0
         * info : string
         * updatedAt : 0
         */

        var amount: Int,
        var createdAt: Long,
        var id: Int,
        var orderNumber: String,
        var orderStatus: Int,
        var orderType: Int,
        var stopAt: Long,
        var tokenId: String,
        var tokenName: String,
        var tokenValue: Int,
        var userId: Int,
        var imageRes: Int
) :Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readLong(),
                parcel.readInt(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readLong(),
                parcel.readString(),
                parcel.readString(),
                parcel.readInt(),
                parcel.readInt(),
                parcel.readInt()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeInt(amount)
                parcel.writeLong(createdAt)
                parcel.writeInt(id)
                parcel.writeString(orderNumber)
                parcel.writeInt(orderStatus)
                parcel.writeInt(orderType)
                parcel.writeLong(stopAt)
                parcel.writeString(tokenId)
                parcel.writeString(tokenName)
                parcel.writeInt(tokenValue)
                parcel.writeInt(userId)
                parcel.writeInt(imageRes)
        }

        override fun describeContents(): Int {
                return 0
        }

        companion object CREATOR : Parcelable.Creator<BlockBean> {
                override fun createFromParcel(parcel: Parcel): BlockBean {
                        return BlockBean(parcel)
                }

                override fun newArray(size: Int): Array<BlockBean?> {
                        return arrayOfNulls(size)
                }
        }

}
