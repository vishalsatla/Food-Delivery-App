package uk.ac.tees.mad.w9617154.foodapp.model

data class UserModel(

    val userId:String,
    val email : String,
    val username: String,
){
    fun toMap():MutableMap<String,Any>{
        return  mutableMapOf(
            "UserId" to this.userId,
            "email" to this.email,
            "username" to this.username
        )
    }
}
