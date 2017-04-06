package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("users") @JsonIgnoreProperties(ignoreUnknown = true)
class User : BaseJsonModel() {

    var name: String? = null

    var pastNames: List<String>? = null

    var about: String? = null

    var bio: String? = null

    var aboutFormatted: String? = null

    var location: String? = null

    var website: String? = null

    var waifuOrHusbando: String? = null

    var followersCount: Int? = null

    var createdAt: String? = null

    //TODO: confirm if string
    var facebookId: String? = null

    var followingCount: Int? = null

    var lifeSpentOnAnime: Int? = null

    var birthday: String? = null

    var gender: String? = null

    var updatedAt: String? = null

    var commentsCount: Int? = null

    var favoritesCount: Int? = null

    var likesGivenCount: Int? = null

    var reviewsCount: Int? = null

    var likesReceivedCount: Int? = null

    var postsCount: Int? = null

    var ratingsCount: Int? = null

    //TODO: confirm if string
    var proExpiresAt: String? = null

    //TODO: confirm if string
    var title: String? = null

    var profileCompleted: Boolean? = null

    var feedCompleted: Boolean? = null

    var avatar: Image? = null

    var coverImage: CoverImage? = null

    var ratingSystem: String? = null

    var theme: String? = null

    @Relationship("waifu")
    var waifu: Character? = null

    @RelationshipLinks("waifu")
    var waifuLinks: Links? = null

    @Relationship("libraryEntries")
    var libraryEntries: List<LibraryEntry>? = null

    @RelationshipLinks("libraryEntries")
    var libraryEntryLinks: Links? = null

    @Relationship("reviews")
    var reviews: List<Review>? = null

    @RelationshipLinks("reviews")
    var reviewLinks: Links? = null

/** These are left here out of love for future generations.
  *
    @Relationship("pinnedPost")
    var pinnedPost: Post? = null

    @RelationshipLinks("pinnedPost")
    var pinnedPostLinks: Links? = null

    @Relationship("followers")
    var followers: List<Follow>? = null

    @RelationshipLinks("followers")
    var followerLinks: Links? = null

    @Relationship("following")
    var following: List<Follow>? = null

    @RelationshipLinks("following")
    var followingLinks: Links? = null

    @Relationship("blocks")
    var blocks: List<SOMETHING>? = null

    @RelationshipLinks("blocks")
    var blockLinks: Links? = null

    @Relationship("linkedAccounts")
    var linkedAccounts: List<LinkedAccount>? = null

    @RelationshipLinks("linkedAccounts")
    var linkedAccountLinks: Links? = null

    @Relationship("profileLinks")
    var profileLinks: List<ProfileLink>? = null

    @RelationshipLinks("profileLinks")
    var profileLinkLinks: Links? = null

    @Relationship("mediaFollows")
    var mediaFollows: List<MediaFollow>? = null

    @RelationshipLinks("mediaFollows")
    var mediaFollowLinks: Links? = null

    @Relationship("userRoles")
    var userRoles: List<UserRole>? = null

    @RelationshipLinks("userRoles")
    var userRoleLinks: Links? = null

    @Relationship("favorites")
    var favorites: List<Favorite>? = null

    @RelationshipLinks("favorites")
    var favoriteLinks: Links? = null
*/

}
