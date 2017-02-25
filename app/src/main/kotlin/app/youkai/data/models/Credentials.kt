package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty

class Credentials {

    @JsonProperty("access_token")
    var accessToken: String? = null

    @JsonProperty("token_type")
    var tokenType: String? = null

    @JsonProperty("expires_in")
    var expriesIn: String? = null

    @JsonProperty("refresh_token")
    var refreshToken: String? = null

    var scope: String? = null

    @JsonProperty("created_at")
    var createdAt: String? = null

}
