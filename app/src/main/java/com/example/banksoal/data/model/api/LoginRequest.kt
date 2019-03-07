package com.example.banksoal.data.model.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by amitshekhar on 07/07/17.
 */

object LoginRequest {
    class ServerLoginRequest(
        @field:Expose
        @field:SerializedName("email")
        val email: String?, @field:Expose
        @field:SerializedName("password")
        val password: String?
    ) {

        override fun equals(other: Any?): Boolean {
            if (this === other) {
                return true
            }
            if (other == null || javaClass != other.javaClass) {
                return false
            }

            val that = other as ServerLoginRequest?

            if (if (email != null) email != that!!.email else that!!.email != null) {
                return false
            }
            return if (password != null) password == that.password else that.password == null
        }

        override fun hashCode(): Int {
            var result = email?.hashCode() ?: 0
            result = 31 * result + (password?.hashCode() ?: 0)
            return result
        }
    }
}