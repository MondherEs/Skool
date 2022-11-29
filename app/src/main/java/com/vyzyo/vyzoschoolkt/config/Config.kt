package com.vyzyo.vyzoschoolkt.config

import com.vyzyo.vyzoschoolkt.model.ChildrenResponse
import com.vyzyo.vyzoschoolkt.model.Role
import com.vyzyo.vyzoschoolkt.model.UserResponse

class Config {
    companion object {
        val IDENTIFICATION_TOKEN = "dbb5c6-6c4732-37312b-f36e33-6bb6c7"
        var BASEURL: String = "BASEURL"
        var CHANGEURL: String = "changeURL"
        var BASEURLLogin: String=""

        var TOKEN: String = ""
        var USER_ID: String = ""
        var children: ArrayList<ChildrenResponse.Data?> = ArrayList()
        var loggedUser: UserResponse.Data? = null

        var CLASSID: String =""
        var SCHOOL_VERIFICATION_URL = "http://www.vs.accounts.voncom.tn/"
        var DEFAULT_BASE_URL = "http://www.vs.accounts.voncom.tn/"
        var LOGO_URL = "http://www.vs.accounts.voncom.tn/"
        val ERROR_TAG= "ErrorTag API"
        var ROLE: Role? = null

        var apiHost = "http://www.vs.accounts.voncom.tn/"
    }
}