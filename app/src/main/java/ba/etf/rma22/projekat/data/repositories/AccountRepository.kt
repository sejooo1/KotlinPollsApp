package ba.etf.rma22.projekat.data.repositories

import android.content.Context

class AccountRepository {

    companion object {
        var acHash: String = "838bb392-2b00-419e-8da4-23f45560a9a9"
        private lateinit var context: Context
        fun setContext(_context: Context){
            context=_context
        }
        fun postaviHash(acHash: String): Boolean {
            this.acHash = acHash
            return true
        }

        fun getHash(): String {
            return acHash
        }


    }
}