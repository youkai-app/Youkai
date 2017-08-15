package app.youkai.data.models

class Status(value: String) {

    companion object {
        val CURRENT = Status("current")
        val PLANNED = Status("planned")
        val COMPLETED = Status("completed")
        val ON_HOLD = Status("on_hold")
        val DROPPED = Status("dropped")
    }

}